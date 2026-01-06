package com.programacion.ditribuida.customers.db;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class PurcharseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate placedOn;
    private LocalDate deliveredOn;
    private BigDecimal total;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    private Customer customer;


}
