package com.subrutin.catalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.domain.Book;
import com.subrutin.catalog.domain.Category;
import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.BookCreateRequestDTO;
import com.subrutin.catalog.dto.BookDetailResponseDTO;
import com.subrutin.catalog.dto.BookListResponseDTO;
import com.subrutin.catalog.dto.BookUpdateRequestDTO;
import com.subrutin.catalog.dto.ResultPageResponseDTO;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.AuthorRepository;
import com.subrutin.catalog.repository.BookRepository;
import com.subrutin.catalog.service.AuthorService;
import com.subrutin.catalog.service.BookService;
import com.subrutin.catalog.service.CategoryService;
import com.subrutin.catalog.service.PublisherService;
import com.subrutin.catalog.util.PaginationUtil;
import com.subrutin.catalog.web.BookResources;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service("bookService")
@Slf4j
public class BookServiceImpl implements BookService{
	
	private final BookRepository bookRepository;

	private final AuthorService authorService;
	
	private final CategoryService categoryService;
	
	private final PublisherService publisherService;

	@Override
	public BookDetailResponseDTO findBookDetailById(String Id) {
		log.info("start get data sql book");
		Book book = bookRepository.findBySecureId(Id)
				.orElseThrow(()->new BadRequestException("book_id.invalid"));
		log.info("finis get data sql book");
		
		BookDetailResponseDTO dto = new BookDetailResponseDTO();
		dto.setBookId(book.getSecureId());	
		
		log.info("start get data sql category");
		dto.setCategories(categoryService.constructDTO(book.getCategories()));
		log.info("finish get data sql category");
		
		log.info("start get data sql author");
		dto.setAuthor(authorService.constructDTO(book.getAuthors()));
		log.info("finish get data sql author");
		
		log.info("start get data sql publisher");
		dto.setPublisher(publisherService.contructDTO(book.getPublisher()));
		log.info("finish get data sql publisher");
		
		dto.setBookTitle(book.getTitle());
		dto.setBookDescription(book.getDescription());
		return dto;
	}


	@Override
	public List<BookDetailResponseDTO> findBookList() {
		List<Book> books =  bookRepository.findAll();
		return books.stream().map((b)->{
			BookDetailResponseDTO dto  = new BookDetailResponseDTO();
//			dto.setBookId(b.getId());
			dto.setBookTitle(b.getTitle());
			dto.setBookDescription(b.getDescription());
			return dto;
		}).collect(Collectors.toList());
	}


	@Override
	public void createNewBook(BookCreateRequestDTO dto) {
		List<Author> authors =  authorService.findAuthors(dto.getAuthorIdList());
		List<Category> categories = categoryService.findCategories(dto.getCategoryList());
		Publisher publisher = publisherService.findPublisher(dto.getPublisherId());
		Book book = new Book();
		book.setAuthors(authors);
		book.setCategories(categories);
		book.setPublisher(publisher);
		book.setTitle(dto.getBookTitle());
		book.setDescription(dto.getDescription());
		bookRepository.save(book);
	}


	@Override
	public void updateBook(long bookId, BookUpdateRequestDTO dto) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(()->new BadRequestException("book_id.invalid"));
		book.setTitle(dto.getBookTitle());
		book.setDescription(dto.getDescription());
		
		bookRepository.save(book);
	}


	@Override
	public void deleteBook(Long Id) {
		bookRepository.deleteById(Id);
		
	}


	@Override
	public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy,
			String direction, String publisherName, String bookTitle) {
		
		publisherName  = StringUtils.isEmpty(publisherName) ?"%":publisherName+"%";
		Sort sort = Sort.by(PaginationUtil.getSortBy(direction), sortBy);
		
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Book> pageResult = bookRepository.findBookList(bookTitle, publisherName, pageable);
		
		List<BookListResponseDTO> dtos= pageResult.stream().map(b->{
			BookListResponseDTO dto = new BookListResponseDTO();
			dto.setAuthorNames(b.getAuthors().stream().map(a->a.getName()).collect(Collectors.toList()));
			dto.setCategoryCodes(b.getCategories().stream().map(c->c.getCode()).collect(Collectors.toList()));
			dto.setTitle(b.getTitle());
			dto.setPublisherName(b.getPublisher().getName());
			dto.setDescription(b.getDescription());
			dto.setId(b.getSecureId());
			return dto;
		}).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

//	public BookRepository getBookRepository() {
//		return bookRepository;
//	}
//
//	public void setBookRepository(BookRepository bookRepository) {
//		this.bookRepository = bookRepository;
//	}
	
	
	
	
}
