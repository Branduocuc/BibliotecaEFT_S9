package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.ReporteController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ReporteView extends JFrame {

    private JTextField txtIdEstudiante;
    private JTextArea areaReporte;
    private ReporteController controller;

    public ReporteView() {

        try {
            controller = new ReporteController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Reportes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        JPanel panelEstudiante = new JPanel(new GridLayout(1, 2, 10, 10));

        txtIdEstudiante = new JTextField();

        panelEstudiante.add(
                new JLabel("ID Estudiante para historial:")
        );

        panelEstudiante.add(txtIdEstudiante);

        JPanel panelBotones =
                new JPanel(new GridLayout(1, 3, 10, 10));

        JButton btnMasPrestados =
                new JButton("Más prestados");

        JButton btnHistorial =
                new JButton("Historial estudiante");

        JButton btnPrestados =
                new JButton("Actualmente prestados");

        panelBotones.add(btnMasPrestados);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnPrestados);

        panelSuperior.add(
                panelEstudiante,
                BorderLayout.NORTH
        );

        panelSuperior.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                new JScrollPane(areaReporte),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        btnMasPrestados.addActionListener(e -> {

            try {

                mostrar(
                        controller.librosMasPrestados()
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage()
                );
            }
        });

        btnPrestados.addActionListener(e -> {

            try {

                mostrar(
                        controller.librosActualmentePrestados()
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage()
                );
            }
        });

        btnHistorial.addActionListener(e -> {

            try {

                int idEstudiante =
                        Integer.parseInt(
                                txtIdEstudiante.getText()
                        );

                mostrar(
                        controller.historialPorEstudiante(
                                idEstudiante
                        )
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un ID Estudiante numérico."
                );

            } catch (RuntimeException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage()
                );
            }
        });

        setVisible(true);
    }

    private void mostrar(List<String> lineas) {

        try {

            areaReporte.setText("");

            if (lineas.isEmpty()) {

                areaReporte.setText(
                        "No hay resultados."
                );

                return;
            }

            for (String linea : lineas) {
                areaReporte.append(
                        linea + "\n"
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }
}