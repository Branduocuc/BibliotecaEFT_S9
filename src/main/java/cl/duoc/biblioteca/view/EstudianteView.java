package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.EstudianteController;
import cl.duoc.biblioteca.model.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EstudianteView extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;
    private JTextArea areaListado;

    private EstudianteController controller;

    public EstudianteView() {

        try {
            controller = new EstudianteController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Gestión de estudiantes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JPanel panelFormulario =
                new JPanel(new GridLayout(5, 2, 10, 10));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        panelFormulario.add(
                new JLabel("ID (actualizar/eliminar):")
        );
        panelFormulario.add(txtId);

        panelFormulario.add(
                new JLabel("Nombre:")
        );
        panelFormulario.add(txtNombre);

        panelFormulario.add(
                new JLabel("RUT:")
        );
        panelFormulario.add(txtRut);

        panelFormulario.add(
                new JLabel("Curso:")
        );
        panelFormulario.add(txtCurso);

        panelFormulario.add(
                new JLabel("Correo:")
        );
        panelFormulario.add(txtCorreo);

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
                    txtNombre.getText(),
                    txtRut.getText(),
                    txtCurso.getText(),
                    txtCorreo.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante guardado correctamente."
            );

            listar();

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
                    txtNombre.getText(),
                    txtRut.getText(),
                    txtCurso.getText(),
                    txtCorreo.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente."
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
                    "Error al actualizar: " + e.getMessage()
            );
        }
    }

    private void eliminar() {

        try {

            controller.eliminar(
                    Integer.parseInt(txtId.getText())
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante eliminado correctamente."
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

            List<Estudiante> estudiantes =
                    controller.listar();

            areaListado.setText("");

            for (Estudiante estudiante : estudiantes) {

                areaListado.append(
                        "ID: " + estudiante.getId()
                                + " | " + estudiante.getNombre()
                                + " | RUT: " + estudiante.getRut()
                                + " | Curso: " + estudiante.getCurso()
                                + " | " + estudiante.getCorreo()
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