package com.programacion.ditribuida.autors.rest;

import com.programacion.ditribuida.autors.db.Author;
import com.programacion.ditribuida.autors.repo.AuthorRepo;
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
public class AuthorRest {
    @Inject
    AuthorRepo authorRepo;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer httpPort;

    @GET
    public List<Author> findAll() {
        return authorRepo.listAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {

        return authorRepo.findByIdOptional(id)
                .map(obj -> {
                    obj.setName(obj.getName() + " - " + httpPort);
                    return obj;
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/find/{isbn}")
    public List<Author> findByBook(@PathParam("isbn") String isbn) {
        return authorRepo.findByBook(isbn).stream()
                .map(obj -> {
                    var newName = String.format("%s - %s", obj.getName(), httpPort);
                    obj.setName(newName);
                    return obj;
                }).toList();
    }

    @GET
    @Path("/test")
    public String test() {
        Config config = ConfigProvider.getConfig();
        config.getConfigSources()
                .forEach(obj -> System.out.printf("%d ->s \n", obj.getOrdinal(), obj.getName()));

        return "ok";
    }
}
