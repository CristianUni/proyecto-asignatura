package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.dto.ProductoCarrito;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Scope("session")
@Component
public class SeguridadBean implements Serializable {

    @Getter @Setter
    private boolean autenticado;

    @Getter @Setter
    private String email, password;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Getter @Setter
    private Usuario usuarioSesion;

    @Getter @Setter
    private ArrayList<ProductoCarrito> productosCarrito;

    @Getter @Setter
    private double subTotal;

    @Autowired
    private ProductoServicio productoServicio;

    @Getter @Setter
    private String medioPago;

    @Getter
    @Setter
    private List<Producto> misProductos;

    @Getter @Setter
    private List<Producto> productosFavoritos;


    @PostConstruct
    public void inicializar(){
        this.subTotal = 0;
        productosCarrito = new ArrayList<>();
        this.medioPago = "";

        if(usuarioSesion != null)
        {
            actualizarListaProductosPublicados();
            actualizarListaProductosFavoritos();
        }

    }

    private void actualizarListaProductosFavoritos() {
        try {
            this.productosFavoritos = usuarioServicio.listarFavoritos(usuarioSesion.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verificarFavortio(Producto producto) {

        if (usuarioSesion.getProductosFavoritos().contains(producto)){
            return true;
        }
        return false;

    }

    public String iniciarSesion(){

        if(!email.isEmpty() && !password.isEmpty()){
            try {
                usuarioSesion = usuarioServicio.iniciarSesion(email, password);
                autenticado=true;

                actualizarListaProductosPublicados();
                actualizarListaProductosFavoritos();

                return "/index.xhtml?faces-redirect=true";
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }
        return null;
    }

    public void actualizarListaProductosPublicados() {
        try {
            this.misProductos = productoServicio.obtenerMisProductos(usuarioSesion.getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml=faces-redirect=true";
    }

    public void agregarAlCarrito(Integer id, double precio, String nombre, String imagen){

        ProductoCarrito productoCarrito =  new ProductoCarrito(id, 1, nombre, imagen, precio);
        if(!productosCarrito.contains(productoCarrito)){
            productosCarrito.add(productoCarrito);
            subTotal += precio;
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Producto agregado al carrito");
            FacesContext.getCurrentInstance().addMessage("add-cart", fm);
        }
    }
    public void eliminarCarrito(int indice){
        subTotal -= productosCarrito.get(indice).getPrecio();
        productosCarrito.remove(indice);
    }

    public void actualizarSubtotal(){
        subTotal = 0;

        for (ProductoCarrito p: productosCarrito) {
            subTotal += p.getPrecio()*p.getUnidades();
        }
    }

    public void comprar(){
        if(usuarioServicio != null && !productosCarrito.isEmpty() && !medioPago.isEmpty()){
            try {
                productoServicio.comprarProductos(usuarioSesion, productosCarrito, medioPago);
                productosCarrito.clear();
                subTotal = 0;
                actualizarListaProductosPublicados();
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Compra realizada con éxito");
                FacesContext.getCurrentInstance().addMessage("compra-msj", fm);
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("compra-msj", fm);

            }
        }
    }

    public void agregarFavorito(Producto productoFavorito) throws Exception{
        try {
            if(productoFavorito != null){
                System.out.println("entró a prod");
                usuarioServicio.agregarFavorito(productoFavorito, usuarioSesion.getCodigo());
                actualizarListaProductosFavoritos();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Producto agregado a favoritos");
                FacesContext.getCurrentInstance().addMessage("favorito-msj", fm);

            }
        }catch (Exception e){
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "El producto ya está en favoritos");
            FacesContext.getCurrentInstance().addMessage("favorito-msj", fm);
        }
    }

    public void eliminarFavorito(Producto productoFavorito) throws Exception{
        try {
            if(productoFavorito != null){
                usuarioServicio.eliminarFavorito(productoFavorito, usuarioSesion.getCodigo());
                actualizarListaProductosFavoritos();
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Producto agregado a favoritos");
                FacesContext.getCurrentInstance().addMessage("favorito-msj", fm);

            }
        }catch (Exception e){
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "El productro ya está en favoritos");
            FacesContext.getCurrentInstance().addMessage("favorito-msj", fm);
        }
    }


}
