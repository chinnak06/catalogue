/**
 * Books Controller.
 */
package com.accenture.catalogue.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.catalogue.entity.Book;
import com.accenture.catalogue.exception.NoDataException;
import com.accenture.catalogue.modal.BookDetails;
import com.accenture.catalogue.service.BookService;

@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	private BookService service;

	/**
	 * Adds a book.
	 * 
	 * @param book
	 * @return the book created.
	 */
	@PostMapping
	public ResponseEntity<Object> addBook(@Valid @RequestBody BookDetails book) {
		BookDetails newBook = service.addBook(book);
		ResponseEntity<Object> response = new ResponseEntity<>(newBook, HttpStatus.CREATED);

		return response;
	}

	/**
	 * Deletes book.
	 * 
	 * @param bookID
	 * @return
	 * @throws NoDataException
	 */
	@DeleteMapping("/{bookID}")
	public ResponseEntity<Object> deleteBook(@PathVariable("bookID") Long bookID) throws NoDataException {
		service.deleteBook(bookID);
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

		return response;
	}
	
	/**
	 * Updates book.
	 * 
	 * @param id
	 * @param book
	 * @return
	 * @throws NoDataException
	 */
	@PutMapping("/{bookID}")
	public ResponseEntity<Object> updateBook(@PathVariable("bookID") Long id, @Valid @RequestBody BookDetails bookDetails)
			throws NoDataException {
		BookDetails updatedBook = service.updateBook(id, bookDetails);
		ResponseEntity<Object> response = new ResponseEntity<>(updatedBook, HttpStatus.OK);

		return response;
	}

	/**
	 * List books.
	 * 
	 * @return list of books
	 */
	@GetMapping public ResponseEntity<Object> listBooks(
					@RequestParam(required = false) String title,
					@RequestParam(required = false) String author, 
					@RequestParam(required = false) String isbn) {
		List<Book> books = service.listBooks(title, author, isbn);
		ResponseEntity<Object> response = new ResponseEntity<>(books, HttpStatus.OK);
	 
		return response;
	 }
	 

}
