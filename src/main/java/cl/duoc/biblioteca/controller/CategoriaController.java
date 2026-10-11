package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.CategoriaDAO;
import cl.duoc.biblioteca.dao.CategoriaDAOImpl;
import cl.duoc.biblioteca.model.Categoria;

import java.sql.SQLException;
import java.util.List;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO;

    public CategoriaController() throws SQLException {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    public void guardar(String nombre) {
        categoriaDAO.insertar(new Categoria(0, nombre));
    }

    public void actualizar(int id, String nombre) {
        categoriaDAO.actualizar(new Categoria(id, nombre));
    }

    public void eliminar(int id) {
        categoriaDAO.eliminar(new Categoria(id, ""));
    }

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }
}
