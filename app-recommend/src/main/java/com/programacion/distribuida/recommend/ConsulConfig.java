package com.programacion.distribuida.recommend;

import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConsulConfig {
    @Bean
    public ConsulRegistrationCustomizer consulRegistrationCustomizer() {
        return registration -> {
            registration.getService()
                .setTags(
                    List.of(
                            "traefik.enable=true",
                            "traefik.http.routers.recommend.rule=PathPrefix(`/app-recommend`)",
                            "traefik.http.middlewares.recommend-stripprefix.stripPrefix.prefixes=/app-recommend",
                            "traefik.http.routers.recommend.middlewares=recommend-stripprefix"
                    )
            );
        };
    }
}
