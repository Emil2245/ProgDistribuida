package com.programacion.ditribuida.books;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;

@ApplicationScoped
public class BooksLifecycle {


    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "localhost")
    String consulHost;
    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consulPort;
    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer appPort;

    public void init(@Observes StartupEvent event, Vertx vertx) {
        System.out.println("*******************BooksLifecycle init()");
        System.out.println(vertx);
        try {

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            var ipAddress = InetAddress.getLocalHost().getHostAddress();
            var urlcheck = String.format("http://%s:%d/ping", ipAddress, appPort);
            var checkOptions = new CheckOptions().setHttp(urlcheck).setInterval("10s").setDeregisterAfter("10s");

            var tags = List.of("traefik.enable= true",
                    "traefik.http.routers.books.rule=PathPrefix(`/app-books`)",
                    "traefik.http.middlewares.books-stripprefix.stripPrefix.prefixes=/app-books",
                    "traefik.http.routers.books.middlewares=books-stripprefix"
            );

            ConsulClient client = ConsulClient.create(vertx, options);

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-books")
                    .setId("app-books-" + appPort)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            client.registerService(serviceOptions).onSuccess(it -> {
                System.out.println("Service registered: " + serviceOptions.getName());
            }).onFailure(err -> {
                System.out.println("Service registration failed: " + err.getMessage());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(@Observes ShutdownEvent ev, Vertx vertx) {
        System.out.println("*******************BooksLifecycle stop()");
        try {

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            String serviceId = "app-books-" + appPort;

            client.deregisterService(serviceId)
                    .onSuccess(it -> {
                        System.out.println("Service deregistered: " + serviceId);
                    })
                    .onFailure(err -> {
                        System.out.println("Service deregistration failed: " + err.getMessage());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


