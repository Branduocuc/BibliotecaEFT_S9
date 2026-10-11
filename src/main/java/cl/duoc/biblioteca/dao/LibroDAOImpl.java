package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private final Connection connection;

    public LibroDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insertar(Libro libro) {

        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getId_categoria());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se pudo insertar el libro.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Libro libro) {

        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, id_categoria = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getId_categoria());
            stmt.setInt(7, libro.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el libro indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Libro libro) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, libro.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el libro indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Libro> listar() {

        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT * FROM libros";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        rs.getInt("id_categoria")
                );

                libros.add(libro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar libros: " + e.getMessage(), e);
        }

        return libros;
    }
}