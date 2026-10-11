package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.LoginController;
import cl.duoc.biblioteca.model.Usuario;

import javax.swing.*;
import java.awt.GridLayout;
import java.sql.SQLException;

public class LoginView extends JFrame {

    private JPanel panelPrincipal;

    private JLabel lblCorreo;
    private JLabel lblContrasenia;

    private JTextField txtCorreo;
    private JPasswordField txtContrasenia;

    private JButton btnIngresar;
    private JButton btnSalir;

    private LoginController controller;

    public LoginView() {

        try {
            controller = new LoginController();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos."
            );

            return;
        }

        setTitle("Biblioteca - Iniciar sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(3, 2, 10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField(15);

        lblContrasenia = new JLabel("Contraseña:");
        txtContrasenia = new JPasswordField(15);

        btnIngresar = new JButton("Ingresar");
        btnSalir = new JButton("Salir");

        panelPrincipal.add(lblCorreo);
        panelPrincipal.add(txtCorreo);

        panelPrincipal.add(lblContrasenia);
        panelPrincipal.add(txtContrasenia);

        panelPrincipal.add(btnIngresar);
        panelPrincipal.add(btnSalir);

        add(panelPrincipal);

        btnIngresar.addActionListener(e -> {

            String correo = txtCorreo.getText().trim();
            String contrasenia = new String(txtContrasenia.getPassword());

            if (correo.isEmpty() || contrasenia.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar correo y contraseña."
                );

                return;
            }

            Usuario usuario = controller.autenticar(
                    correo,
                    contrasenia
            );

            if (usuario == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Correo o contraseña incorrectos."
                );

                return;
            }

            String rol = usuario.getRol();

            if (rol.equals("bibliotecario")) {

                new BibliotecaView(rol);
                dispose();

            } else if (rol.equals("estudiante")) {

                new BibliotecaView(rol);
                dispose();
            }
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}