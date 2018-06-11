package com.hinckel.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hinckel.cursomc.domain.Categoria;
import com.hinckel.cursomc.domain.Cliente;
import com.hinckel.cursomc.repositories.ClienteRepository;
import com.hinckel.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type:  " + Cliente.class.getSimpleName()));
	}

}
