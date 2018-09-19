package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria find(Integer id) {
        Optional<Categoria> categoria = repository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não enconcontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Page<Categoria> findAll(Integer page){
        Pageable pageable = PageRequest.of(page,100, Sort.Direction.ASC, "nome");
        Page<Categoria> categorias = repository.findAll(pageable);
        if (categorias.getContent().size() == 0) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Categoria.class.getName());
        return categorias;
    }

    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias =  repository.findAll();
        if (categorias.size() == 0) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Categoria.class.getName());
        return categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        find(categoria.getId());
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma catagoria que possui produtos! " + e.getClass().getName());
        }
    }
}
