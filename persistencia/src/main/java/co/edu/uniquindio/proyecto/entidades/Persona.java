package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

//Padre persona
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@ToString
public class Persona implements Serializable {

    //Llave primaria de la entidad
    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    //Atributo nombre de la persona
    @Column(nullable = false,length = 150)
    private String nombre;

    //Atributo email de la persona
    @Column(nullable = false, unique = true)
    private String email;

    //Atributo contrasena de la persona
    @Column(nullable = false, length = 100)
    private String password;
}
