package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.UsuarioYProducto;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, String> {

    List<Usuario> findAllByNombreContains(String nombre);

    Optional<Usuario> findByEmail (String email);

    Optional<Usuario> findByEmailAndPassword (String email, String clave);

    Page<Usuario> findAll(Pageable paginador);

    @Query("select p from Usuario u, IN (u.productosFavoritos) p where u.email = :email")
    List<Producto> obtenerProductosFavoritos(String email);

    @Query("select new co.edu.uniquindio.proyecto.dto.UsuarioYProducto(u.email, u.nombre, p) from Usuario u left join u.productosVenta p")
    List<UsuarioYProducto> listarUsuariosYProductos();

    @Query("select distinct c.usuario from Producto p join p.comentarios c where p.codigo = :id")
    List<Usuario> listarUsuariosComentario(Integer id);

}
