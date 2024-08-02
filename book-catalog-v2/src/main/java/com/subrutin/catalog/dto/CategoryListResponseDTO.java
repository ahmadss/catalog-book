package com.subrutin.catalog.dto;

import java.io.Serializable;

import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class CategoryListResponseDTO implements Serializable{

	private static final long serialVersionUID = -9145132319388794342L;
	
	private String code;
	private String name;
	private String description;

}
