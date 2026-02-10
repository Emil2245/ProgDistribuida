package com.programacion.distribuida.recommend;

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

            registration.getService().setTags(
                    List.of(
                            "traefik.enable=true",
                            "traefik.http.routers.recommend.rule=PathPrefix(`/app-recommend`)",
                            "traefik.http.routers.recommend.priority=10",
                            "traefik.http.middlewares.recommend-stripprefix.stripPrefix.prefixes=/app-recommend",
                            "traefik.http.routers.recommend.middlewares=recommend-stripprefix"));
        };
    }
}
