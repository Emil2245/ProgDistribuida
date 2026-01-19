package com.programacion.ditribuida.books.clients;



import com.programacion.ditribuida.books.dto.AuthorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;


@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/authors")
//@RegisterRestClient(configKey = "AuthorRestClient")
@RegisterRestClient(baseUri = "stork://authors-api")
public interface AuthorRestClient {

    @GET
    @Path("/find/{isbn}")
    @Retry(maxRetries = 4, delay = 100)
    @Fallback(fallbackMethod = "findByBookFallback")
    List<AuthorDTO> findByBook(@PathParam("isbn") String isbn) ;

    default List<AuthorDTO> findByBookFallback(String isbn){
        var dto = new AuthorDTO();
        dto.setId(0);
        dto.setName("NotFound");
        return List.of(dto);
    }
}
