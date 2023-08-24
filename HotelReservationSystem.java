import java.util.Scanner;

public class HotelReservationSystem {
    private static Habitacion[] habitaciones;
    private static ListaEspera listaEspera;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bienvenido al Sistema de Reservas del Hotel");
        System.out.print("Ingrese el número de habitaciones disponibles: ");
        int numHabitaciones = scanner.nextInt();
        
        habitaciones = new Habitacion[numHabitaciones];
        for (int i = 0; i < numHabitaciones; i++) {
            habitaciones[i] = crearHabitacion(i + 1);
        }
        
        listaEspera = new ListaEspera(10);
        
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    recibirHuesped();
                    break;
                case 2:
                    asignarHabitacion();
                    break;
                case 3:
                    mostrarEstadoHabitaciones();
                    break;
                case 4:
                    mostrarListaEspera();
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

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Recibir huésped");
        System.out.println("2. Asignar habitación");
        System.out.println("3. Mostrar estado de habitaciones");
        System.out.println("4. Mostrar lista de espera");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static Habitacion crearHabitacion(int numero) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese la capacidad máxima de ocupantes para la Habitación " + numero + ":");
            int capacidadMaxima = scanner.nextInt();
   
            System.out.println("Ingrese el precio por noche para la Habitación " + numero + ":");
            double precioPorNoche = scanner.nextDouble();
   
            System.out.println("Ingrese el tipo de habitación para la Habitación " + numero + " (1: Estándar, 2: Deluxe, 3: Suite):");
            int tipoHabitacion = scanner.nextInt();
            TipoHabitacion tipo;
            
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
   
            return new Habitacion(numero, capacidadMaxima, precioPorNoche, tipo);
        }
    }
    

    private static void recibirHuesped() {
        // Lógica para recibir datos del huésped y agregarlo a la lista de espera
    }

    private static void asignarHabitacion() {
        // Lógica para asignar una habitación al huésped de la lista de espera
    }

    private static void mostrarEstadoHabitaciones() {
        // Lógica para mostrar el estado actual de las habitaciones
    }

    private static void mostrarListaEspera() {
        // Lógica para mostrar la lista de espera de huéspedes
    }
}

class Habitacion {

    public Habitacion(int numero, int capacidadMaxima, double precioPorNoche, TipoHabitacion tipo) {
    }
    // Atributos y métodos de la clase Habitacion
}

class ListaEspera {

    public ListaEspera(int i) {
    }
    // Atributos y métodos de la clase ListaEspera
}

class Cliente {
    // Atributos y métodos de la clase Cliente
}
