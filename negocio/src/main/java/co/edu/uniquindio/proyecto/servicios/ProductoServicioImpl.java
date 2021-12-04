package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ProductoCarrito;
import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.excepciones.ProductoNoEncontradoException;
import co.edu.uniquindio.proyecto.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepo productoRepo;

    private final ComentarioRepo comentarioRepo;

    private final CategoriaRepo categoriaRepo;

    private final CompraRepo compraRepo;

    private final DetalleCompraRepo detalleCompraRepo;

    public ProductoServicioImpl(ProductoRepo productoRepo, ComentarioRepo comentarioRepo, CategoriaRepo categoriaRepo, CompraRepo compraRepo, DetalleCompraRepo detalleCompraRepo) {
        this.productoRepo = productoRepo;
        this.comentarioRepo = comentarioRepo;
        this.categoriaRepo = categoriaRepo;
        this.compraRepo = compraRepo;
        this.detalleCompraRepo = detalleCompraRepo;

    }

    @Override
    public Producto publicarProducto(Producto p) throws Exception {
        try {
            return productoRepo.save(p);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void actualizarProducto(Producto producto) throws Exception {
        try {
            productoRepo.save(producto);
        } catch (Exception e){
            throw new Exception("Error al actualizar el producto");
        }

    }

    @Override
    public void eliminarProducto(Integer codigo) throws Exception {

        Optional<Producto> producto = productoRepo.findById(codigo);

        if (producto.isEmpty()) {
            throw new Exception("El código del producto no existe");
        }

        productoRepo.deleteById(codigo);
    }

    @Override
    public Producto obtenerProducto(Integer codigo) throws ProductoNoEncontradoException {
        return productoRepo.findById(codigo).orElseThrow(() -> new ProductoNoEncontradoException("El código del producto no es válido"));
    }

    @Override
    public List<Producto> listarProductos(Categoria categoria) {

        return productoRepo.listarPorCategoria(categoria);
    }

    @Override
    public void comentarProducto(Comentario comentario) throws Exception {

        comentario.setFechaComentario(LocalDateTime.now());
        comentarioRepo.save(comentario);
    }

    @Override
    public void guardarProductoFavorito(Producto producto, Usuario usuario) throws Exception {

    }

    @Override
    public void eliminarProductoFavorito(Producto producto, Usuario usuario) throws Exception {

    }

    @Override
    public void comprarProductos(Compra compra) throws Exception {

    }

    @Override
    public List<Producto> buscarProductos(String nombreProducto, String[] filtros) {

        return productoRepo.buscarProductosPorNombre(nombreProducto);
    }

    @Override
    public List<Producto> listarProductosUsuario(String codigoUsuario) throws Exception {
        return null;
    }

    @Override
    public List<Producto> listarTodosProductos() throws Exception {
        return productoRepo.findAll();
    }

    @Override
    public List<Producto> obtenerMisProductos(String codigo) throws Exception {
        return productoRepo.obtenerMisProductos(codigo);
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepo.findAll();
    }

    @Override
    public Categoria obtenerCategoria(int id) throws Exception {
        return categoriaRepo.findById(id).orElseThrow(() -> new Exception("No se encontró una categoría con ese id"));
    }

    @Override
    public Compra comprarProductos(Usuario usuario, ArrayList<ProductoCarrito> productos, String medioPago) throws Exception{

        try {

            Compra compra = new Compra();
            compra.setFechaCompra(LocalDateTime.now(ZoneId.of("America/Bogota")));
            compra.setUsuario(usuario);
            compra.setMedioPago(medioPago);
            Compra compraGuardada = compraRepo.save(compra);

            DetalleCompra dc;
            for (ProductoCarrito p : productos){
                dc = new DetalleCompra();
                dc.setCompra(compraGuardada);
                dc.setPrecioProducto(p.getPrecio());
                dc.setUnidades((p.getUnidades()));
                dc.setProducto(productoRepo.findById(p.getId()).get());
                if(productoRepo.findById(p.getId()).get().getUnidades() < p.getUnidades()){
                    throw new Exception("El producto " + productoRepo.findById(p.getId()).get().getNombre() + " tiene " + productoRepo.findById(p.getId()).get().getUnidades() + " unidades disponibles");
                }

                int unidadesNuevas = productoRepo.findById(p.getId()).get().getUnidades() - p.getUnidades();
                Producto productoAux = productoRepo.findById(p.getId()).orElse(null);
                productoAux.setUnidades(unidadesNuevas);
                productoRepo.save(productoAux);
                detalleCompraRepo.save(dc);
            }
            return compraGuardada;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

}
