/**
 * 
 */
package com.accenture.catalogue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.accenture.catalogue.entity.Book;
import com.accenture.catalogue.modal.BookDetails;
import com.accenture.catalogue.repository.BookRepository;

@SpringBootTest
public class BookServiceImplTest {
	
	@InjectMocks
	private BookServiceImpl service;
	
	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private BookDetails bookDetails;
	
	@Mock
	private Book book1;	
	
	@Mock
	private Book book2;	
	
	@Mock
	private ModelMapper modelMapper;
	
	private Long bookID = 1L;
	
	private String title = "title";
	
	private String author = "author";
	
	private String isbn = "isbn";
	
	private List<Book> books = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		books.add(book1);
		books.add(book2);
		
		Mockito.when(bookRepository.saveOrUpdateBook(book1)).thenReturn(book1);
		Mockito.when(bookRepository.findById(bookID)).thenReturn(book1);
		Mockito.when(bookRepository.findAll(title, author, isbn)).thenReturn(books);
		Mockito.when(modelMapper.map(bookDetails, Book.class)).thenReturn(book1);
		Mockito.when(modelMapper.map(book1, BookDetails.class)).thenReturn(bookDetails);
		ReflectionTestUtils.setField(service, "modelMapper", modelMapper);
		
	}
	
	
	@Test
	void testAddBook() {
	   BookDetails newBook = service.addBook(bookDetails);
	   assertNotNull(newBook);
	   assertEquals(bookDetails, newBook);
	}
	
	@Test
	void testDeleteBook() throws Exception {
		service.deleteBook(bookID);
	}

	
	@Test
	void testUpdateBook() throws Exception {
		BookDetails updatedBook = service.updateBook(bookID, bookDetails);
		assertNotNull(updatedBook);
		assertEquals(bookDetails, updatedBook);
	}
	
	@Test
	void testListBooks() {
		List<Book> books = service.listBooks(title, author, isbn);
		assertNotNull(books);
		assertFalse(books.isEmpty());
	}

}
