package com.nelioalves.cursomc.domain.service;

import com.nelioalves.cursomc.domain.entity.Categoria;
import com.nelioalves.cursomc.api.model.CategoriaModel;
import com.nelioalves.cursomc.domain.repository.CategoriaRepository;
import com.nelioalves.cursomc.domain.exception.DataIntegrityException;
import com.nelioalves.cursomc.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<CategoriaModel> findAll() {
        List<Categoria> categorias = repository.findAll();
        if (categorias.isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Categoria.class.getName());
        return categorias.stream().map(categoria -> new CategoriaModel(categoria)).collect(Collectors.toList());
    }

    public Page<CategoriaModel> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Categoria> categorias = repository.findAll(pageRequest);
        if (categorias.getContent().isEmpty()) throw new ObjectNotFoundException("Nenhum objeto foi encontrado! Tipo: " + Categoria.class.getName());
        return categorias.map(categoria -> new CategoriaModel(categoria));
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return repository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria newCategoria = find(categoria.getId());
        updateData(newCategoria, categoria);
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos! ");
        }
    }

    public Categoria fromDTO(CategoriaModel categoriaModel) {
        return new Categoria(categoriaModel);
    }

    private void updateData(Categoria newCategoria, Categoria categoria) {
        newCategoria.setNome(categoria.getNome());
    }
}