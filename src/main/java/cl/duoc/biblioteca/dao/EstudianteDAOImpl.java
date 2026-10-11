package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    private final Connection connection;

    public EstudianteDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insertar(Estudiante estudiante) {

        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se pudo insertar el estudiante.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al insertar estudiante: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizar(Estudiante estudiante) {

        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());
            stmt.setInt(5, estudiante.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el estudiante indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar estudiante: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void eliminar(Estudiante estudiante) {

        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, estudiante.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el estudiante indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar estudiante: " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<Estudiante> listar() {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT * FROM estudiantes";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Estudiante estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("curso"),
                        rs.getString("correo")
                );

                estudiantes.add(estudiante);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar estudiantes: " + e.getMessage(), e
            );
        }

        return estudiantes;
    }
}