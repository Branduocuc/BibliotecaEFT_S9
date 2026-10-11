package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.model.Usuario;
import java.util.List;

public interface UsuarioDAO {


    void insertar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(Usuario usuario);
    List<Usuario> listar();
    Usuario autenticar(String correo, String contrasenia);



}
