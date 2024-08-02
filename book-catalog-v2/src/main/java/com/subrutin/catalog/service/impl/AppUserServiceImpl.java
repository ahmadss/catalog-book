package com.subrutin.catalog.service.impl;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subrutin.catalog.dto.UserDetailResponseDTO;
import com.subrutin.catalog.exception.BadRequestException;
import com.subrutin.catalog.repository.AppUserRepository;
import com.subrutin.catalog.service.AppUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService{

	private final AppUserRepository appUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username).orElseThrow(()->new BadRequestException("invalid username"));
	}

	@Override
	public UserDetailResponseDTO findUserDetail() {
		SecurityContext ctx =SecurityContextHolder.getContext();
		UserDetailResponseDTO dto = new UserDetailResponseDTO();
		String username = ctx.getAuthentication().getName();
		dto.setUsername(username);
		return dto;
	}

}
