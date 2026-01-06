package com.programacion.ditribuida.customers.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    
//    @OneToMany(mappedBy = "customer")
//    private List<PurcharseOrder> purcharseOrder;

}
