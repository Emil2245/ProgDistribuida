package com.programacion.ditribuida.books.rest;

import com.programacion.ditribuida.books.clients.AuthorRestClient;
import com.programacion.ditribuida.books.clients.CustomersRestClient;
import com.programacion.ditribuida.books.dto.BookDTO;
import com.programacion.ditribuida.books.repo.BookRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.stork.Stork;
import io.smallrye.stork.api.Service;
import io.smallrye.stork.api.ServiceInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/books")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class BookRest {
    @Inject
    BookRepository bookRepository;

    @Inject
    ModelMapper mapper;

    @Inject
    @RestClient
    AuthorRestClient authorRestClient;
    @Inject
    @RestClient
    CustomersRestClient customersRestClient;

    AtomicInteger index = new AtomicInteger(0);

//    @PostConstruct
//    void init(){
//        var authorServer = "http://localhost:8070";
//
//        client = RestClientBuilder.newBuilder()
//                .baseUri(authorServer)
//                .build(AuthorRestClient.class);
//    }
    @GET
    @Path("/test2")
    public List<Object> test2() {
        return  customersRestClient.findAll();
    }

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {

        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    System.out.println("Buscando autores para el libro con ISBN: " + isbn);
                    var authors = authorRestClient.findByBook(isbn);
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
                            return dto;
                        }
                ).map(book -> {
                            var authors = authorRestClient.findByBook(book.getIsbn());
                            book.setAuthors(List.of());
                            book.setAuthors(authors);
                            return book;
                        }
                ).toList();
    }

    @GET
    @Path("/test")
    public Response test() {
        Stork stork = Stork.getInstance();


        //--------------------- imprimir el contenido del registro:
        Map<String, Service> services = stork.getServices();

        services.entrySet().forEach(it -> {
            String key = it.getKey();
            Service service = it.getValue();
            System.out.println("--grupo: " + key);
            Multi<ServiceInstance> instances = service.getInstances()
                    .onItem()
                    .transformToMulti(items -> Multi.createFrom()
                            .iterable(items));

//            instances.subscribe().with(item -> {
//                System.out.println("   -> instancia: " + item.getHost() + ":" + item.getPort());
//            });

        });
        //--------------------- buscar un servico, seleccionar instancia, balancear:

        Service service = stork.getService("authors-api");
        List<ServiceInstance> instances = service.getInstances().await().indefinitely();

        int curlIndex = index.getAndIncrement() % instances.size();
        var instancia = instances.get(curlIndex);
        System.out.println("   -> instancia: " + instancia.getHost() + ":" + instancia.getPort());


        return Response.ok("ok").build();
    }

}
