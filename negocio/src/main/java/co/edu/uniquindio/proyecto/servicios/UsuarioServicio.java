package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioServicio {

    Usuario registrarUsuario (Usuario u) throws Exception;

    Usuario actualizarUsuario(Usuario u) throws Exception;

    void eliminarUsuario(String codigo) throws Exception;

    List<Usuario> listarUsuarios() ;

    List<Producto> listarFavoritos (String email) throws Exception;

    Usuario obtenerUsuario (String codigo ) throws Exception;



}
