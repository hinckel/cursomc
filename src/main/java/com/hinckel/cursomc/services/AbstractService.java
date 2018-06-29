package com.hinckel.cursomc.services;

import org.springframework.data.jpa.repository.JpaRepository;

// TODO implement an AbstractService
public class AbstractService<T> {
	
	private JpaRepository<T, Integer> repository;
	
}
