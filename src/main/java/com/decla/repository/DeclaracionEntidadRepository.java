package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.DeclaracionEntidad;
import com.decla.model.Entidad;

@ApplicationScoped
public class DeclaracionEntidadRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(DeclaracionEntidad data) {
        em.persist(data);	
    }

    
    public Collection<DeclaracionEntidad> list() {

        TypedQuery<DeclaracionEntidad> q = em.createQuery("SELECT de FROM DeclaracionEntidad de ",DeclaracionEntidad.class);
        return q.getResultList();

    }

    public DeclaracionEntidad findByEntidad(Entidad entidad) {

        TypedQuery<DeclaracionEntidad> q = em.createQuery("SELECT de FROM DeclaracionEntidad de WHERE de.entidad = :entidad", DeclaracionEntidad.class)
                                                .setParameter("entidad", entidad);

        return q.getSingleResult();
    
    }

    public Collection<DeclaracionEntidad> listByAllEntidad(Collection<Entidad> listaEntidad) {

        TypedQuery<DeclaracionEntidad> q = em.createQuery("SELECT de FROM DeclaracionEntidad de WHERE de.entidad IN (:listaEntidad)",DeclaracionEntidad.class)
                                                .setParameter("listaEntidad", listaEntidad);
        return q.getResultList();

    }


    public Collection<DeclaracionEntidad> listByEntidad(Entidad entidad) {

        TypedQuery<DeclaracionEntidad> q = em.createQuery("SELECT de FROM DeclaracionEntidad de WHERE de.entidad = :entidad",DeclaracionEntidad.class)
                                                .setParameter("entidad", entidad);
        return q.getResultList();

    }


}