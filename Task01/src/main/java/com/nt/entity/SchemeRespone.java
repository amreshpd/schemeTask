package com.nt.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SchemeRespone {
	private Integer schemeCode;
	private String schemeName;	
	private String filter;	
	private LocalDate date;
	private List<List<String>> data;
}
