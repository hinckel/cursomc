package com.hinckel.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hinckel.cursomc.domain.Categoria;
import com.hinckel.cursomc.repositories.CategoriaRepository;
import com.hinckel.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type:  " + Categoria.class.getSimpleName()));
	}

	public Categoria insert(Categoria entity) {
		entity.setId(null);
		return repo.save(entity);
	}

	public Categoria update(Categoria entity) {
		find(entity.getId());
		return repo.save(entity);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException dive) {
			throw new com.hinckel.cursomc.services.exception.DataIntegrityViolationException(
					"Não é possível excluir uma categoria que possui produtos!");
		}
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
}
