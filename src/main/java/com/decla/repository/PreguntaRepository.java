package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Pregunta;

@ApplicationScoped
public class PreguntaRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Pregunta data) {
        em.persist(data);	
    }

    public Collection<Pregunta> list() {

        TypedQuery<Pregunta> q = em.createQuery("SELECT p FROM Pregunta p ",Pregunta.class);
        return q.getResultList();

    }

    public Pregunta findById(int id) {

        Pregunta p = em.find(Pregunta.class, id);

        if (p==null) {
            throw new EntityNotFoundException("no se encuentra la pregunta " + id);
        }

        return p;

    }

}