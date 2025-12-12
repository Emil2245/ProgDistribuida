package com.programacion.ditribuida.autors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthorsLifecycle {

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
        System.out.println("*******************AuthorsLifecycle init()");
        System.out.println(vertx);
        try {

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-authors")
                    .setId("app-authors-" + appPort)
                    .setAddress("127.0.0.1")
                    .setPort(appPort);

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
        System.out.println("*******************AuthorsLifecycle stop()");
        try {

            ConsulClientOptions options = new ConsulClientOptions()
                    .setHost(consulHost)
                    .setPort(consulPort);

            ConsulClient client = ConsulClient.create(vertx, options);

            String serviceId = "app-authors-" + appPort;

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
