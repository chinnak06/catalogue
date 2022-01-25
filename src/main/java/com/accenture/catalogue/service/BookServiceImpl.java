package com.accenture.catalogue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.catalogue.entity.Book;
import com.accenture.catalogue.exception.NoDataException;
import com.accenture.catalogue.modal.BookDetails;
import com.accenture.catalogue.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	private Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Override
	public BookDetails addBook(BookDetails bookDetails) {
		Book newBook = repository.saveOrUpdateBook(modelMapper.map(bookDetails, Book.class));

		return modelMapper.map(newBook, BookDetails.class);

	}

	@Override
	public void deleteBook(Long id) throws NoDataException {
		Book book = repository.findById(id);
		if (book == null) {
			logger.error("No Book Found with the ID: " + id);
			throw new NoDataException("Record not found");
		}

		repository.deleteBook(book);

	}

	@Transactional
	@Override
	public BookDetails updateBook(Long id, BookDetails bookDetails) throws NoDataException {
		Book book = repository.findById(id);
		if (book == null) {
			logger.error("No Book Found with the ID: " + id);
			throw new NoDataException("Record not found");
		}
		book.setTitle(bookDetails.getTitle());
		book.setAuthor(bookDetails.getAuthor());
		book.setIsbn(bookDetails.getIsbn());
		book.setPublicationDate(bookDetails.getPublicationDate());

		repository.saveOrUpdateBook(book);

		return modelMapper.map(book, BookDetails.class);
	}

	@Override 
	public List<Book> listBooks(String title, String author, String isbn) { 
		List<Book> books = repository.findAll(title, author, isbn);
			 
		return books; 
	}

}
