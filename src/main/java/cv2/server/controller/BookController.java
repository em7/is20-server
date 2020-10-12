package cv2.server.controller;

import cv2.server.api.BookNotFoundException;
import cv2.server.model.Book;
import cv2.server.model.BookRepository;
import cv2.server.model.BookToAdd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RESTful API for books, in path /books
 */
@RestController()
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Operation(summary = "Gets a list of all books in the shop.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "A list of all books",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
    })
    @GetMapping("")
    public List<Book> getBooks() {
        return bookRepository.allBooks();
    }

    @Operation(summary = "Gets a book by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "A detail of the book",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400",
                description = "A book with requested ID does not exist or the book could not be fetched.",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/book/{id}")
    public Book getBook(@Parameter(description = "ID of the book to fetch.")
                        @PathVariable long id){
        return bookRepository.bookById(id)
                .orElseThrow(() -> new BookNotFoundException(
                    String.format("A book with ID '%d' was not found.", id)));
    }


    @Operation(summary = "Creates a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "A newly created book.",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400",
                description = "Book could not be successfully added to the repository.",
                content = {@Content(schema = @Schema(implementation = String.class))})
    })
    @PostMapping("/book")
    public Book addBook(@Parameter(description = "A book to be created.")
                        @RequestBody BookToAdd bookToAdd) {
        final long bookId = bookRepository.addBook(bookToAdd);
        return bookRepository.bookById(bookId)
                .orElseThrow(() -> new BookNotFoundException("A book was not successfully added to the repository."));
    }
}
