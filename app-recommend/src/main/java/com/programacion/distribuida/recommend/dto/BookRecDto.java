package com.programacion.distribuida.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRecDto {

    private String titulo;
    private String isbn;
    private String editorial;
    private String descripcion;

}
