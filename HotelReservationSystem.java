//Universidad del Valle de Guatemala - POO - 2023
//Mauricio Enrique Montenegro González - 23679
//Ejercicio 2 - Este codigo intenta recrear una aplicacion para realizar reservaciones en un hotel.

import java.util.ArrayList;
import java.util.Scanner;

public class HotelReservationSystem {
    private static Habitacion[] habitaciones;
    private static ListaEspera listaEspera;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenido al Sistema de Reservas del Hotel");
        System.out.print("Ingrese el número de habitaciones disponibles: ");
        int numHabitaciones = scanner.nextInt();
        
        // Crear un arreglo de habitaciones según el número ingresado
        habitaciones = new Habitacion[numHabitaciones];
        for (int i = 0; i < numHabitaciones; i++) {
            habitaciones[i] = crearHabitacion(i + 1);
        }
        
        // Inicializar la lista de espera con capacidad para 10 clientes
        listaEspera = new ListaEspera(10);
        
        int opcion;
        do {
            mostrarMenu();  // Mostrar el menú de opciones al usuario
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    recibirHuesped();  // Invocar el método para recibir un huésped
                    break;
                case 2:
                    asignarHabitacion();  // Invocar el método para asignar una habitación
                    break;
                case 3:
                    mostrarEstadoHabitaciones();  // Invocar el método para mostrar el estado de las habitaciones
                    break;
                case 4:
                    mostrarListaEspera();  // Invocar el método para mostrar la lista de espera
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el Sistema de Reservas del Hotel!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
        
        scanner.close();
    }

    // Método para mostrar el menú de opciones al usuario
    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Recibir huésped");
        System.out.println("2. Asignar habitación");
        System.out.println("3. Mostrar estado de habitaciones");
        System.out.println("4. Mostrar lista de espera");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Método para crear una nueva habitación y obtener su información desde el usuario
private static Habitacion crearHabitacion(int numero) {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Ingrese la capacidad máxima de ocupantes para la Habitación " + numero + ":");
        int capacidadMaxima = scanner.nextInt();

        System.out.println("Ingrese el precio por noche para la Habitación " + numero + ":");
        double precioPorNoche = scanner.nextDouble();

        System.out.println("Ingrese el tipo de habitación para la Habitación " + numero + " (1: Estándar, 2: Deluxe, 3: Suite):");
        int tipoHabitacion = scanner.nextInt();
        TipoHabitacion tipo;

        // Asignar el tipo de habitación en base a la opción ingresada por el usuario
        if (tipoHabitacion == 1) {
            tipo = TipoHabitacion.ESTANDAR;
        } else if (tipoHabitacion == 2) {
            tipo = TipoHabitacion.DELUXE;
        } else if (tipoHabitacion == 3) {
            tipo = TipoHabitacion.SUITE;
        } else {
            System.out.println("Opción inválida. Se asignará habitación estándar por defecto.");
            tipo = TipoHabitacion.ESTANDAR;
        }

        // Crear una nueva instancia de Habitacion con los datos proporcionados
        return new Habitacion(numero, capacidadMaxima, precioPorNoche, tipo);
    }
}

// Método para recibir datos del huésped y agregarlo a la lista de espera
private static void recibirHuesped() {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Ingrese el nombre del huésped:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el número de visitas del huésped:");
        int visitas = scanner.nextInt();

        TipoCliente tipo;

        // Asignar el tipo de cliente en base al número de visitas
        if (visitas < 5) {
            tipo = TipoCliente.REGULAR;
        } else if (visitas < 10) {
            tipo = TipoCliente.FRECUENTE;
        } else {
            tipo = TipoCliente.VIP;
        }

        // Crear una nueva instancia de Cliente y agregarlo a la lista de espera
        Cliente cliente = new Cliente(nombre, visitas, tipo);
        listaEspera.agregarCliente(cliente);

        System.out.println("Huésped " + nombre + " agregado a la lista de espera.");
    }
}

    private static void asignarHabitacion() {
        if (listaEspera.hayClientes()) {
            Cliente cliente = listaEspera.obtenerPrimerCliente();
            Habitacion habitacionAsignada = null;
    
            for (Habitacion habitacion : habitaciones) {
                if (!habitacion.isOcupada()) {
                    TipoCliente tipoCliente = cliente.getTipo();
                    if (habitacion.esTipoAdecuado(tipoCliente)) {
                        habitacionAsignada = habitacion;
                        break;
                    }
                }
            }
    
            if (habitacionAsignada != null) {
                habitacionAsignada.ocuparHabitacion();
                listaEspera.eliminarCliente(cliente);
                System.out.println("Habitación asignada al huésped " + cliente.getNombre());
            } else {
                System.out.println("No se pudo asignar una habitación al huésped " + cliente.getNombre() + ". Se mantendrá en lista de espera.");
            }
        } else {
            System.out.println("No hay huéspedes en lista de espera.");
        }
    }

    private static void mostrarEstadoHabitaciones() {
        System.out.println("Estado actual de las habitaciones:");
        for (Habitacion habitacion : habitaciones) {
            String estado = habitacion.isOcupada() ? "Ocupada" : "Disponible";
            System.out.println("Habitación " + habitacion.getNumero() + ": " + estado);
        }
    }

    private static void mostrarListaEspera() {
        System.out.println("Lista de espera de huéspedes:");
        for (Cliente cliente : listaEspera.getClientes()) {
            System.out.println("Cliente: " + cliente.getNombre());
        }
    }
    
}

class Habitacion {
    private int numero;
    private int capacidadMaxima;
    private double precioPorNoche;
    private boolean ocupada;

    public Habitacion(int numero, int capacidadMaxima, double precioPorNoche, TipoHabitacion tipo) {
        this.numero = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.ocupada = false;
    }

    public boolean esTipoAdecuado(TipoCliente tipoCliente) {
        return false;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocuparHabitacion() {
        ocupada = true;
    }

    public void desocuparHabitacion() {
        ocupada = false;
    }
}

class ListaEspera {
    private ArrayList<Cliente> clientes;

    public ListaEspera() {
        clientes = new ArrayList<>();
    }

    public ListaEspera(int i) {
    }

    public Cliente[] getClientes() {
        return clientes.toArray(new Cliente[0]);
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente obtenerPrimerCliente() {
        if (!clientes.isEmpty()) {
            return clientes.get(0);
        }
        return null;
    }

    public boolean hayClientes() {
        return !clientes.isEmpty();
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("El cliente " + cliente.getNombre() + " ha sido agregado a la lista de espera.");
    }
}

class Cliente {
    private String nombre;
    private int visitas;
    private TipoCliente tipo;

    public Cliente(String nombre, int visitas, TipoCliente tipo) {
        this.nombre = nombre;
        this.visitas = visitas;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public int getVisitas() {
        return visitas;
    }
}

