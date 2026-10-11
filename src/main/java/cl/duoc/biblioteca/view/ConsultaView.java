package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.LibroController;
import cl.duoc.biblioteca.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ConsultaView extends JFrame {

    private JTextArea areaLibros;
    private LibroController controller;

    public ConsultaView() {

        try {
            controller = new LibroController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Consulta de libros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 450);
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

        JButton btnActualizar =
                new JButton("Actualizar listado");

        areaLibros = new JTextArea();
        areaLibros.setEditable(false);

        panelPrincipal.add(
                btnActualizar,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                new JScrollPane(areaLibros),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        btnActualizar.addActionListener(
                e -> listar()
        );

        listar();

        setVisible(true);
    }

    private void listar() {

        try {

            List<Libro> libros =
                    controller.listar();

            areaLibros.setText("");

            for (Libro libro : libros) {

                areaLibros.append(
                        "ID: " + libro.getId()
                                + " | " + libro.getTitulo()
                                + " | " + libro.getAutor()
                                + " | ISBN: " + libro.getIsbn()
                                + " | Stock: " + libro.getStock()
                                + "\n"
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al consultar libros: "
                            + e.getMessage()
            );
        }
    }
}