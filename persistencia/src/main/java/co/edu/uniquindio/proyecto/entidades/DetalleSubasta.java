package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.time.LocalDateTime;

//Entidad DetalleSubasta

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class DetalleSubasta implements Serializable {

    //Llave primaria de la entidad que es autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    //Atributo que sirve para guardar el valor del producto de la subasta
    @Column(nullable = false, precision = 9, scale = 2)
    private Double valor;

    //Fecha de la subasta
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaSubasta;

    //Relacion de DetalleSubasta con Usuario de muchos a uno
    @ManyToOne
    @ToString.Exclude
    private Usuario usuario;

    //Relacion de DetalleSubasta con Subasta de muchos a uno
    @ManyToOne
    @ToString.Exclude
    private Subasta subasta;
}
