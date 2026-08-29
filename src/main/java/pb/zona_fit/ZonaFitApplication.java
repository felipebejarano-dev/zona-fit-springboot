package pb.zona_fit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pb.zona_fit.modelo.Cliente;
import pb.zona_fit.servicio.IClienteServicio;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

    @Autowired
    private IClienteServicio clienteServicio;

    private static final Logger logger = LoggerFactory.getLogger (ZonaFitApplication.class);

    public static void main (String[] args) {
        logger.info ("FELIPE BEJARANO CASTAÑO - INGENIERÍA DE SISTEMAS Y COMPUTACIÓN");
        SpringApplication.run (ZonaFitApplication.class, args);
        logger.info ("Aplicación ZonaFitApplication finalizada.");
    }

    @Override
    public void run (String... args) throws Exception {
        zonaFitApp ();
    }

    private void zonaFitApp () {
        logger.info ("---- APLICACION ZONA FIT INICIADA (GYM) ----");
        boolean salir = false;
        Scanner sc = new Scanner (System.in);

        while (! salir) {
            try {
                int opcion = mostrarMenu (sc);
                salir = ejecutarOpciones (opcion, sc);
            } catch ( Exception e ) {
                // Captura cualquier error inesperado dentro del ciclo y evita que la app muera
                logger.error ("Error en la ejecución del sistema: {}", e.getMessage ());
            }
            logger.info ("");
        }
    }

    private int mostrarMenu (Scanner sc) {
        logger.info ("---- MENÚ DE OPCIONES ----");
        logger.info ("1. Listar clientes");
        logger.info ("2. Buscar cliente por ID");
        logger.info ("3. Guardar cliente");
        logger.info ("4. Actualizar cliente");
        logger.info ("5. Eliminar cliente");
        logger.info ("6. Salir");
        // Validación de entrada numérica
        int opcion = leerEnteroSeguro (sc, "Ingrese una opción válida (1-6): ");
        return opcion;
    }

    private boolean ejecutarOpciones (int opcion, Scanner sc) {
        try {
            switch ( opcion ) {
                case 1 -> {
                    var clientes = clienteServicio.listarClientes ();
                    if ( clientes.isEmpty () ) {
                        logger.info ("No hay clientes registrados.");
                    } else {
                        logger.info ("===============================================================");
                        // Encabezado con anchos de columna definidos
                        logger.info (String.format ("%-5s | %-6s | %-15s | %-15s | %-10s",
                                "#", "ID", "NOMBRE", "APELLIDO", "MEMBRESÍA"));
                        logger.info ("===============================================================");

                        AtomicInteger consecutivo = new AtomicInteger (1);

                        // Imprimir cada fila alineada
                        clientes.forEach (cliente -> {
                            String fila = String.format ("%-5d | %-6d | %-15s | %-15s | %-10d",
                                    consecutivo.getAndIncrement (),
                                    cliente.getId (),
                                    cliente.getNombre (),
                                    cliente.getApellido (),
                                    cliente.getMembresia ());
                            logger.info (fila);
                        });

                        logger.info ("===============================================================");
                    }
                }

                case 2 -> {
                    // Utiliza el método seguro: no avanza hasta recibir un número válido
                    Integer idBuscar = leerEnteroSeguro (sc, "Ingrese el ID del cliente a buscar: ");

                    Cliente cliente = clienteServicio.buscarClientePorId (idBuscar);

                    if ( cliente != null ) {
                        logger.info ("===============================================================");
                        // Encabezado con anchos de columna definidos
                        logger.info (String.format ("%-6s | %-15s | %-15s | %-10s",
                                "ID", "NOMBRE", "APELLIDO", "MEMBRESÍA"));
                        logger.info ("===============================================================");

                        String fila = String.format ("%-6d | %-15s | %-15s | %-10d",
                                cliente.getId (),
                                cliente.getNombre (),
                                cliente.getApellido (),
                                cliente.getMembresia ());
                        logger.info (fila);
                        logger.info ("===============================================================");
                    } else {
                        logger.warn ("Cliente con ID {} no encontrado.", idBuscar);
                    }
                }

                case 3 -> {
                    logger.info("Ingrese el nombre del cliente: ");
                    String nombre = sc.nextLine();
                    logger.info("Ingrese el apellido del cliente: ");
                    String apellido = sc.nextLine();
                    Integer membresia = leerEnteroSeguro(sc, "Ingrese la membresía del cliente (número entero): ");

                    var cliente = new Cliente();
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setMembresia(membresia);

                    clienteServicio.guardarCliente(cliente);

                    logger.info("===============================================================");
                    logger.info("                  CLIENTE GUARDADO CON ÉXITO                   ");
                    logger.info("===============================================================");
                    logger.info(String.format("%-6s | %-15s | %-15s | %-10s", "ID", "NOMBRE", "APELLIDO", "MEMBRESÍA"));
                    logger.info("===============================================================");
                    logger.info(String.format("%-6s | %-15s | %-15s | %-10d",
                            (cliente.getId() != null ? cliente.getId() : "N/A"),
                            cliente.getNombre(),
                            cliente.getApellido(),
                            cliente.getMembresia()));
                    logger.info("===============================================================");
                }

                case 4 -> {

                    Integer idActualizar = leerEnteroSeguro (sc, "Ingrese el ID del cliente a actualizar: ");
                    Cliente clienteActualizar = clienteServicio.buscarClientePorId (idActualizar);

                    if ( clienteActualizar != null ) {
                        logger.info ("Ingrese el nuevo nombre del cliente: ");
                        String nuevoNombre = sc.nextLine ();
                        logger.info ("Ingrese el nuevo apellido del cliente: ");
                        String nuevoApellido = sc.nextLine ();
                        Integer nuevaMembresia = leerEnteroSeguro (sc, "Ingrese la nueva membresía del cliente (número entero): ");

                        clienteActualizar.setNombre (nuevoNombre);
                        clienteActualizar.setApellido (nuevoApellido);
                        clienteActualizar.setMembresia (nuevaMembresia);
                        clienteServicio.actualizarCliente (clienteActualizar);
                        logger.info("===============================================================");
                        logger.info("                  CLIENTE ACTUALIZADO CON ÉXITO                   ");
                        logger.info("===============================================================");
                        logger.info(String.format("%-6s | %-15s | %-15s | %-10s", "ID", "NOMBRE", "APELLIDO", "MEMBRESÍA"));
                        logger.info("===============================================================");
                        logger.info(String.format("%-6s | %-15s | %-15s | %-10d",
                                (clienteActualizar.getId() != null ? clienteActualizar.getId() : "N/A"),
                                clienteActualizar.getNombre(),
                                clienteActualizar.getApellido(),
                                clienteActualizar.getMembresia()));
                        logger.info("===============================================================");
                    } else {
                        logger.warn ("No se puede actualizar. Cliente no encontrado.");
                    }
                }

                case 5 -> {
                    Integer idEliminar = leerEnteroSeguro (sc, "Ingrese el ID del cliente a eliminar: ");
                    Cliente clienteEliminar = clienteServicio.buscarClientePorId (idEliminar);

                    if ( clienteEliminar != null ) {
                        clienteServicio.eliminarCliente (idEliminar);
                        logger.info ("Cliente eliminado correctamente.");
                    } else {
                        logger.warn ("No se puede eliminar. Cliente no encontrado.");
                    }
                }

                case 6 -> {
                    logger.info ("Saliendo del programa...");
                    return true;
                }

                default -> logger.warn ("Opción inválida. Intente nuevamente.");
            }
        } catch ( NumberFormatException e ) {
            logger.error ("Error: Se esperaba un valor numérico entero.");
        } catch ( Exception e ) {
            logger.error ("Error al procesar la operación: {}", e.getMessage ());
        }

        return false;
    }

    private Integer leerEnteroSeguro (Scanner sc, String mensaje) {
        while (true) {
            logger.info (mensaje);
            try {
                return Integer.parseInt (sc.nextLine ());
            } catch ( NumberFormatException e ) {
                logger.warn ("¡Entrada inválida! Debes ingresar un número entero.");
            }
        }
    }
}