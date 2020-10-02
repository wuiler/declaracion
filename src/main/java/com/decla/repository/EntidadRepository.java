package com.decla.repository;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.decla.model.Entidad;
import com.decla.util.Crypto;

import org.jboss.logging.Logger;

@ApplicationScoped
public class EntidadRepository {

    private static final Logger LOG = Logger.getLogger(EntidadRepository.class);
    
    @Inject
    EntityManager em;

    @Transactional
    public void save(Entidad data) {
        em.persist(data);	
    }

    public Collection<Entidad> list() {

        TypedQuery<Entidad> q = em.createQuery("SELECT e FROM Entidad e ",Entidad.class);
        return q.getResultList();

    }

    public Collection<Entidad> listPublic() {

        TypedQuery<Entidad> q = em.createQuery("SELECT e FROM Entidad e WHERE e.esPublica = true",Entidad.class);
        return q.getResultList();

    }

    public Entidad findById(long id) {

        Entidad e = em.find(Entidad.class, id);

        if (e==null) {
            throw new EntityNotFoundException("no se encuentra el negocio " + id);
        }

        return e;

    }
    	
    public Entidad findByHash(String hash) throws Exception {

        String idEntidad = null;

        try {
            idEntidad = Crypto.b64Decode(hash);
        } catch (Exception e) {
            throw new Exception("Id incorrecto : " + hash);
        }
                
        return findById(Long.valueOf(idEntidad));

    }

    public Entidad findByOwner(String owner) {
        try {
            TypedQuery<Entidad> q = em.createQuery("SELECT e FROM Entidad e WHERE e.creadoPor = :creadoPor", Entidad.class)
                .setParameter("creadoPor", owner);
                return q.getSingleResult();
        } catch(NoResultException e) { 
            return null;
        }
    }

    public Collection<Entidad> findAllByOwner(String owner) {
        try {
            TypedQuery<Entidad> q = em.createQuery("SELECT e FROM Entidad e WHERE e.creadoPor = :creadoPor", Entidad.class)
                .setParameter("creadoPor", owner);
                return q.getResultList();
        } catch(NoResultException e) { 
            return null;
        }
    }

}