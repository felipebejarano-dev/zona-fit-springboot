package pb.zona_fit.servicio;

import pb.zona_fit.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {

    // Aquí se definen los métodos para interactuar con el servicio
    public List<Cliente> listarClientes();
    public Cliente buscarClientePorId(Integer id);
    public void guardarCliente(Cliente cliente);
    public void actualizarCliente(Cliente cliente);
    public void eliminarCliente(Integer id);

}
