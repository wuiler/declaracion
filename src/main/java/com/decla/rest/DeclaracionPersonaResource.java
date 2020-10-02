package com.decla.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.decla.model.Declaracion;
import com.decla.model.DeclaracionPersona;
import com.decla.model.Entidad;
import com.decla.model.Persona;
import com.decla.model.Pregunta;
import com.decla.model.Respuesta;
import com.decla.repository.DeclaracionPersonaRepository;
import com.decla.repository.DeclaracionRepository;
import com.decla.repository.EntidadRepository;
import com.decla.repository.PersonaRepository;
import com.decla.repository.PreguntaRepository;
import com.decla.repository.RespuestaRepository;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.quarkus.mailer.Mailer;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

@ApplicationScoped
@Path("/api/declaracion")
public class DeclaracionPersonaResource {

    private static final Logger LOG = Logger.getLogger(DeclaracionPersonaResource.class);

    @Inject
    JsonWebToken jwt;

    @Inject
    Mailer mailer;

    @Inject
    DeclaracionRepository controladorDeclaracion;

    @Inject
    EntidadRepository controladorEntidad;

    @Inject
    DeclaracionPersonaRepository controladorDeclaracionPersona;
    
    @Inject
    PersonaRepository controladorPersona;

    @Inject
    RespuestaRepository controladorRespuesta;

    @Inject
    PreguntaRepository controladorPregunta;

    @Context
    HttpServerRequest request;

    @Inject
    UserTransaction transaction;

    @POST
    @Path("/registrar")
    @Consumes("multipart/form-data")
    @Produces({ MediaType.TEXT_PLAIN })
    @Transactional
    public String create(MultipartFormDataInput input) {
        LOG.info("ingresando al metodo");

        Map<String, List<InputPart>> form = input.getFormDataMap();
        Collection<List<InputPart>> lists = form.values();

        Entidad entidad = null;
        Persona persona = null;
        Declaracion declaracion = null;
        Collection<Respuesta> listaRespuesta = new ArrayList<Respuesta>();

        Pregunta pregunta = null;
        
        for (List<InputPart> list : lists) {

            try {
                InputPart inputPart = list.get(0);
                MultivaluedMap<String, String> header = inputPart.getHeaders();

                String campo = inputPart.getBodyAsString();
                String valor = getValue(header);

                if (valor.contains("idEntidad")) {
                    entidad = controladorEntidad.findById(Long.valueOf(campo));
                }

                if (valor.contains("idUsuario")) {
                    persona = controladorPersona.findById(campo);
                }

                if (valor.contains("idDeclaracion")) {
                    declaracion = controladorDeclaracion.findById(Long.valueOf(campo));
                }

                if (valor.startsWith("pregunta")) {
                    String idPreguntaValor = valor;
                    String idPregunta = idPreguntaValor.trim().replace("pregunta", "");

                    pregunta = controladorPregunta.findById(Integer.valueOf(idPregunta));
                    
                    //LOG.infof("Pregunta : %s", pregunta.toString());
                    //aqui debemos verificar la existencia de la pregunta sino.
                    //if ("option".equals(pregunta.getTipo().trim())) {

                    //}
                     
                    Respuesta respuesta = new Respuesta();
                    respuesta.setEntidad(entidad);
                    respuesta.setPersona(persona);
                    respuesta.setPregunta(pregunta);
                    respuesta.setValor(campo);
                    respuesta.setFecha(new Date());
                    
                    controladorRespuesta.save(respuesta);

                    listaRespuesta.add(respuesta);
    
                    LOG.infof("ID pregunta : %s" , idPregunta);
                }

                
                LOG.info(campo + ": " +   valor);
                //LOG.info( list.get(0).getHeaders().get("name") + " - " +  list.get(0).getBodyAsString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

        DeclaracionPersona declaracionPersona = new DeclaracionPersona();
        declaracionPersona.setDeclaracion(declaracion);
        declaracionPersona.setEntidad(entidad);
        declaracionPersona.setPersona(persona);
        declaracionPersona.setFecha(new Date());
        
        int estadoDeclaracion = 0;
        int alertaEstadoDeclaracion = 0;

        for (Respuesta respuestaRealizadas : listaRespuesta) {
            System.out.println("value= " + respuestaRealizadas);

            String tipoPregunta = respuestaRealizadas.getPregunta().getTipo();
            String valor = respuestaRealizadas.getValor();
            
            alertaEstadoDeclaracion+= verificarAlertas(tipoPregunta, valor);
/*
            if ("option".equals(tipoPregunta)) {


                //significa positivo en las repuestas
                if ("1".equals(valor)) {                                    
                    alertaEstadoDeclaracion++;
                }
            }
            */
        }

        //podemos definir una lista de estados posibles;
        if (alertaEstadoDeclaracion==0) {
            estadoDeclaracion = 1;
        } else if (alertaEstadoDeclaracion>=100) {
            estadoDeclaracion = 2;
        } else {
            estadoDeclaracion = 0;
        }
        
        declaracionPersona.setEstado(estadoDeclaracion);
        controladorDeclaracionPersona.save(declaracionPersona);        
    
        return "Gracias completar tu declaración! Que tengas muy buen juego.";
        
    }

    private String getValue(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String value : contentDisposition) {
            if ((value.trim().startsWith("name"))) {

                String[] name = value.split("=");        
                String finalValue = name[1].trim().replaceAll("\"", "");

                return finalValue;
            }                
        }

        return "unknown";
    }
    
    private int verificarAlertas(String tipoPregunta, String valor) {
        
        int alertaEstadoDeclaracion = 0;

        if ("option".equals(tipoPregunta)) {
            if ("1".equals(valor)) {                                    
                alertaEstadoDeclaracion++;
            }
        }
        if ("text".equals(tipoPregunta)) {            
            try {
                Integer temp = Integer.valueOf(valor);
                if (temp>37) {
                    alertaEstadoDeclaracion=100;
                } 
            } catch(Exception e) {
                LOG.info("No se puede convertir");
            }
        }

        return alertaEstadoDeclaracion;
    }

}
