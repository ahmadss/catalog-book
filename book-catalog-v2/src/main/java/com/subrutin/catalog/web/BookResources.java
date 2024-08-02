package com.subrutin.catalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.BookCreateRequestDTO;
import com.subrutin.catalog.dto.BookDetailResponseDTO;
import com.subrutin.catalog.dto.BookListResponseDTO;
import com.subrutin.catalog.dto.BookUpdateRequestDTO;
import com.subrutin.catalog.dto.ResultPageResponseDTO;
import com.subrutin.catalog.service.BookService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BookResources {
	
	private final BookService bookService;
			
	@GetMapping("/v1/book/{bookId}")
	public ResponseEntity<BookDetailResponseDTO> findBookDetail(@PathVariable("bookId") String id){
		log.info("test", "test");
		BookDetailResponseDTO  result = bookService.findBookDetailById(id);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/v1/book")
	public ResponseEntity<Void> createNewBook(@RequestBody BookCreateRequestDTO bookCreateDTO){
		bookService.createNewBook(bookCreateDTO);
		return ResponseEntity.created(URI.create("/book")).build();
	}
	
	@GetMapping("/v1/book")
	public ResponseEntity<ResultPageResponseDTO<BookListResponseDTO>> findBookList(@RequestParam(name = "page", required = true, defaultValue = "0") Integer page, 
			@RequestParam(name = "limit", required = true, defaultValue = "0") Integer limit,
			@RequestParam(name = "sortBy", required = true, defaultValue = "title") String sortBy,
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
			@RequestParam(name = "bookTitle", required = false, defaultValue = "") String bookTitle,
			@RequestParam(name = "publisherName", required = false, defaultValue = "") String publisherName){
		return ResponseEntity.ok(bookService.findBookList(page, limit, sortBy, direction, publisherName, bookTitle));
	}
	
	@GetMapping("/v2/book")
	public ResponseEntity<List<BookDetailResponseDTO>> findBookList(){
		return ResponseEntity.ok(bookService.findBookList());
	}
	
	@PutMapping("/v1/book/{bookId}")
	public ResponseEntity<Void> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookUpdateRequestDTO bookUpdateRequestDTO){
		bookService.updateBook(bookId, bookUpdateRequestDTO);
		return ResponseEntity.ok().build();	
	}
	
	@DeleteMapping("/v1/book/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable("bookId") Long bookid){
		bookService.deleteBook(bookid);
		return ResponseEntity.ok().build();
	}
}	
