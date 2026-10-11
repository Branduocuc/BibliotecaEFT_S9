package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.UsuarioDAO;
import cl.duoc.biblioteca.dao.UsuarioDAOImpl;
import cl.duoc.biblioteca.model.Usuario;




import java.sql.SQLException;

public class LoginController {

    private final UsuarioDAO usuarioDAO;

    public LoginController() throws SQLException {
        this.usuarioDAO = new UsuarioDAOImpl();





    }

    public Usuario autenticar(String correo, String contrasenia) {
        return usuarioDAO.autenticar(correo, contrasenia);
    }
}