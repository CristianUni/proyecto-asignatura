package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Autor extends Persona implements Serializable {

    @Max(9999)
    @Column(nullable = false)
    private Integer anioNacimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;


}
