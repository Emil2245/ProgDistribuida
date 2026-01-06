package com.programacion.ditribuida.customers.rest;

import com.programacion.ditribuida.customers.dto.CustomerDto;
import com.programacion.ditribuida.customers.dto.PurcharseOrderDto;
import com.programacion.ditribuida.customers.repo.CustomersRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
@AllArgsConstructor
public class CustomerRest {

    private final CustomersRepo repo;

    @GetMapping
    public List<CustomerDto> findAll() {
        return repo.findAll().stream()
                .map(customer -> {
                    // Map de ordenes de compra a DTO
                    var purchaseOrders = customer.getPurcharseOrder().stream()
                            .map(po -> PurcharseOrderDto.builder()
                                    .id(po.getId())
                                    .placedOn(po.getPlacedOn())
                                    .deliveredOn(po.getDeliveredOn())
                                    .total(po.getTotal())
                                    .build()
                            )
                            .toList();

                    // Map de cliente a DTO
                    return CustomerDto.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .email(customer.getEmail())
//                                .purcharseOrders(purchaseOrders)
                            .build();
                })
                .toList();
    }
}


