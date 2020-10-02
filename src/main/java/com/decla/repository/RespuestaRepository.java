package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Entidad;
import com.decla.model.Persona;
import com.decla.model.Pregunta;
import com.decla.model.Respuesta;

@ApplicationScoped
public class RespuestaRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Respuesta data) {
        em.persist(data);	
    }

    public Collection<Respuesta> list() {

        TypedQuery<Respuesta> q = em.createQuery("SELECT r FROM Respuesta r",Respuesta.class);
        return q.getResultList();

    }

    public Respuesta find(Entidad entidad, Persona persona) {
        try {
            TypedQuery<Respuesta> q =  em.createQuery("SELECT r FROM Respuesta r WHERE r.entidad = :entidad AND r.pregunta = :pregunta AND r.persona = :persona", Respuesta.class)
                                            .setParameter("entidad", entidad)
                                            .setParameter("persona", persona);
                return q.getSingleResult();
        } catch(NoResultException e) { 
            return null;
        }        
    }

    public Collection<Respuesta> find(Entidad entidad, Pregunta pregunta, Persona persona) {
        
        TypedQuery<Respuesta> q = em.createQuery("SELECT r FROM Respuesta r WHERE r.entidad = :entidad AND r.pregunta = :pregunta AND r.persona = :persona", Respuesta.class)
                                                .setParameter("entidad", entidad)
                                                .setParameter("pregunta", pregunta)
                                                .setParameter("persona", persona);
        return q.getResultList();

    }

    	
}