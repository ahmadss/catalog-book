package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.domain.Book;
import com.subrutin.catalog.dto.BookCreateRequestDTO;
import com.subrutin.catalog.dto.BookDetailResponseDTO;
import com.subrutin.catalog.dto.BookListResponseDTO;
import com.subrutin.catalog.dto.BookUpdateRequestDTO;
import com.subrutin.catalog.dto.ResultPageResponseDTO;

public interface BookService {
	public BookDetailResponseDTO findBookDetailById(String Id);
	
	public List<BookDetailResponseDTO> findBookList();
	
	public void createNewBook(BookCreateRequestDTO dto);
	
	public void updateBook(long bookId, BookUpdateRequestDTO dto);
	
	public void deleteBook(Long Id);
	
	public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy, String direction, String publisherName, String bookTitle);
}

