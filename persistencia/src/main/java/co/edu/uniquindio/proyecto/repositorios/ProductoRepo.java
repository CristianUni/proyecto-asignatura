package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.ProductosPorUsuario;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {

    Page<Producto> findAll(Pageable paginador);

    @Query("select p.vendedor.nombre from Producto p where p.codigo = :id")
    String obtenerNombreVendedor (Integer id);

    @Query("select p.nombre, c from Producto p left join p.comentarios c ")
    List<Object[]> listarProductosComentarios();

    @Query("select new co.edu.uniquindio.proyecto.dto.ProductoValido(p.nombre, p.descripcion, p.precio, p.ciudad) from Producto p where :fechaActual < p.fechaLimite ")
    List<Object[]> listarProductosValidos(LocalDateTime fechaActual);

    @Query("select c, count(p) from Producto p join p.categorias c group by c")
    List<Object[]> obtenerTotalProductosPorCategoria();

    @Query("select p from Producto p where p.comentarios is empty")
    List<Producto> obtenerProductosSinComentarios();

    @Query("select p from Producto p where p.nombre like concat('%', :nombre, '%') ")
    List<Producto> buscarProductosPorNombre(String nombre);

    @Query("select new co.edu.uniquindio.proyecto.dto.ProductosPorUsuario(p.vendedor.codigo, p.vendedor.email, count(p)) from Producto p group by p.vendedor")
    List<ProductosPorUsuario> obtenerProductosEnVenta();

    @Query("select c, count(p) as total from Producto p join p.categorias c group by c order by total desc")
    List<Object[]> obtenerCategoriaMasUsada();

    @Query("select avg(c.calificacion) from Producto p join p.comentarios c where p.codigo = :codigo")
    Float obtenerPromedioCalificaciones(Integer codigo);
}
