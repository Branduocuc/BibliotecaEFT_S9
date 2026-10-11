package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.PrestamoController;
import cl.duoc.biblioteca.model.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PrestamoView extends JFrame {

    private JTextField txtIdEstudiante;
    private JTextField txtIdLibro;
    private JTextField txtIdPrestamo;
    private JTextArea areaListado;

    private JButton btnPrestar;
    private JButton btnDevolver;
    private JButton btnListar;

    private PrestamoController controller;

    public PrestamoView() {

        try {
            controller = new PrestamoController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Préstamos y devoluciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JPanel panelFormulario =
                new JPanel(new GridLayout(3, 2, 10, 10));

        txtIdEstudiante = new JTextField();
        txtIdLibro = new JTextField();
        txtIdPrestamo = new JTextField();

        panelFormulario.add(
                new JLabel("ID Estudiante:")
        );

        panelFormulario.add(txtIdEstudiante);

        panelFormulario.add(
                new JLabel("ID Libro:")
        );

        panelFormulario.add(txtIdLibro);

        panelFormulario.add(
                new JLabel("ID Préstamo (devolver):")
        );

        panelFormulario.add(txtIdPrestamo);

        JPanel panelBotones =
                new JPanel(new GridLayout(1, 3, 10, 10));

        btnPrestar =
                new JButton("Registrar préstamo");

        btnDevolver =
                new JButton("Registrar devolución");

        btnListar =
                new JButton("Listar préstamos");

        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnListar);

        areaListado = new JTextArea();
        areaListado.setEditable(false);

        JPanel panelSuperior =
                new JPanel(new BorderLayout(10, 10));

        panelSuperior.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        panelSuperior.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                new JScrollPane(areaListado),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        btnPrestar.addActionListener(
                e -> registrarPrestamo()
        );

        btnDevolver.addActionListener(
                e -> devolver()
        );

        btnListar.addActionListener(
                e -> listar()
        );

        setVisible(true);
    }

    private void registrarPrestamo() {

        int idEstudiante;
        int idLibro;

        try {

            idEstudiante =
                    Integer.parseInt(
                            txtIdEstudiante.getText()
                    );

            idLibro =
                    Integer.parseInt(
                            txtIdLibro.getText()
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID Estudiante e ID Libro deben ser números."
            );

            return;
        }

        btnPrestar.setEnabled(false);

        new Thread(() -> {

            try {

                String mensaje =
                        controller.registrarPrestamo(
                                idEstudiante,
                                idLibro
                        );

                SwingUtilities.invokeLater(() -> {

                    JOptionPane.showMessageDialog(
                            this,
                            mensaje
                    );

                    listar();
                });

            } catch (RuntimeException e) {

                SwingUtilities.invokeLater(() ->

                        JOptionPane.showMessageDialog(
                                this,
                                "Error al registrar préstamo: "
                                        + e.getMessage()
                        )
                );

            } finally {

                SwingUtilities.invokeLater(
                        () -> btnPrestar.setEnabled(true)
                );
            }

        }).start();
    }

    private void devolver() {

        try {

            int idPrestamo =
                    Integer.parseInt(
                            txtIdPrestamo.getText()
                    );

            String mensaje =
                    controller.devolver(idPrestamo);

            JOptionPane.showMessageDialog(
                    this,
                    mensaje
            );

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID Préstamo debe ser numérico."
            );

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al devolver: "
                            + e.getMessage()
            );
        }
    }

    private void listar() {

        try {

            List<Prestamo> prestamos =
                    controller.listar();

            areaListado.setText("");

            for (Prestamo prestamo : prestamos) {

                areaListado.append(

                        "ID: " + prestamo.getId()
                                + " | Estudiante: "
                                + prestamo.getIdEstudiante()
                                + " | Libro: "
                                + prestamo.getIdLibro()
                                + " | Préstamo: "
                                + prestamo.getFechaPrestamo()
                                + " | Vence: "
                                + prestamo.getFechaDevolucion()
                                + " | Devuelto: "
                                + (prestamo.isDevuelto()
                                ? "Sí"
                                : "No")
                                + "\n"
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al listar: "
                            + e.getMessage()
            );
        }
    }
}