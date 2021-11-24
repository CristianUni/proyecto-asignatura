package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements  UsuarioServicio{

    private final UsuarioRepo usuarioRepo;

    public UsuarioServicioImpl(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Override
    public Usuario registrarUsuario(Usuario u) throws Exception {

        Optional<Usuario> buscado = usuarioRepo.findById(u.getCodigo());

        if (buscado.isPresent()){
            throw new Exception("El código del usuario ya existe.");
        }

        buscado = buscarPorEmail(u.getEmail());

        if (buscado.isPresent()){
            throw new Exception("El email del usuario ya existe.");
        }


        buscado = usuarioRepo.findByUsername(u.getUsername());

        if (buscado.isPresent()){
            throw new Exception("El username del usuario ya existe.");
        }

        return usuarioRepo.save(u);
    }

    @Override
    public Usuario actualizarUsuario(Usuario u) throws Exception {

      Optional<Usuario> buscado = buscarPorEmail(u.getEmail());

        if (buscado.isPresent()){
            throw new Exception("El email del usuario ya existe.");
        }


        buscado = usuarioRepo.findByUsername(u.getUsername());

        if (buscado.isPresent()){
            throw new Exception("El username del usuario ya existe.");
        }

         buscado = usuarioRepo.findById(u.getCodigo());

        if (buscado.isEmpty())
        {
            throw new Exception("El usuario no existe");
        }

        return usuarioRepo.save(u);
    }

    private Usuario buscarUsuario(Usuario u) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(u.getCodigo());

        if (buscado.isPresent()){
            throw new Exception("El código del usuario ya existe.");
        }

       buscado = buscarPorEmail(u.getEmail());

        if (buscado.isPresent()){
            throw new Exception("El email del usuario ya existe.");
        }


        buscado = usuarioRepo.findByUsername(u.getUsername());

        if (buscado.isPresent()){
            throw new Exception("El username del usuario ya existe.");
        }

        return usuarioRepo.save(u);
    }

    @Override
    public void eliminarUsuario(String codigo) throws Exception {

        Optional<Usuario> buscado = usuarioRepo.findById(codigo);

        if (buscado.isEmpty()){
            throw new Exception("El código del usuario no existe.");
        }

        usuarioRepo.deleteById(codigo);
    }

    private Optional<Usuario> buscarPorEmail (String email)
    {
       return  usuarioRepo.findByEmail(email);


    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public List<Producto> listarFavoritos(String email) throws Exception {

        Optional<Usuario> buscado = buscarPorEmail(email);
        if (buscado.isEmpty())
        {
            throw new Exception("El correo no existe");
        }

        return usuarioRepo.obtenerProductosFavoritos(email);
    }

    @Override
    public Usuario obtenerUsuario(String codigo) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(codigo);

        if (buscado.isEmpty()){
            throw new Exception("El usuario no existe.");
        }

        return buscado.get();
    }

    @Override
    public Usuario iniciarSesion(String email, String password) throws Exception {

       return usuarioRepo.findByEmailAndPassword(email, password).orElseThrow(() -> new Exception("Los datos de autenticación son incorrecots"));


    }
}
