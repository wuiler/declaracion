package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Tipo;


@ApplicationScoped
public class TipoRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Tipo data) {
        em.persist(data);	
    }

    public Collection<Tipo> list() {

        TypedQuery<Tipo> q = em.createQuery("SELECT t FROM Item t",Tipo.class);
        return q.getResultList();

    }

    public Tipo findById(Integer id) {
        return em.find(Tipo.class, id);
    }
    
    
	
}