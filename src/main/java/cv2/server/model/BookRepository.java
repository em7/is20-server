package cv2.server.model;

import java.util.List;
import java.util.Optional;

/**
 * Repository for storing books
 */
public interface BookRepository {

    /**
     * Fetches a single book by its ID
     *
     * @param id Unique ID of the book
     * @return Found book or empty if was not found
     */
    Optional<Book> bookById(long id);

    /**
     * Fetches all books from the data source.
     *
     * @return A list of all books or an empty list
     */
    List<Book> allBooks();
}
