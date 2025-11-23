package com.prog;


import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.http.media.jsonb.JsonbSupport;
import io.helidon.http.media.jsonp.JsonpSupport;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;

import java.time.LocalDateTime;
import java.util.Map;

public class ApplicationMain {
    //json processing
    static JsonBuilderFactory factory = Json.createBuilderFactory(Map.of());
    //acceso a datos
    static DbClient dbClient;

    static void handleHola(ServerRequest req, ServerResponse resp) {
//        var name = req.path().pathParameters().get("name");

        JsonObject response = factory.createObjectBuilder()
                .add("name", "hello" + LocalDateTime.now()).build();

        resp.send(response);
    }

    static void handleBooks(ServerRequest req, ServerResponse resp) {
//        var name = req.path().pathParameters().get("name");
//
//        Persona p = new Persona();
//        p.setNombre("Hola "+name);
//        p.setDate(LocalDateTime.now());
        dbClient.execute()
                .createGet("SELECT * FROM books")
                .execute().map(book -> {
                    Book b = new Book();
                    b.setIsbn(book.column("ISBN").getString());
                    b.setTitle(book.column("TITLE").getString());
                    return b;
                })
                .ifPresentOrElse(
                        resp::send,
                        () -> resp.status(404).send()
                );
    }

    static void handleBook(ServerRequest req, ServerResponse resp) {
        var isbn = req.path().pathParameters().get("isbn");
        dbClient.execute()
                .createGet("SELECT * FROM books WHERE ISBN = ?")
                .params(isbn)
                .execute().map(book -> {
                    Book b = new Book();
                    b.setIsbn(book.column("ISBN").getString());
                    b.setTitle(book.column("TITLE").getString());
                    return b;
                })
                .ifPresentOrElse(
                        resp::send,
                        () -> resp.status(404).send()
                );

    }

    public static void main(String[] args) {
        Config config = Config.create();
        var configHttp = config.get("server");
        var configDb = config.get("db");

        //acceso a datos
        dbClient = DbClient.builder().config(configDb).build();


        WebServer.builder()
//                .port(8080)
                .config(configHttp)
                .mediaContext(it -> it

                        .mediaSupportsDiscoverServices(true)
                        .addMediaSupport(JsonbSupport.create())
                        .addMediaSupport(JsonpSupport.create())
                )
                .routing(it -> it
                        .get("/hola/{name}",
                                ApplicationMain::handleHola)
                        .get("/books",
                                ApplicationMain::handleBooks)
                        .get("/books/{isbn}",
                                ApplicationMain::handleBook)
                )
                .build()
                .start();
    }

}