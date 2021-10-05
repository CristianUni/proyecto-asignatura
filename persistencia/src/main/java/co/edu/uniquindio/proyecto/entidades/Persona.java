package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Map;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Persona implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String cedula;
    private String nombre;
    private String email;

    @ElementCollection
    private Map<String, String> numTelefonos;

    @Enumerated
    private GeneroPersona generoPersona;


    public Persona(String cedula, String nombre, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
    }

}
