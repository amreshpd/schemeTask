package com.nt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEME")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scheme {
	@Id
	private Integer schemeCode;
	@Column(length =200)
	private String schemeName;
	@Column(length =200)
	private String filter;	
}
