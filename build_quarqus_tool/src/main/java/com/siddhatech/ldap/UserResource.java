package com.siddhatech.ldap;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/user")
public class UserResource {

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @RolesAllowed("standardRole")
//    public String userResource(@Context SecurityContext securityContext) {
//        return securityContext.getUserPrincipal().getName();
//    }
}
