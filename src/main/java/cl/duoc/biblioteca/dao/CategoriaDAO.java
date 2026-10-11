package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Categoria;

import java.util.List;

public interface CategoriaDAO {

    void insertar(Categoria categoria);

    void actualizar(Categoria categoria);

    void eliminar(Categoria categoria);

    List<Categoria> listar();
}