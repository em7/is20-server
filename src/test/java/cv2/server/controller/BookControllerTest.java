package cv2.server.controller;

import cv2.server.model.Book;
import cv2.server.model.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@WebAppConfiguration
public class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    BookRepository bookRepository;

    @Test
    public void getBooks() throws Exception {
        List<Book> allBooks = new ArrayList<>(2);
        allBooks.add(new Book(1, "Title 1"));
        allBooks.add(new Book(2, "Title 2"));

        when(bookRepository.allBooks()).thenReturn(allBooks);

        mvc.perform(get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", iterableWithSize(2)))
                .andExpect(jsonPath("$[0]['title']", containsString("Title 1")))
                .andExpect(jsonPath("$[1]['title']", containsString("Title 2")));
    }
}
