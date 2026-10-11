package cl.duoc.biblioteca.dao;

import java.util.List;

public interface ReporteDAO {

    List<String> librosMasPrestados();

    List<String> historialPorEstudiante(int idEstudiante);

    List<String> librosActualmentePrestados();
}
