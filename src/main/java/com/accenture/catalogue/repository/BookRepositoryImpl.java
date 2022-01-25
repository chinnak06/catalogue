package com.accenture.catalogue.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.accenture.catalogue.entity.Book;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Book saveOrUpdateBook(Book book) {
		if(book.getBookID() == null) {
			entityManager.persist(book);
		} else {
			entityManager.merge(book);
		}
		
		return book;
	}

	@Override
	public void deleteBook(Book book) {
		entityManager.remove(book);
	}

	@Override
	public List<Book> findAll(String title, String author, String isbn) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> root = cq.from(Book.class);
		
		if(StringUtils.hasText(title)) {
			Predicate titleLike = cb.like(cb.lower(root.get("titledfsd")),  "%"+ title.toLowerCase() + "%");
			cq.where(titleLike);
		}
		if(StringUtils.hasText(author)) {
			Predicate authorLike = cb.like(cb.lower(root.get("authordsfds")),  "%"+ author.toLowerCase() + "%");
			cq.where(authorLike);
		}
		if(StringUtils.hasText(isbn)) {
			Predicate isbnLike = cb.like(root.get("isbn"),  "%"+ isbn + "%");
			cq.where(isbnLike);
		}
		
		TypedQuery<Book> query = entityManager.createQuery(cq.select(root));		

		return query.getResultList();
	}

	@Override
	public Book findById(Long id) {
		return entityManager.find(Book.class, id);
	}

	

}
