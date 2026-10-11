package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Prestamo;

import java.util.List;

public interface PrestamoDAO {

    void insertar(Prestamo prestamo);

    void actualizar(Prestamo prestamo);

    void eliminar(Prestamo prestamo);

    List<Prestamo> listar();

    void registrarPrestamo(Prestamo prestamo);

    void devolver(int idPrestamo);
}
