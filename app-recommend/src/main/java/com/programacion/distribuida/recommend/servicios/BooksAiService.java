package com.programacion.distribuida.recommend.servicios;

import com.programacion.ditribuida.customers.dto.BookRecDto;

import java.util.List;

public interface BooksAiService {
    List<BookRecDto> recommend(String title);
}