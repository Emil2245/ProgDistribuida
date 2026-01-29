package com.programacion.distribuida.recommend.rest;

import com.programacion.distribuida.recommend.servicios.BooksAiService;
import com.programacion.distribuida.recommend.dto.BookRecDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BooksRecommendRest {

    private final BooksAiService booksAiService;

    @GetMapping
    public List<BookRecDto> findRecommend(@RequestParam String title) {
        return booksAiService.recommend(title);
    }
}
