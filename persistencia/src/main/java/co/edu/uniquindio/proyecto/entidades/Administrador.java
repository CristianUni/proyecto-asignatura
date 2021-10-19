package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

//Entidad Administrador que hereda atributos y métodos de la entidad Persona

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class Administrador extends Persona implements Serializable {
}
