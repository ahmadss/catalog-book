package com.subrutin.catalog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subrutin.catalog.dto.BookCreateRequestDTO;
import com.subrutin.catalog.dto.BookDetailResponseDTO;
import com.subrutin.catalog.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/book")
public class BookController {
	
	private final BookService bookService;
	
	@GetMapping("/list")
	public String findBookList(Model model) {
		List<BookDetailResponseDTO> books = bookService.findBookList();
		model.addAttribute("books", books);
		return "book/book-list";
	}
	
	@GetMapping("/new")
	public String loadBookForm(Model model) {
		BookCreateRequestDTO bookCreateDTO  = new BookCreateRequestDTO();
		model.addAttribute("bookCreateDTO", bookCreateDTO);
		return "book/book-new";
	}
	
	@PostMapping("/new")
	public String addNewBook(@ModelAttribute("bookCreateDTO") @Valid BookCreateRequestDTO dto, 
			BindingResult bindingResult,
			Errors errors,
			Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("bookCreateDTO", dto);
			return "book/book-new";
		}
		bookService.createNewBook(dto);
		return "redirect:/book/list";	
	}
}
