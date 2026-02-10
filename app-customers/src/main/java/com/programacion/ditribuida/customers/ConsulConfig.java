package com.programacion.ditribuida.customers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class ConsulConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public ConsulRegistrationCustomizer consulRegistrationCustomizer() {
        return registration -> {
            String hostname = System.getenv("HOSTNAME");
            if (hostname == null || hostname.isEmpty()) {
                hostname = "localhost";
            }

            String instanceId = applicationName + "-" + hostname + "-" + UUID.randomUUID().toString().substring(0, 8);
            registration.getService().setId(instanceId);

            // Configurar tags de Traefik
            registration.getService().setTags(
                    List.of(
                            "traefik.enable=true",
                            "traefik.http.routers.customers.rule=PathPrefix(`/app-customers`)",
                            "traefik.http.routers.customers.priority=10",
                            "traefik.http.middlewares.customers-stripprefix.stripPrefix.prefixes=/app-customers",
                            "traefik.http.routers.customers.middlewares=customers-stripprefix"
                    )
            );
        };
    }
}
