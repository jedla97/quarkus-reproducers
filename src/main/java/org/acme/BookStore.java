package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.model.Book;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ApplicationScoped
public class BookStore implements PanacheRepository<Book> {
    public Page<Book> findPaged(Pageable pageable) {
        return new PageImpl<>(this.findAll().page(pageable.getPageNumber(), pageable.getPageSize()).list());
    }
}
