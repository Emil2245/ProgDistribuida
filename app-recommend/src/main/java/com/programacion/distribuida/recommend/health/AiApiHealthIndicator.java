package com.programacion.distribuida.recommend.health;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component("aiApi")
public class AiApiHealthIndicator implements HealthIndicator {

    private final ChatClient chatClient;

    public AiApiHealthIndicator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public Health health() {
        Instant start = Instant.now();
        try {
            String testPrompt = "Test. Reply 'OK'";

            String response = chatClient.prompt()
                    .user(testPrompt)
                    .call()
                    .content();

            Duration duration = Duration.between(start, Instant.now());

            if (response != null && !response.trim().isEmpty()) {
                return Health.up()
                        .withDetail("aiApi", "Connected")
                        .withDetail("responseTime", duration.toMillis() + "ms")
                        .withDetail("lastCheck", Instant.now().toString())
                        .withDetail("responsePreview", response.substring(0, Math.min(30, response.length())))
                        .build();
            } else {
                return Health.down()
                        .withDetail("aiApi", "Empty response received")
                        .withDetail("responseTime", duration.toMillis() + "ms")
                        .withDetail("lastCheck", Instant.now().toString())
                        .build();
            }

        } catch (Exception e) {
            Duration duration = Duration.between(start, Instant.now());
            return Health.down()
                    .withDetail("aiApi", "Connection failed")
                    .withDetail("error", e.getMessage())
                    .withDetail("errorClass", e.getClass().getSimpleName())
                    .withDetail("responseTime", duration.toMillis() + "ms")
                    .withDetail("lastCheck", Instant.now().toString())
                    .build();
        }
    }
}