package com.programacion.ditribuida.books.rest;

import com.programacion.ditribuida.books.clients.AuthorRestClient;
import com.programacion.ditribuida.books.dto.BookDTO;
import com.programacion.ditribuida.books.repo.BookRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.modelmapper.ModelMapper;

import java.util.List;

@Path("/books")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class BookRest {
    @Inject
    BookRepository bookRepository;

    @Inject
    ModelMapper mapper;

    AuthorRestClient client;

    @PostConstruct
    void init(){
        var authorServer = "http://localhost:8070";

        client = RestClientBuilder.newBuilder()
                .baseUri(authorServer)
                .build(AuthorRestClient.class);
    }

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {

        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    var authors = client.findByBook(isbn);

                    var dto = new BookDTO();
                    mapper.map(book, dto);
                    dto.setAuthors(authors);
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

//        var obj = bookRepository.findByIdOptional(isbn);
//        if (obj.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        BookDTO ret = new BookDTO();
//
//        mapper.map(obj.get(), ret);
// REEMPLAZADO POR EL MAPEO AUTOMATICO
//        ret.setIsbn(obj.get().getIsbn());
//        ret.setTitle(obj.get().getTitle());
//        ret.setPrice(obj.get().getPrice());
//        if (obj.get().getInventory() != null) {
//            ret.setInventorySold(obj.get().getInventory().getSold());
//            ret.setInventorySupplied(obj.get().getInventory().getSupplied());
//        }

//        ret.setAuthors(List.of());
//
//        return Response.ok(ret).build();

    }

    @GET
    @Path("/")
    public List<BookDTO> findAll() {
        return bookRepository.streamAll()
                .map(book -> {
                            BookDTO dto = new BookDTO();
                            mapper.map(book, dto);
                            dto.setAuthors(List.of());
                            return dto;
                        }
                ).map(book -> {
                            book.setAuthors(List.of());
                            return book;
                        }
                ).toList();
    }

}
