package com.accenture.catalogue.modal;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BookDetails {

	private Long bookID;
	
	@NotNull(message = "Title is invalid")
	private String title;
	
	@NotNull(message = "Author is invalid")
	private String author;

	@NotNull(message = "isbn is invalid")
	@Pattern(regexp = "[0-9]+", message = "ISBN should be only digits")
	@Size(min = 13, max = 13, message = "Please provide 13 digit value for ISBN")
	private String isbn;

	@NotNull(message = "Date is invalid")
	@JsonFormat(pattern = "dd/mm/yyyy")
	private Date publicationDate;
	
	public Long getBookID() {
		return bookID;
	}

	public void setBookID(Long bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}	

}
