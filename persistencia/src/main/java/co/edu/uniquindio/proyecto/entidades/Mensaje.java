package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

//Entidad Mensaje

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Mensaje implements Serializable {

    //Llave primaria de la entidad que es autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    //Atributo que sirve para guardar el mensaje
    @Column(nullable = false, length = 450)
    private String mensaje;

    @Column(nullable = false)
    private String emisor;

    //Fecha del mensaje al momento de ser enviado
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    //Relación muchos a uno con la entidad Chat
    @ManyToOne
    @ToString.Exclude
    private Chat chat;
}
