package com.decla.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
@Path("/tester")
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class);

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    

    @GET
    @Path("/me")
    //@RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @NoCache
    public User me() {
        LOG.infof("securityIdentity.getPrincipal().getName() : %s" , securityIdentity.getPrincipal().toString());
        //System.out.println("securityIdentity.getPrincipal().getName() : " + securityIdentity.getPrincipal().getName());
        return new User(securityIdentity);
    }

    public static class User {

        private final String userName;

        User(SecurityIdentity securityIdentity) {
            System.out.println("securityIdentity.getPrincipal().getName() : " + securityIdentity.getPrincipal().getName());
            this.userName = securityIdentity.getPrincipal().getName();
        }

        public String getUserName() {
            System.out.println("userName: " + this.userName);
            return userName;
        }
    }
}