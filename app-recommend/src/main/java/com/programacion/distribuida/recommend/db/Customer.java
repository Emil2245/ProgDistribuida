package com.programacion.ditribuida.customers.db;

import javax.annotation.processing.Generated;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

//    @OneToMany(mappedBy = "customer")
//    private List<PurcharseOrder> purcharseOrder;

}
