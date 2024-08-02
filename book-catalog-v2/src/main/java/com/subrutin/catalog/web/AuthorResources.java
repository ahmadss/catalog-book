package com.subrutin.catalog.web;

import java.net.URI;
import java.util.List;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.AuthorCreateRequestDTO;
import com.subrutin.catalog.dto.AuthorResponseDTO;
import com.subrutin.catalog.dto.AuthorUpdateRequestDTO;
import com.subrutin.catalog.service.AuthorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthorResources {
	
	private final AuthorService authorService;
	
	@GetMapping("/v1/author/{id}/detail")
	public ResponseEntity<AuthorResponseDTO> findAuthorById(@PathVariable String id){
		AuthorResponseDTO result = authorService.findAuthorById(id);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/v1/author")
	public ResponseEntity<Void> createNewBook(@RequestBody @Valid List<AuthorCreateRequestDTO> dto){
		authorService.createNewAuthor(dto);
		return ResponseEntity.created(URI.create("/author")).build();
	}
	
	@PutMapping("/v1/author/{authorId}")
	public ResponseEntity<Void> updateAuthor(@PathVariable String authorId, @RequestBody AuthorUpdateRequestDTO dto){
		authorService.updateAuthor(authorId, dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/v1/author/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable String id){
		authorService.deleteAuthor(id);
		return ResponseEntity.ok().build();
	}
}
