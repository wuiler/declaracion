package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Espacio;

import org.jboss.logging.Logger;

@ApplicationScoped
public class EspacioRepository {

    private static final Logger LOG = Logger.getLogger(EntidadRepository.class);
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Espacio data) {
        em.persist(data);	
    }

    public Collection<Espacio> list() {

        TypedQuery<Espacio> q = em.createQuery("SELECT e FROM Espacio e ",Espacio.class);
        return q.getResultList();

    }

    public Espacio findById(long id) {

        Espacio e = em.find(Espacio.class, id);

        if (e==null) {
            throw new EntityNotFoundException("no se encuentra el negocio " + id);
        }

        return e;

    }

}