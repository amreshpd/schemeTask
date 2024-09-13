package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.Scheme;

public interface SchemeRepository extends JpaRepository<Scheme, Integer> {
	public List<Scheme> findBySchemeNameContainingIgnoreCase(String schemeName);
}
