package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, String> {

    List<Usuario> findAllByNombreContains(String nombre);

    Optional<Usuario> findByEmail (String email);

    Optional<Usuario> findByEmailAndPassword (String email, String clave);

    Page<Usuario> findAll(Pageable paginador);
}
