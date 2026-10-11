package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.PrestamoDAO;
import cl.duoc.biblioteca.dao.PrestamoDAOImpl;
import cl.duoc.biblioteca.model.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrestamoController {

    private final PrestamoDAO prestamoDAO;

    public PrestamoController() throws SQLException {
        this.prestamoDAO = new PrestamoDAOImpl();
    }

    public String registrarPrestamo(int idEstudiante, int idLibro) {

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaVencimiento = fechaPrestamo.plusDays(7);

        Prestamo prestamo = new Prestamo(
                0,
                idEstudiante,
                idLibro,
                fechaPrestamo,
                fechaVencimiento,
                false
        );

        prestamoDAO.registrarPrestamo(prestamo);

        return "Préstamo registrado. Vence el " + fechaVencimiento;
    }

    public String devolver(int idPrestamo) {

        Prestamo prestamo = buscarPorId(idPrestamo);

        if (prestamo == null) {
            throw new RuntimeException("No existe el préstamo indicado.");
        }

        if (prestamo.isDevuelto()) {
            throw new RuntimeException("El préstamo ya estaba devuelto.");
        }

        prestamoDAO.devolver(idPrestamo);

        if (LocalDate.now().isAfter(prestamo.getFechaDevolucion())) {
            return "Devolución registrada con atraso.";
        }

        return "Devolución registrada a tiempo.";
    }

    public List<Prestamo> listar() {
        return prestamoDAO.listar();
    }

    private Prestamo buscarPorId(int idPrestamo) {
        for (Prestamo prestamo : prestamoDAO.listar()) {
            if (prestamo.getId() == idPrestamo) {
                return prestamo;
            }
        }
        return null;
    }
}
