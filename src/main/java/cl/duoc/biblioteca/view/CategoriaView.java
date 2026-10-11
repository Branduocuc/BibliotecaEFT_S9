package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.CategoriaController;
import cl.duoc.biblioteca.model.Categoria;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CategoriaView extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextArea areaListado;
    private CategoriaController controller;

    public CategoriaView() {

        try {
            controller = new CategoriaController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Gestión de categorías");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
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
                new JPanel(new GridLayout(2, 2, 10, 10));

        txtId = new JTextField();
        txtNombre = new JTextField();

        panelFormulario.add(
                new JLabel("ID (actualizar/eliminar):")
        );
        panelFormulario.add(txtId);

        panelFormulario.add(
                new JLabel("Nombre:")
        );
        panelFormulario.add(txtNombre);

        JPanel panelBotones =
                new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnGuardar =
                new JButton("Guardar");

        JButton btnActualizar =
                new JButton("Actualizar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnListar =
                new JButton("Listar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
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

        btnGuardar.addActionListener(
                e -> guardar()
        );

        btnActualizar.addActionListener(
                e -> actualizar()
        );

        btnEliminar.addActionListener(
                e -> eliminar()
        );

        btnListar.addActionListener(
                e -> listar()
        );

        setVisible(true);
    }

    private void guardar() {

        try {

            controller.guardar(
                    txtNombre.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Categoría guardada correctamente."
            );

            listar();

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar: "
                            + e.getMessage()
            );
        }
    }

    private void actualizar() {

        try {

            controller.actualizar(
                    Integer.parseInt(
                            txtId.getText()
                    ),
                    txtNombre.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Categoría actualizada correctamente."
            );

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un ID numérico."
            );

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar: "
                            + e.getMessage()
            );
        }
    }

    private void eliminar() {

        try {

            controller.eliminar(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Categoría eliminada correctamente."
            );

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un ID numérico."
            );

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar: "
                            + e.getMessage()
            );
        }
    }

    private void listar() {

        try {

            List<Categoria> categorias =
                    controller.listar();

            areaListado.setText("");

            for (Categoria categoria : categorias) {

                areaListado.append(
                        "ID: "
                                + categoria.getId()
                                + " | "
                                + categoria.getNombre()
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