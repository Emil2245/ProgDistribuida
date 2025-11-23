package com.prog;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hola")   // el recurso
public class HolaResource {

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hola(@PathParam("name") String name) {
        return Response.ok("Hola " + name).build();
    }
}