package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@ToString
public class Persona implements Serializable {

    @Id
    @Column(length = 10)
    @EqualsAndHashCode.Include
    private String codigo;

    @Column(nullable = false,length = 100)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    @Enumerated
    private GeneroPersona generoPersona;
}
