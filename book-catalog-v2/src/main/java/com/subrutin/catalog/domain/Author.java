package com.subrutin.catalog.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("deprecation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author", indexes = {
		@Index(name="author_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE author SET deleted= true WHERE id = ?")
@org.hibernate.annotations.Where(clause = "deleted=false")
//@DynamicUpdate
public class Author extends AbstractBaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7269059274874955056L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
	@SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq")
	private Long id;
	
	
	@Column(name = "name", nullable = false, columnDefinition = "varchar(300)")
	private String name;
	
	@Column(name = "birth_date", nullable = false)
	private String birthDate;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Address> addresses;
	
}
