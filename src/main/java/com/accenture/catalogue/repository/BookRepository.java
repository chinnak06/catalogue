package com.accenture.catalogue.repository;

import java.util.List;

import com.accenture.catalogue.entity.Book;

public interface BookRepository {

	public Book saveOrUpdateBook(Book book);

	public void deleteBook(Book book);

	public List<Book> findAll(String title, String author, String isbn);

	public Book findById(Long id);

}
