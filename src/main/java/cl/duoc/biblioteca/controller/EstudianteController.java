package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.EstudianteDAO;
import cl.duoc.biblioteca.dao.EstudianteDAOImpl;
import cl.duoc.biblioteca.model.Estudiante;

import java.sql.SQLException;
import java.util.List;

public class EstudianteController {

    private final EstudianteDAO estudianteDAO;

    public EstudianteController() throws SQLException {
        this.estudianteDAO = new EstudianteDAOImpl();
    }

    public void guardar(String nombre, String rut, String curso, String correo) {
        Estudiante estudiante = new Estudiante(0, nombre, rut, curso, correo);
        estudianteDAO.insertar(estudiante);
    }

    public void actualizar(int id, String nombre, String rut, String curso, String correo) {
        Estudiante estudiante = new Estudiante(id, nombre, rut, curso, correo);
        estudianteDAO.actualizar(estudiante);
    }

    public void eliminar(int id) {
        Estudiante estudiante = new Estudiante(id, "", "", "", "");
        estudianteDAO.eliminar(estudiante);
    }

    public List<Estudiante> listar() {
        return estudianteDAO.listar();
    }
}
