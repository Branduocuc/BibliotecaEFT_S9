package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.LibroController;
import cl.duoc.biblioteca.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LibroView extends JFrame {

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;
    private JTextField txtIdCategoria;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnListar;

    private JTextArea areaListado;
    private LibroController controller;

    public LibroView() {

        try {
            controller = new LibroController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Gestión de libros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));

        txtId = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();
        txtIdCategoria = new JTextField();

        panelFormulario.add(new JLabel("ID (actualizar/eliminar):"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("ISBN:"));
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Editorial:"));
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("ID Categoría:"));
        panelFormulario.add(txtIdCategoria);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        areaListado = new JTextArea();
        areaListado.setEditable(false);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

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

        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> listar());

        setVisible(true);
    }

    private void guardar() {

        try {

            controller.guardar(
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    txtIsbn.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtStock.getText()),
                    Integer.parseInt(txtIdCategoria.getText())
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Libro guardado correctamente."
            );

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Stock e ID Categoría deben ser números."
            );

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar: " + e.getMessage()
            );
        }
    }

    private void actualizar() {

        try {

            controller.actualizar(
                    Integer.parseInt(txtId.getText()),
                    txtTitulo.getText(),
                    txtAutor.getText(),
                    txtIsbn.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtStock.getText()),
                    Integer.parseInt(txtIdCategoria.getText())
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Libro actualizado correctamente."
            );

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID, Stock e ID Categoría deben ser números."
            );

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar: " + e.getMessage()
            );
        }
    }

    private void eliminar() {

        try {

            int id = Integer.parseInt(
                    txtId.getText()
            );

            controller.eliminar(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Libro eliminado correctamente."
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
                    "Error al eliminar: " + e.getMessage()
            );
        }
    }

    private void listar() {

        try {

            List<Libro> libros = controller.listar();

            areaListado.setText("");

            for (Libro libro : libros) {

                areaListado.append(
                        "ID: " + libro.getId()
                                + " | " + libro.getTitulo()
                                + " | " + libro.getAutor()
                                + " | ISBN: " + libro.getIsbn()
                                + " | Editorial: " + libro.getEditorial()
                                + " | Stock: " + libro.getStock()
                                + " | Categoría: " + libro.getId_categoria()
                                + "\n"
                );
            }

        } catch (RuntimeException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al listar: " + e.getMessage()
            );
        }
    }
}