package cv2.server.controller;

import cv2.server.model.Book;
import cv2.server.model.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
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
}
