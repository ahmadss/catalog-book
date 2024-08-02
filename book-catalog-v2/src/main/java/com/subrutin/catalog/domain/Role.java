package com.subrutin.catalog.domain;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = -3535157084126830747L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return "ROLE_"+name;
	}
}
