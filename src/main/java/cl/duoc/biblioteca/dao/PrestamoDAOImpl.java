package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {

    private final Connection connection;

    public PrestamoDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insertar(Prestamo prestamo) {

        String sql = "INSERT INTO prestamos " +
                "(id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdEstudiante());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));

            if (prestamo.getFechaDevolucion() != null) {
                stmt.setDate(
                        4,
                        Date.valueOf(prestamo.getFechaDevolucion())
                );
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setBoolean(5, prestamo.isDevuelto());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se pudo insertar el préstamo."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al insertar préstamo: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizar(Prestamo prestamo) {

        String sql = "UPDATE prestamos SET " +
                "id_estudiante = ?, " +
                "id_libro = ?, " +
                "fecha_prestamo = ?, " +
                "fecha_devolucion = ?, " +
                "devuelto = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdEstudiante());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));

            if (prestamo.getFechaDevolucion() != null) {
                stmt.setDate(
                        4,
                        Date.valueOf(prestamo.getFechaDevolucion())
                );
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setBoolean(5, prestamo.isDevuelto());
            stmt.setInt(6, prestamo.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró el préstamo indicado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar préstamo: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void eliminar(Prestamo prestamo) {

        String sql = "DELETE FROM prestamos WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getId());

            int filas = stmt.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException(
                        "No se encontró el préstamo indicado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar préstamo: " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<Prestamo> listar() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM prestamos ORDER BY id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Date fechaDevolucionSQL =
                        rs.getDate("fecha_devolucion");

                Prestamo prestamo = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_libro"),
                        rs.getDate("fecha_prestamo")
                                .toLocalDate(),
                        fechaDevolucionSQL != null
                                ? fechaDevolucionSQL.toLocalDate()
                                : null,
                        rs.getBoolean("devuelto")
                );

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar préstamos: " + e.getMessage(), e
            );
        }

        return prestamos;
    }

    @Override
    public synchronized void registrarPrestamo(
            Prestamo prestamo) {

        String buscarLibro =
                "SELECT stock FROM libros WHERE id = ? FOR UPDATE";

        String insertarPrestamo =
                "INSERT INTO prestamos " +
                        "(id_estudiante, id_libro, fecha_prestamo, " +
                        "fecha_devolucion, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?)";

        String actualizarStock =
                "UPDATE libros SET stock = stock - 1 WHERE id = ?";

        try {

            connection.setAutoCommit(false);

            int stock;

            try (PreparedStatement stmt =
                         connection.prepareStatement(buscarLibro)) {

                stmt.setInt(1, prestamo.getIdLibro());

                try (ResultSet rs = stmt.executeQuery()) {

                    if (!rs.next()) {
                        throw new RuntimeException(
                                "El libro indicado no existe."
                        );
                    }

                    stock = rs.getInt("stock");
                }
            }

            if (stock <= 0) {
                throw new RuntimeException(
                        "El libro no tiene stock disponible."
                );
            }

            try (PreparedStatement stmt =
                         connection.prepareStatement(insertarPrestamo)) {

                stmt.setInt(
                        1,
                        prestamo.getIdEstudiante()
                );

                stmt.setInt(
                        2,
                        prestamo.getIdLibro()
                );

                stmt.setDate(
                        3,
                        Date.valueOf(
                                prestamo.getFechaPrestamo()
                        )
                );

                stmt.setDate(
                        4,
                        Date.valueOf(
                                prestamo.getFechaDevolucion()
                        )
                );

                stmt.setBoolean(5, false);

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException(
                            "No se pudo registrar el préstamo."
                    );
                }
            }

            try (PreparedStatement stmt =
                         connection.prepareStatement(actualizarStock)) {

                stmt.setInt(
                        1,
                        prestamo.getIdLibro()
                );

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException(
                            "No se pudo actualizar el stock."
                    );
                }
            }

            connection.commit();

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }

            throw new RuntimeException(
                    "Error al registrar préstamo: " +
                            e.getMessage(),
                    e
            );

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public synchronized void devolver(int idPrestamo) {

        String buscarPrestamo =
                "SELECT id_libro, devuelto " +
                        "FROM prestamos WHERE id = ? FOR UPDATE";

        String marcarDevuelto =
                "UPDATE prestamos " +
                        "SET devuelto = TRUE WHERE id = ?";

        String aumentarStock =
                "UPDATE libros " +
                        "SET stock = stock + 1 WHERE id = ?";

        try {

            connection.setAutoCommit(false);

            int idLibro;
            boolean devuelto;

            try (PreparedStatement stmt =
                         connection.prepareStatement(buscarPrestamo)) {

                stmt.setInt(1, idPrestamo);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (!rs.next()) {
                        throw new RuntimeException(
                                "El préstamo indicado no existe."
                        );
                    }

                    idLibro = rs.getInt("id_libro");
                    devuelto = rs.getBoolean("devuelto");
                }
            }

            if (devuelto) {
                throw new RuntimeException(
                        "El préstamo ya estaba devuelto."
                );
            }

            try (PreparedStatement stmt =
                         connection.prepareStatement(marcarDevuelto)) {

                stmt.setInt(1, idPrestamo);

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException(
                            "No se pudo registrar la devolución."
                    );
                }
            }

            try (PreparedStatement stmt =
                         connection.prepareStatement(aumentarStock)) {

                stmt.setInt(1, idLibro);

                int filas = stmt.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException(
                            "No se pudo actualizar el stock."
                    );
                }
            }

            connection.commit();

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }

            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }

            throw new RuntimeException(
                    "Error al registrar devolución: " +
                            e.getMessage(),
                    e
            );

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}