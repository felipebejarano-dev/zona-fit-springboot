package pb.zona_fit.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pb.zona_fit.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    //Esta interfaz extiende JpaRepository, lo que permite realizar operaciones CRUD en la entidad Cliente sin necesidad de implementar métodos adicionales.

}
