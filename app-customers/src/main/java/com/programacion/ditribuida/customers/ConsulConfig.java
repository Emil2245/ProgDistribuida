package com.programacion.ditribuida.customers;

import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConsulConfig {
    @Bean
    public ConsulRegistrationCustomizer consulRegistrationCustomizer() {
        return registration -> registration.getService()
                .setTags(
                        List.of(
                                "traefik.enable=true",
                                "traefik.http.routers.customers.rule=PathPrefix(`/app-customers`)",
                                "traefik.http.middlewares.customers-stripprefix.stripPrefix.prefixes=/app-customers",
                                "traefik.http.routers.customers.middlewares=customers-stripprefix"
                        )
                );
    }
}
