package com.programacion.ditribuida.customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_order")
@Getter
@Setter
public class PurcharseOrder {
    @Id
    private Long id;

    @Column(name = "placedon")
    private LocalDateTime placedOn;

    @Column(name = "deliveredon")
    private LocalDateTime deliveredOn;

    private Integer status;
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
