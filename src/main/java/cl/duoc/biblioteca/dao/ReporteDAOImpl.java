package cl.duoc.biblioteca.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAOImpl implements ReporteDAO {

    private final Connection connection;

    public ReporteDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<String> librosMasPrestados() {

        List<String> resultado = new ArrayList<>();

        String sql = "SELECT l.titulo, COUNT(p.id) AS total " +
                "FROM prestamos p " +
                "JOIN libros l ON p.id_libro = l.id " +
                "GROUP BY l.id, l.titulo " +
                "ORDER BY total DESC, l.titulo";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.add(
                        rs.getString("titulo") +
                                " | préstamos: " +
                                rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al generar reporte: " + e.getMessage(), e
            );
        }

        return resultado;
    }

    @Override
    public List<String> historialPorEstudiante(int idEstudiante) {

        List<String> resultado = new ArrayList<>();

        String sql = "SELECT p.id, e.nombre, l.titulo, p.fecha_prestamo, " +
                "p.fecha_devolucion, p.devuelto " +
                "FROM prestamos p " +
                "JOIN estudiantes e ON p.id_estudiante = e.id " +
                "JOIN libros l ON p.id_libro = l.id " +
                "WHERE p.id_estudiante = ? " +
                "ORDER BY p.fecha_prestamo DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String estado = rs.getBoolean("devuelto")
                            ? "Devuelto"
                            : "Pendiente";

                    resultado.add(
                            "#" + rs.getInt("id") +
                                    " | " + rs.getString("nombre") +
                                    " | " + rs.getString("titulo") +
                                    " | préstamo: " + rs.getDate("fecha_prestamo") +
                                    " | vencimiento: " + rs.getDate("fecha_devolucion") +
                                    " | " + estado
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al generar historial: " + e.getMessage(), e
            );
        }

        return resultado;
    }

    @Override
    public List<String> librosActualmentePrestados() {

        List<String> resultado = new ArrayList<>();

        String sql = "SELECT p.id, e.nombre, l.titulo, p.fecha_prestamo, p.fecha_devolucion " +
                "FROM prestamos p " +
                "JOIN estudiantes e ON p.id_estudiante = e.id " +
                "JOIN libros l ON p.id_libro = l.id " +
                "WHERE p.devuelto = FALSE " +
                "ORDER BY p.fecha_devolucion";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.add(
                        "#" + rs.getInt("id") +
                                " | " + rs.getString("titulo") +
                                " | estudiante: " + rs.getString("nombre") +
                                " | préstamo: " + rs.getDate("fecha_prestamo") +
                                " | vence: " + rs.getDate("fecha_devolucion")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al generar reporte: " + e.getMessage(), e
            );
        }

        return resultado;
    }
}
