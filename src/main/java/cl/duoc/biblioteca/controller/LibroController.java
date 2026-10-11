package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.LibroDAO;
import cl.duoc.biblioteca.dao.LibroDAOImpl;
import cl.duoc.biblioteca.model.Libro;

import java.sql.SQLException;
import java.util.List;

public class LibroController {

    private final LibroDAO libroDAO;

    public LibroController() throws SQLException {
        this.libroDAO = new LibroDAOImpl();
    }

    public void guardar(String titulo, String autor, String isbn,
                        String editorial, int stock, int idCategoria) {

        Libro libro = new Libro(
                0,
                titulo,
                autor,
                isbn,
                editorial,
                stock,
                idCategoria
        );

        libroDAO.insertar(libro);
    }

    public void actualizar(int id, String titulo, String autor, String isbn,
                           String editorial, int stock, int idCategoria) {

        Libro libro = new Libro(
                id,
                titulo,
                autor,
                isbn,
                editorial,
                stock,
                idCategoria
        );

        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        Libro libro = new Libro(id, "", "", "", "", 0, 0);
        libroDAO.eliminar(libro);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }
}
