package com.accenture.catalogue.service;

import java.util.List;

import com.accenture.catalogue.entity.Book;
import com.accenture.catalogue.exception.NoDataException;
import com.accenture.catalogue.modal.BookDetails;

public interface BookService {

	public BookDetails addBook(BookDetails book);
	
	public void deleteBook(Long id) throws NoDataException;

	public BookDetails updateBook(Long id, BookDetails bookDetials) throws NoDataException;	

	public List<Book> listBooks(String title, String author, String isbn);
}
