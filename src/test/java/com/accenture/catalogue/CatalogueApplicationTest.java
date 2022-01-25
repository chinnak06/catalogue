package com.accenture.catalogue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.accenture.catalogue.entity.Book;
import com.accenture.catalogue.modal.BookDetails;
import com.accenture.catalogue.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CatalogueApplicationTest {

	private static final String  INVALID_TITLE = " Title is invalid.";
	private static final String  INVALID_ISBN = " Please provide 13 digit value for ISBN.";	
	private static final String RECORD_NOT_FOUND = "Record not found";
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	private BookService service;
	
	private BookDetails bookDetails;
	
	private Book book;	
		
	@Mock
	private ModelMapper modelMapper;
	
	private Long bookID = 1L;
	
	private String title = "title";
	
	private String author = "author";
	
	private String isbn = "9876543217654";
	
	private String json;
	
	private List<Book> books = new ArrayList<>();
	
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws Exception {
		createMock();
        Mockito.when(service.listBooks(title, author, isbn)).thenReturn(books);	
        Mockito.when(service.updateBook(bookID, bookDetails)).thenReturn(bookDetails);
	}
	
	@Test
	public void testListBooks() throws Exception {
        mockMvc.perform(get("/books"))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void testAddBookSuccess() throws Exception {		
		String json = mapper.writeValueAsString(bookDetails);		
		mockMvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", Matchers.equalTo(title)));
		
	}
	
	@Test
	public void testAddBookFailInvalidTitle() throws Exception {
		bookDetails.setTitle(null);
		String json = mapper.writeValueAsString(bookDetails);		
		mockMvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo(INVALID_TITLE)));
		
	}
	
	@Test
	public void testAddBookFailInvalidISBN() throws Exception {
		bookDetails.setIsbn("34234");
		String json = mapper.writeValueAsString(bookDetails);		
		mockMvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.equalTo(INVALID_ISBN)));		
	}
	
	@Test
	public void testRecordNotFound() throws Exception {		
		String json = mapper.writeValueAsString(bookDetails);	
		mockMvc.perform(put("/books/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", Matchers.equalTo(RECORD_NOT_FOUND)));
	}
	
	private void createMock() {
		book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setIsbn(isbn);
		
		bookDetails = new BookDetails();
		bookDetails.setTitle(title);
		bookDetails.setAuthor(author);
		bookDetails.setIsbn(isbn);
		bookDetails.setPublicationDate(new Date());
        books.add(book);
	}
}
