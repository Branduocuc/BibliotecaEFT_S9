package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Estudiante;

import java.util.List;

public interface EstudianteDAO {

    void insertar(Estudiante estudiante);

    void actualizar(Estudiante estudiante);

    void eliminar(Estudiante estudiante);

    List<Estudiante> listar();
}