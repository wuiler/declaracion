package com.decla.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.decla.config.AppConfig;
import com.decla.model.DeclaracionEntidad;
import com.decla.model.Entidad;
import com.decla.model.Pregunta;
import com.decla.repository.DeclaracionEntidadRepository;
import com.decla.repository.EntidadRepository;
import com.decla.repository.PreguntaRepository;

import org.jboss.logging.Logger;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class Front {
    private static final Logger LOG = Logger.getLogger(AppConfig.class);
    
    @Inject 
    Template index;

    @Inject 
    Template organizacion;

    @Inject 
    Template registro;

    @Inject 
    Template declaracion;

    @Inject 
    Template error;

    @Inject
    AppConfig config;

    @Inject
    PreguntaRepository preguntaRepository;
  
    @Inject
    EntidadRepository entidadRepository;

    @Inject
    DeclaracionEntidadRepository declaracionEntidadRepository;

    @GET
    @Path("/")
    public TemplateInstance get() {                
        return index.instance();
    }    

    @GET
    @Path("organizacion")
    public TemplateInstance getOrganzacion() {
        return organizacion
            .data("listaEntidades",new ArrayList<DeclaracionEntidad>());
    }    

    @GET
    @Path("registro")
    public TemplateInstance getPersona() {
        return registro.instance();
    }    

    @GET
    @Path("declaracion")
    public TemplateInstance getDeclaracion(@QueryParam("entidad") String idEntidad) {

        Entidad entidad = null;
        DeclaracionEntidad declaracionEntidad = null;

        Collection<Pregunta> listaPreguntas = new ArrayList<Pregunta>();
        Collection<DeclaracionEntidad> listaDeclaracionEntidad = new ArrayList<DeclaracionEntidad>();

        if (idEntidad==null || idEntidad.length()==0 || idEntidad.isEmpty()) {

            Collection<Entidad> listaEntidad = new ArrayList<Entidad>();
            listaEntidad = entidadRepository.listPublic();

            listaDeclaracionEntidad = declaracionEntidadRepository.listByAllEntidad(listaEntidad);

            return organizacion
                .data("listaDeclaracionEntidad",listaDeclaracionEntidad);

        } else {


            try {
                entidad = entidadRepository.findByHash(idEntidad);
            } catch (Exception e) {
                return error
                    .data("mensajeError",e.getMessage());
            }
            
            if (entidad==null) {
                return error
                    .data("mensajeError","No existe la Entidad seleccionada.");
            }
            
            declaracionEntidad = declaracionEntidadRepository.findByEntidad(entidad);
    
            if (declaracionEntidad==null) {
                return error
                    .data("mensajeError","No hay declaraciones definidas para la identidad");
            }
    
            listaPreguntas = preguntaRepository.list();
            
        }

        return declaracion
            .data("declaracionEntidad",declaracionEntidad)
            .data("entidad",entidad)
            .data("listaPreguntas",listaPreguntas);
    }    

}