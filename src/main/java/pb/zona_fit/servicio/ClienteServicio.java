package pb.zona_fit.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pb.zona_fit.modelo.Cliente;
import pb.zona_fit.repositorio.ClienteRepositorio;

import java.util.List;

@Service//Se indica que esta clase es un servicio de Spring y sirve para la inyección de dependencias
public class ClienteServicio implements IClienteServicio {

    @Autowired//Se indica para que Spring inyecte la dependencia de ClienteRepositorio
    private ClienteRepositorio clienteRepositorio;//Se indica que esta clase es un servicio de Spring

    @Override
    public List<Cliente> listarClientes () {
        List<Cliente> clientes = clienteRepositorio.findAll ();
        return clientes;
    }

    @Override
    public Cliente buscarClientePorId (Integer id) {
        return clienteRepositorio.findById (id).orElse (null);
        // Se utiliza el método findById del repositorio para buscar un cliente por su id. Si no se encuentra, se devuelve null.
    }

    @Override
    public void guardarCliente (Cliente cliente) {

        clienteRepositorio.save (cliente);

    }

    @Override
    public void actualizarCliente (Cliente cliente) {
        clienteRepositorio.save (cliente);
    }

    @Override
    public void eliminarCliente (Integer id) {
        clienteRepositorio.deleteById (id);

    }

}