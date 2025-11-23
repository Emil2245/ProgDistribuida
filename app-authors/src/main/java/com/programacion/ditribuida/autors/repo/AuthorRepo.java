package com.programacion.ditribuida.autors.repo;

import com.programacion.ditribuida.autors.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorRepo implements PanacheRepositoryBase<Author,Integer> {
    public List<Author> findByBook(String isbn) {
        return this.list(
                "select o.author from BookAuthor o where o.id.bookIsbn=?1",
                isbn
        );
    }
}
