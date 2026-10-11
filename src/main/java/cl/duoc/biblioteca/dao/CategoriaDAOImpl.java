package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    private final Connection connection;

    public CategoriaDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insertar(Categoria categoria) {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNombre());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se pudo insertar la categoría.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al insertar categoría: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizar(Categoria categoria) {

        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró la categoría indicada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar categoría: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void eliminar(Categoria categoria) {

        String sql = "DELETE FROM categorias WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, categoria.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró la categoría indicada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar categoría: " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<Categoria> listar() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                categorias.add(categoria);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar categorías: " + e.getMessage(), e
            );
        }

        return categorias;
    }
}