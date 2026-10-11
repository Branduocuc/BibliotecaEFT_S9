package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.ReporteDAO;
import cl.duoc.biblioteca.dao.ReporteDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class ReporteController {

    private final ReporteDAO reporteDAO;

    public ReporteController() throws SQLException {
        this.reporteDAO = new ReporteDAOImpl();
    }

    public List<String> librosMasPrestados() {
        return reporteDAO.librosMasPrestados();
    }

    public List<String> historialPorEstudiante(int idEstudiante) {
        return reporteDAO.historialPorEstudiante(idEstudiante);
    }

    public List<String> librosActualmentePrestados() {
        return reporteDAO.librosActualmentePrestados();
    }
}
