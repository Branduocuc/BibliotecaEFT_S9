package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Usuario;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UsuarioDAOImpl implements UsuarioDAO {

    private final Connection connection;

    public UsuarioDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insertar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nombre, rut, correo, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasenia());
            stmt.setString(5, usuario.getRol());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se pudo insertar el usuario.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al insertar usuario: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nombre = ?, rut = ?, correo = ?, contraseña = ?, rol = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasenia());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el usuario indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar usuario: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void eliminar(Usuario usuario) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No se encontró el usuario indicado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar usuario: " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        rs.getString("rol")
                );

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar usuarios: " + e.getMessage(), e
            );
        }

        return usuarios;
    }

    @Override
    public Usuario autenticar(String correo, String contrasenia) {

        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasenia);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("rut"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            rs.getString("rol")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al autenticar usuario: " + e.getMessage(), e
            );
        }

        return null;
    }
}