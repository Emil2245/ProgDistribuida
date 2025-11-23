package com.programacion.ditribuida.books.clients;



import com.programacion.ditribuida.books.dto.AuthorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;


@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/authors")
public interface AuthorRestClient {


    @GET
    @Path("/find/{isbn}")
    public List<AuthorDTO> findByBook(@PathParam("isbn") String isbn) ;
}
