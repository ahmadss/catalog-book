package com.subrutin.catalog.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book_detail", indexes = {
		@Index(name="book_detail_secure_id", columnList = "secure_id")
})
public class BookDetail extends AbstractBaseEntity{
	
	private static final long serialVersionUID = -4572088950134314195L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "settings")
	private String settings;
	
	@Column(name = "thumbnail")
	private String thumbnail;
	
	@OneToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;
}
