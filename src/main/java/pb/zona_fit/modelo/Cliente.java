package pb.zona_fit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity//Se indica que esta clase es una entidad de la base de datos
@Data//Se indica que esta clase tiene métodos getter y setter
@NoArgsConstructor//Se indica que esta clase tiene un constructor sin argumentos
@AllArgsConstructor//Se indica que esta clase tiene un constructor con argumentos
@ToString//Se indica que esta clase tiene un método toString
@EqualsAndHashCode//Se indica que esta clase tiene un método equals y hashCode
public class Cliente {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer membresia;

}
