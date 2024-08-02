package com.subrutin.catalog.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.domain.Address;
import com.subrutin.catalog.domain.Author;
import com.subrutin.catalog.dto.AuthorCreateRequestDTO;
import com.subrutin.catalog.dto.AuthorResponseDTO;
import com.subrutin.catalog.dto.AuthorUpdateRequestDTO;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.AuthorRepository;
import com.subrutin.catalog.service.AuthorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{
	
	private final AuthorRepository authorRepository;

	@Override
	public AuthorResponseDTO findAuthorById(String id) {
		Author author =  authorRepository.findBySecureId(id)
		.orElseThrow(()->new BadRequestException("invalid author id"));
		
		AuthorResponseDTO dto = new AuthorResponseDTO();
		dto.setAuthorName(author.getName());
		dto.setBirthDate(author.getBirthDate());
		return dto;
	}

	@Override
	public void createNewAuthor(List<AuthorCreateRequestDTO> dtos) {
		List<Author> authors=  dtos.stream().map((dto)->{
			Author author = new Author();
			author.setName(dto.getAuthorName());
			author.setBirthDate(dto.getBirthDate());
			author.setDeleted(false);
			List<Address> addresses =  dto.getAddresess().stream().map(a->{
				Address address = new Address();
				address.setCityName(a.getCityName());
				address.setZipCode(a.getZipCode());
				address.setStreetName(a.getStreetName());
				address.setAuthor(author);
				return address;
			}).collect(Collectors.toList());
			author.setAddresses(addresses);
			return author;
		}).collect(Collectors.toList());
		
		
		authorRepository.saveAll(authors);
		
	}

	@Override
	public void updateAuthor(String id, AuthorUpdateRequestDTO dto) {
		Author author =  authorRepository.findBySecureId(id)
				.orElseThrow(()->new BadRequestException("invalid author id"));
		
		Map<Long, Address> 	addressMap = author.getAddresses().stream().map(a->a).collect(Collectors.toMap(Address::getId, Function.identity()));
		
		author.setName(dto.getAuthorName()==null?author.getName():dto.getAuthorName());
		author.setBirthDate(dto.getBirthDate()==null?author.getBirthDate():dto.getBirthDate());
		List<Address> addresses =  dto.getAddresess().stream().map(a->{
			Address address = addressMap.get(a.getAddressId());
			address.setCityName(a.getCityName());
			address.setStreetName(a.getStreetName());
			address.setZipCode(a.getZipCode());
			return address;
		}).collect(Collectors.toList());
		author.setAddresses(addresses);
		authorRepository.save(author);
		
	}

	@Override
	public void deleteAuthor(String id) {
//		authorRepository.deleteById(id);
		Author author =  authorRepository.findBySecureId(id)
		.orElseThrow(()->new BadRequestException("invalid author id"));
		author.setDeleted(Boolean.TRUE);
		
		authorRepository.save(author);
		
	}

	@Override
	public List<Author> findAuthors(List<String> authorIdList) {
		List<Author> authors =  authorRepository.findBySecureIdIn(authorIdList);
		if(authors.isEmpty()) throw new BadRequestException("author can empty");
		return authors;
	}

	@Override
	public List<AuthorResponseDTO> constructDTO(List<Author> authors) {
		
		return authors.stream().map((a)->{
			AuthorResponseDTO dto = new AuthorResponseDTO();
			dto.setAuthorName(a.getName());
			dto.setBirthDate(a.getBirthDate());
			return dto;
		}).collect(Collectors.toList());
	}

}
