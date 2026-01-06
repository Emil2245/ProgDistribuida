package com.programacion.distribuida.recommend;

import com.programacion.distribuida.recommend.servicios.BooksAiService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppRecommendMain {
    private final ChatModel chatmodel;

    public AppRecommendMain(ChatModel chatmodel) {
        this.chatmodel = chatmodel;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppRecommendMain.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ChatModel chatmodel, BooksAiService booksAiService) {
        return (String... args) -> {
            System.out.println(chatmodel);

            var res = booksAiService.recomendar("El Quijote");
            System.out.println("Recomendacion: " + res);
        };
    }
}
