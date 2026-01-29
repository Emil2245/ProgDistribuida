package com.programacion.distribuida.recommend.servicios;

import com.programacion.distribuida.recommend.dto.BookRecDto;

import java.util.List;

public interface BooksAiService {
    List<BookRecDto> recommend(String title);
}