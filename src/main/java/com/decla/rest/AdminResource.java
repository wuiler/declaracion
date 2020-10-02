
package com.decla.rest;

import java.security.Principal;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.oidc.IdToken;


@Path("/admin")
public class AdminResource {

    @Inject
    @IdToken
    JsonWebToken idToken;

    @Inject
    JsonWebToken jwt;

    @Path("/logout")
    public String logout() {
        return "chau";
    }

    @GET
//    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/subject")
    public String adminSubject() {
        Set<String> l = jwt.getClaimNames();
        for (String string : l) {         
            //System.out.println("String" + string + " - " + l.);
        }

        System.out.println("String: " + jwt.getClaim("family_name"));
        System.out.println("String: " + jwt.getClaim("email"));
        return "Access for subject " + jwt.getSubject() + " is granted" + jwt.getName();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();  

        String name = caller == null ? "anonymous" : caller.getName();  
        
        return "hello" + name +  " ----- " + idToken.getClaim("preferred_username");// + " - "+ idToken.getClaim("birthdate").toString() ;
    }

    @GET()
    @Path("permit-all")
    @PermitAll 
    @Produces(MediaType.TEXT_PLAIN)
    public String helloall(@Context SecurityContext ctx) {
        return getResponseString(ctx); 
    }

    @GET
    @Path("roles-allowed") 
    @RolesAllowed({"admin"}) 
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        System.out.println(ctx.toString());
        System.out.println("Wuiler");
        return getResponseString(ctx) + ", birthdate: ";// + jwt.getClaim("birthdate").toString(); 
    }    

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) { 
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) { 
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName(); 
        }
        return String.format("hello + %s,"
            + " isHttps: %s,"
            + " authScheme: %s,"
            + " hasJWT: %s",
            name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt()); 
    }

    private boolean hasJwt() {
	    return jwt.getClaimNames() != null;
    }    
}