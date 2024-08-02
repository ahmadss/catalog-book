package com.subrutin.catalog.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.subrutin.catalog.dto.ResultPageResponseDTO;

public class PaginationUtil {
	
	public static <T> ResultPageResponseDTO<T> createResultPageDTO(List<T> result, long totalElement, int pages){
		ResultPageResponseDTO<T> resultsDto = new ResultPageResponseDTO<T>();
		resultsDto.setPages(pages);
		resultsDto.setElements(totalElement);
		resultsDto.setResult(result);
		
		return resultsDto;
	}
	
	public static Sort.Direction getSortBy(String sortBy){
		if(sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}
}
