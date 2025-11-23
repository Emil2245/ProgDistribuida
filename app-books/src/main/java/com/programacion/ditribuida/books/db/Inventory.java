package com.programacion.ditribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString(exclude = "book")
public class Inventory {
    @Id
    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Book book;
    private Integer sold;
    private Integer supplied;

    private Integer version;
}
