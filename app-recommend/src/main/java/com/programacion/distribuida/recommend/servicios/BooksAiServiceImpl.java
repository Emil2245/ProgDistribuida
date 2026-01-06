package com.programacion.distribuida.recommend.servicios;

import com.programacion.ditribuida.customers.dto.BookRecDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksAiServiceImpl implements BooksAiService {

    public final ChatClient chatClient;

    public BooksAiServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public List<BookRecDto> recommend(String title) {
        String promptText = """
                recomendar 2 libros para alguien que ingresa %s. Devuelve exclusivamente un json con el siguiente formato:
                [
                    {
                        "titulo": "titulo del libro 1",
                        "isbn": "isbn del libro 1",
                        "editorial": "editorial del libro 1",
                        "descripcion": "descripcion del libro 1"
                    }
                ]
                No incluyas ningun texto alternativo o adicional fuera del json.
                """.formatted(title);

        return chatClient.prompt()
                .user(userSpec -> userSpec.text(promptText))
                .call()
                .entity(new ParameterizedTypeReference<List<BookRecDto>>() {
                });
    }

}