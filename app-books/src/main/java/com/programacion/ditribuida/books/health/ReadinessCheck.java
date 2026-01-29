package com.programacion.ditribuida.books.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.sql.DataSource;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        try (var c = dataSource.getConnection()) {
            boolean valid = c.isValid(2);
            return valid
                    ? HealthCheckResponse.up("app-books-db")
                    : HealthCheckResponse.down("app-books-db");
        } catch (Exception e) {
            return HealthCheckResponse.down("app-books-db");
        }
    }
}


