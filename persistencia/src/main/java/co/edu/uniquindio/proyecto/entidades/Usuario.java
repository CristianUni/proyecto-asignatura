package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//Entidad hijo Usuario
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Usuario extends Persona implements Serializable {

    //Lista de telefonos del usuario
    @ElementCollection
    private List<String> telefonos;

    @Column(nullable = false, unique = true)
    private String username;

    //Relacion de muchos a uno con Ciudad
    @ManyToOne
    @ToString.Exclude
    //@JoinColumn(nullable = false)
    private Ciudad ciudad;

    //Relacion de uno a muchos con Producto
    @OneToMany(mappedBy = "vendedor")
    @ToString.Exclude
    private List<Producto> productosVenta;

    //Relacion de uno a muchos con Comentario
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<Comentario> comentarios;

    //Relacion de uno a muchos con Compra
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<Compra> compras;

    //Relacion de uno a muchos con DetalleSubasta
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<DetalleSubasta> detalleSubasta;

    //Relacion de uno a muchos con Chat
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<Chat> chats;

    //Relacion de muchos a muchos con Producto
    @ManyToMany
    @ToString.Exclude
    private List<Producto> productosFavoritos;

    public Usuario(String codigo, String nombre, String email, String password, String username) {
        super(codigo, nombre, email, password);
        this.username = username;
    }
}
