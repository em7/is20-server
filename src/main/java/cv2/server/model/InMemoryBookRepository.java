package cv2.server.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * In-memory repository for storing books
 */
@Repository
public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public Optional<Book> bookById(long id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    @Override
    public List<Book> allBooks() {
        return Collections.unmodifiableList(books);
    }
}
