package com.programacion.ditribuida.customers.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private Integer version;

    private List<PurcharseOrderDto> purchaseOrders;

}
