package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Entidad Producto
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Producto implements Serializable {

    //Llave primaria de la entidad que es autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    //Atributo nombre del producto
    @Column(nullable = false, length = 100)
    private String nombre;

    //Atributo unidades disponibles de un producto
    @Column(nullable = false)
    @Positive
    private int unidades;

    //Atributo descripcion de un producto
    @Column(nullable = false, length = 1000)
    private String descripcion;

    //Atributo precio del producto en el instante actual
    @Column(nullable = false, precision = 9, scale = 2)
    @Positive
    private double precio;

    //Atributo descuento del producto
    @Column(nullable = false, precision = 3, scale = 2)
    @Positive
    private double descuento;

    //Atributo de la fecha limite del producto
    @Column(nullable = false)
    private LocalDate fechaLimite;

    //Relacion de muchos a uno con Usuario
    @ToString.Exclude
    @ManyToOne
    private Usuario vendedor;

    //Relacion de muchos a uno con Ciudad
    @ToString.Exclude
    @ManyToOne
    private Ciudad ciudad;

    //Lista de rutas para las imagenes
    @ToString.Exclude
    @ElementCollection
    private List<String> rutaImagen;

    //Relacion de uno a muchos con DetalleCompra
    @ToString.Exclude
    @OneToMany(mappedBy = "producto")
    private List<DetalleCompra> detalleCompras;

    //Relacion de uno a muchos con Subasta
    @ToString.Exclude
    @OneToMany(mappedBy = "producto")
    private List<Subasta> subastas;

    //Relacion de uno a muchos con Comentario
    @ToString.Exclude
    @OneToMany(mappedBy = "producto")
    private List<Comentario> comentarios;

    //Relacion de uno a muchos con Chat
    @ToString.Exclude
    @OneToMany(mappedBy = "producto")
    private List<Chat> chats;

    //Relacion de muchos a muchos con Usuario
    @ToString.Exclude
    @ManyToMany(mappedBy = "productosFavoritos")
    private List<Usuario> usuarios;

    //Relacion de muchos a muchos con Categoria
    @ToString.Exclude
    @ManyToMany
    private List<Categoria> categorias;


    public Producto(String nombre, int unidades, String descripcion, double precio, double descuento, LocalDate fechaLimite) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.descripcion = descripcion;
        this.precio = precio;
        this.descuento = descuento;
        this.fechaLimite = fechaLimite;
    }
}
