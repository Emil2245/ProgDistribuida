package com.programacion.ditribuida.customers.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurcharseOrderDto {
    @Id
    private Integer id;
    private LocalDate placedOn;
    private LocalDate deliveredOn;
    private BigDecimal total;

}
