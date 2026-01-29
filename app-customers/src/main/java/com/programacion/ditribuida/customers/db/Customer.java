package com.programacion.ditribuida.customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {
    @Id
    private Long id;

    private String name;
    private String email;
    private Integer version;

    @OneToMany(mappedBy = "customer")
    private List<PurcharseOrder> purcharseOrders = new ArrayList<>();
}
