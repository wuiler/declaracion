package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Declaracion;

@ApplicationScoped
public class DeclaracionRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Declaracion data) {
        em.persist(data);	
    }

    public Collection<Declaracion> list() {

        TypedQuery<Declaracion> q = em.createQuery("SELECT d FROM Declaracion d ", Declaracion.class);
        return q.getResultList();

    }
    
    public Declaracion findById(Long id) {
        return em.find(Declaracion.class, id);
    }
    
    public Declaracion findDeclaracionBase() {

            TypedQuery<Declaracion> q = em.createQuery("SELECT d FROM Declaracion d WHERE d.esBase = true", Declaracion.class);
    
            return q.getSingleResult();
    
    }

}