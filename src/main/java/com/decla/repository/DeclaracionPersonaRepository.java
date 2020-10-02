package com.decla.repository;

import java.math.BigInteger;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.DeclaracionPersona;
import com.decla.model.Entidad;

@ApplicationScoped
public class DeclaracionPersonaRepository {
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(DeclaracionPersona data) {
        em.persist(data);	
    }

    public Collection<DeclaracionPersona> list() {

        TypedQuery<DeclaracionPersona> q = em.createQuery("SELECT d FROM DeclaracionPersona d ",DeclaracionPersona.class);
        return q.getResultList();

    }

    public Collection<DeclaracionPersona> listByAllEntidad(Collection<Entidad> listaEntidad) {

        TypedQuery<DeclaracionPersona> q = em.createQuery("SELECT d FROM DeclaracionPersona d WHERE d.entidad IN (:listaEntidad)",DeclaracionPersona.class)
                                                .setParameter("listaEntidad", listaEntidad);
        return q.getResultList();

    }

    public Collection<DeclaracionPersona> listByEntidad(Entidad entidad) {

        TypedQuery<DeclaracionPersona> q = em.createQuery("SELECT d FROM DeclaracionPersona d WHERE d.entidad = :entidad",DeclaracionPersona.class)
                                                .setParameter("entidad", entidad);
        return q.getResultList();

    }


    public Long countByEntidad(Entidad entidad) {

        TypedQuery<Long> q = em.createQuery("SELECT count(d.mail) FROM DeclaracionPersona d WHERE d.entidad = :entidad",Long.class)
                                                .setParameter("entidad", entidad);
        return q.getSingleResult();

    }

    public Long countByAllEntidad(Collection<Entidad> listaEntidad) {

        TypedQuery<Long> q = em.createQuery("SELECT count(*) FROM DeclaracionPersona d WHERE d.entidad IN (:listaEntidad)",Long.class)
                                                .setParameter("listaEntidad", listaEntidad);
        return q.getSingleResult();

    }


    public BigInteger countByTodayAndEntidad(Entidad entidad) {
        long idEntidad = entidad.getIdEntidad();
        Query q = em.createNativeQuery("SELECT count(*) FROM declaracionpersona WHERE idEntidad = :idEntidad AND date_part('day',fecha) =  date_part('day',now())")
                                                .setParameter("idEntidad", idEntidad);
        return (BigInteger) q.getSingleResult();

    }    

    public BigInteger countByYesterdayAndEntidad(Entidad entidad) {
        long idEntidad = entidad.getIdEntidad();
        Query q = em.createNativeQuery("SELECT count(*) FROM declaracionpersona WHERE idEntidad = :idEntidad AND date_part('day',fecha) =  date_part('day',(now()-interval '1 day'))")
                                                .setParameter("idEntidad", idEntidad);
        return (BigInteger) q.getSingleResult();

    }    

    public DeclaracionPersona findById(long id) {

        DeclaracionPersona e = em.find(DeclaracionPersona.class, id);

        if (e==null) {
            throw new EntityNotFoundException("no se encuentra : " + id);
        }

        return e;

    }
    	
}