package cl.duoc.biblioteca.view;

import javax.swing.*;
import java.awt.GridLayout;

public class BibliotecaView extends JFrame {

    private final String rol;

    public BibliotecaView(String rol) {

        this.rol = rol;

        setTitle("Biblioteca - Menú principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(0, 1, 10, 10));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JButton btnLibros = new JButton("Gestión de libros");
        JButton btnCategorias = new JButton("Gestión de categorías");
        JButton btnEstudiantes = new JButton("Gestión de estudiantes");
        JButton btnPrestamos = new JButton("Préstamos / devoluciones");
        JButton btnReportes = new JButton("Reportes");
        JButton btnConsulta = new JButton("Consultar libros");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        if (rol.equals("bibliotecario")) {
            panelPrincipal.add(btnLibros);
            panelPrincipal.add(btnCategorias);
            panelPrincipal.add(btnEstudiantes);
            panelPrincipal.add(btnPrestamos);
            panelPrincipal.add(btnReportes);
        } else {
            panelPrincipal.add(btnConsulta);
            panelPrincipal.add(btnPrestamos);
        }

        panelPrincipal.add(btnCerrarSesion);
        add(panelPrincipal);

        btnLibros.addActionListener(e -> new LibroView());
        btnCategorias.addActionListener(e -> new CategoriaView());
        btnEstudiantes.addActionListener(e -> new EstudianteView());
        btnPrestamos.addActionListener(e -> new PrestamoView());
        btnReportes.addActionListener(e -> new ReporteView());
        btnConsulta.addActionListener(e -> new ConsultaView());

        btnCerrarSesion.addActionListener(e -> {
            new LoginView();
            dispose();
        });

        setVisible(true);
    }
}
