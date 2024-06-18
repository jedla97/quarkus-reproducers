package org.acme;

import org.acme.model.Book;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Path("/book")
public class BookResource {
    private final BookStore bookStore;

    public BookResource(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    @GET
    @Path("/paged")
    public Page<Book> getPaged(@QueryParam("size") int size, @QueryParam("page") int page) {
        return bookStore.findPaged(PageRequest.of(page, size));
    }
}
