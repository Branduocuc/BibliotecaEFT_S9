package cl.duoc.biblioteca.dao;


import cl.duoc.biblioteca.model.Libro;


import java.util.List;

public interface LibroDAO {




    void insertar (Libro libro) ;
    void actualizar (Libro libro);
    void eliminar (Libro libro);

    List<Libro> listar();











}
