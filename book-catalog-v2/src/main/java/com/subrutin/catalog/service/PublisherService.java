package com.subrutin.catalog.service;

import com.subrutin.catalog.domain.Publisher;
import com.subrutin.catalog.dto.PublisherCreateRequestDTO;
import com.subrutin.catalog.dto.PublisherListResponseDTO;
import com.subrutin.catalog.dto.PublisherResponseDTO;
import com.subrutin.catalog.dto.PublisherUpdateRequestDTO;
import com.subrutin.catalog.dto.ResultPageResponseDTO;

public interface PublisherService {
	public void createPublisher(PublisherCreateRequestDTO dto);
	public void updatePublisher(String id, PublisherUpdateRequestDTO dto);
	public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit, String sortBy, String direction, String publisherName);
	public Publisher findPublisher(String publisherId);
	public PublisherResponseDTO contructDTO(Publisher publisher);
}
