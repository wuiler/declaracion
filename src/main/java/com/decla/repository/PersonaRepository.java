package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Persona;


@ApplicationScoped
public class PersonaRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Persona data) {
        em.persist(data);	
    }

    public Collection<Persona> list() {

        TypedQuery<Persona> q = em.createQuery("SELECT p FROM Persona p ", Persona.class);
        return q.getResultList();

    }
    
    public Persona findById(String id) {
        return em.find(Persona.class, id);
    }
    
}