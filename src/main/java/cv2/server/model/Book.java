package cv2.server.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * An existing book.
 */
@Schema(description = "A detail of the book.")
public class Book {
    @Schema(description = "ID of the book", required = true, example = "1")
    private final long id;

    @Schema(description = "Title of the book", required = true, example = "Catch XXII")
    private final String title;

    public Book(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
