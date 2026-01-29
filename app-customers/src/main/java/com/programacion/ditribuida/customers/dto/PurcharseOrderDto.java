package com.programacion.ditribuida.customers.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurcharseOrderDto {
    private Long id;
    private LocalDateTime placedOn;
    private LocalDateTime deliveredOn;
    private Integer status;
    private Integer total;

}
