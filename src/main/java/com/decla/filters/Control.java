package com.decla.filters;

import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;


public class Control implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(Control.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Context
    SecurityContext scx;

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public void filter(ContainerRequestContext context) {

        final String path = info.getPath();

        
        LOG.infof("PATH : %s - El user en SecurityContext: %s", path, scx.getUserPrincipal());

        LOG.infof("PATH : %s - securityIdentity.getPrincipal().getName() : %s", path , securityIdentity.getPrincipal().toString());        
    }
}