package com.decla.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.decla.config.AppConfig;
import com.decla.model.DeclaracionEntidad;
import com.decla.model.DeclaracionPersona;
import com.decla.model.Entidad;
import com.decla.model.JsonString;
import com.decla.model.Persona;
import com.decla.model.Pregunta;
import com.decla.model.Respuesta;
import com.decla.repository.DeclaracionEntidadRepository;
import com.decla.repository.DeclaracionPersonaRepository;
import com.decla.repository.EntidadRepository;
import com.decla.repository.PersonaRepository;
import com.decla.repository.RespuestaRepository;
import com.decla.util.Fecha;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;

@Path("/admin")
public class FrontAdmin {
    private static final Logger LOG = Logger.getLogger(AppConfig.class);
    
    @Inject
    JsonWebToken jwt;

    @Inject 
    Template index;

    @ResourcePath("admin/organizacion.html") 
    Template organizacion;

    @ResourcePath("admin/persona.html") 
    Template personaTemplate;

    @ResourcePath("admin/dashboard.html") 
    Template dashboard;

    @Inject
    EntidadRepository entidadRepository;

    @Inject
    PersonaRepository personaRepository;

    @Inject
    DeclaracionPersonaRepository declaracionPersonaRepository;

    @Inject
    DeclaracionEntidadRepository declaracionEntidadRepository;    

    @Inject
    RespuestaRepository respuestaRepository;

    @GET
    @Path("/organizacion")
    public TemplateInstance getEntidad() {

        String userId = jwt.getName();

        LOG.infof("userid : %s", userId);

        Persona persona = personaRepository.findById(userId);

        if (persona==null) {
            persona = registroInicialPersona(userId);
        }

        Collection<Entidad> listaEntidad = new ArrayList<Entidad>();
        listaEntidad = entidadRepository.findAllByOwner(userId);
        LOG.infof("listaEntidad.size() : %s",listaEntidad.size());

        Collection<DeclaracionEntidad> listaDeclaracionEntidad = new ArrayList<DeclaracionEntidad>();        
        listaDeclaracionEntidad = declaracionEntidadRepository.listByAllEntidad(listaEntidad);

        return organizacion
                .data("listaEntidad", listaEntidad)
                .data("persona", persona)
                .data("listaDeclaracionEntidad", listaDeclaracionEntidad);
            
    }    

    @GET
    @Path("/personas")
    public TemplateInstance getPersonas() {

        String userId = jwt.getName();

        LOG.infof("userid : %s", userId);

        Persona persona = personaRepository.findById(userId);

        if (persona==null) {
            persona = registroInicialPersona(userId);
        }

        Collection<Entidad> listaEntidad = new ArrayList<Entidad>();
        listaEntidad = entidadRepository.findAllByOwner(userId);
        LOG.infof("listaEntidad.size() : %s",listaEntidad.size());

        Collection<DeclaracionPersona> listaDeclaracionPersonas = new ArrayList<DeclaracionPersona>();
        listaDeclaracionPersonas = declaracionPersonaRepository.listByAllEntidad(listaEntidad);
        LOG.infof("listaDeclaracionPersonas : %s",listaDeclaracionPersonas.size());

        return personaTemplate
                .data("listaEntidad", listaEntidad)
                .data("persona", persona)
                .data("listaDeclaracionPersonas", listaDeclaracionPersonas);
            
    }

    @GET
    public TemplateInstance getDashboard() {

        String userId = jwt.getName();

        LOG.infof("user : %s",userId);

        Persona persona = null;
        persona = personaRepository.findById(userId);

        if (persona==null) {
            persona = registroInicialPersona(userId);
        }

        Map<String,String> utils = new HashMap<String,String>();
        utils.put("fecha", Fecha.hoyTexto());

        Map<String,BigInteger> stats = new HashMap<String,BigInteger>();
        //stats.put("access", Fecha.hoyTexto());


        Collection<Entidad> listaEntidad = new ArrayList<Entidad>();
        listaEntidad = entidadRepository.findAllByOwner(userId);
        LOG.infof("listaEntidad.size() : %s",listaEntidad.size());

        LOG.infof("declaracionPersonaRepository.countByAllEntidad(listaEntidad): %s",declaracionPersonaRepository.countByAllEntidad(listaEntidad));


        BigInteger accessCountTodayByAllEntity = BigInteger.valueOf(0);
        BigInteger accessCountYesterdayByAllEntity = BigInteger.valueOf(0);

        Long countByAllEntidad = declaracionPersonaRepository.countByAllEntidad(listaEntidad);

        for(Entidad entidad : listaEntidad) {
            accessCountTodayByAllEntity.add(declaracionPersonaRepository.countByTodayAndEntidad(entidad));
            accessCountYesterdayByAllEntity.add(declaracionPersonaRepository.countByYesterdayAndEntidad(entidad));
            LOG.infof("declaracionPersonaRepository.countByYesterdayAndEntidad(entidad): %s", declaracionPersonaRepository.countByYesterdayAndEntidad(entidad));
        }
        
        stats.put("countByAllEntidad", BigInteger.valueOf(countByAllEntidad));
        stats.put("accessCountTodayByAllEntity", accessCountTodayByAllEntity);
        stats.put("accessCountYesterdayByAllEntity", accessCountYesterdayByAllEntity);

        Collection<DeclaracionPersona> lista = declaracionPersonaRepository.listByAllEntidad(listaEntidad);

        for(DeclaracionPersona decla: lista) {
            //LOG.infof("decla : %s",decla.toString());
            List<Pregunta> listaPreguntas = decla.getDeclaracion().getPreguntas();    
            Entidad e = decla.getEntidad();
            Persona per = decla.getPersona();


            for (Pregunta pre : listaPreguntas) {
                //LOG.info(pre.getNombre() + ":\n");
                Collection<Respuesta> listaRespuesta = respuestaRepository.find(e, pre, per);
                for (Respuesta res : listaRespuesta) {
                    //LOG.info(res.getValor() + "\n");
                }
            }
        }

        
        return dashboard
                .data("stats", stats)
                .data("util", utils)
                .data("listaEntidad", listaEntidad)
                .data("persona", persona)
                .data("listaDeclaraciones",lista);
            
    }    

    private Persona registroInicialPersona(String userId) {

        Persona persona = new Persona();
        persona.setMail(userId);
        personaRepository.save(persona);

        return persona;
        
    }

}