package com.programacion.distribuida.recommend;

import com.programacion.distribuida.recommend.servicios.BooksAiService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppRecommendMain {

    public static void main(String[] args) {
        SpringApplication.run(AppRecommendMain.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            ChatModel chatModel,
            BooksAiService booksAiService
    ) {
        return args -> {
            System.out.println(chatModel);

            var res = booksAiService.recommend("El Quijote");
            System.out.println("Recomendacion: " + res);
        };
    }
}
