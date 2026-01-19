package org.acme;

import io.quarkus.infinispan.client.Remote;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionManager;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.infinispan.client.hotrod.RemoteCache;

@Path("/books")
public class BookResource {

    @Inject
    @Remote("books")
    RemoteCache<String, Book> booksCache;

    @POST
    @Path("/commit")
    @Produces(MediaType.TEXT_PLAIN)
    public String addBooksWithCommit() {
        TransactionManager tm = booksCache.getTransactionManager();
        if (tm == null) {
            throw new RuntimeException(
                    "TransactionManager is null - check cache transaction-mode configuration");
        }

        try {
            tm.begin();
            booksCache.put("book-1", new Book("Book 1"));
            booksCache.put("book-2", new Book("Book 2"));
            tm.commit();
            return "Committed: 2 books added";
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to commit transaction: " + e.getMessage(),
                    e);
        }
    }

    @DELETE
    @Path("/clear")
    public void clearCache() {
        booksCache.clear();
    }
}
