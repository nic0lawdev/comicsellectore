package cl.duoc.comiccollectorsystem;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.io.*;
import cl.duoc.comiccollectorsystem.models.Usuario;
import cl.duoc.comiccollectorsystem.models.Comic;
import cl.duoc.comiccollectorsystem.exceptions.ComicNoEncontradoException;
import cl.duoc.comiccollectorsystem.exceptions.ComicSinStockException;
import cl.duoc.comiccollectorsystem.exceptions.UsuarioNoEncontradoException;

public class Menu {

    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private ArrayList<Comic> comics = new ArrayList<>();
    private HashSet<String> generosUnicos = new HashSet<>(); // Para manejar géneros únicos
    private TreeSet<String> autoresOrdenados = new TreeSet<>(); // Para autores ordenados alfabéticamente

    private Scanner scanner;
    private Usuario user;

    public Menu() {
        scanner = new Scanner(System.in);
        cargarComics();
        cargarUsuarios();
    }

    public void mostrar() {
        int opcion = 0;
        do {
            System.out.println("Menu  Comic  Collector");
            System.out.println("1-. Registro de Usuarios");
            System.out.println("2-. Eliminar Usuarios");
            System.out.println("3-. Listar Usuarios");
            System.out.println("4-. Comprar Coleccionables");
            System.out.println("5-. Mostrar Inventario");
            System.out.println("6-. Gestion de Coleccionables");
            System.out.println("7-. Reserva Coleccionables");
            System.out.println("8-. Generar Reporte de Usuarios");
            System.out.println("9-. Mostrar Géneros y Autores");
            System.out.println("0-.  Salir");
            try {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        registrarUsers();
                        break;
                    case 2:
                        eliminarUsers();
                        break;
                    case 3:
                        listarUsers();
                        break;
                    case 4:
                        comprarColeccionables();
                        break;
                    case 5:
                        mostrarInventario();
                        break;
                    case 6:
                        gestionColeccionables();
                        break;
                    case 7:
                        reservarColeccionables();
                        break;
                    case 8:
                        generarReporteUsuarios();
                        break;
                    case 9:
                        mostrarGenerosYAutores();
                        break;
                    case 0:
                        guardarUsuarios();
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opcion Invalida");
                }
            } catch (InputMismatchException e) {
                System.out.println("debe ingresar una opcion valida!");
                scanner.next();
            }

        } while (opcion != 0);

    }

    public void registrarUsers() {
        String rut, name, apellido, direccion, telefono, email;
        System.out.println("\n ====Registro de Usuarios ====");
        
        try {
            do {
                System.out.println("Ingrese su Rut:");
                rut = scanner.nextLine();
                if (rut.isEmpty() || rut.length() < 8) {
                    System.out.println("el rut no puede estar vacio (12.345.678-9)");
                    rut = "";
                } else if (usuarios.containsKey(rut)) {
                    System.out.println("Ya existe un Usuario con ese Rut");
                    rut = "";
                }
            } while (rut.isEmpty());
            
            do {
                System.out.println("Ingrese su Nombre:");
                name = scanner.nextLine();
                if (name.trim().isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                }
            } while (name.trim().isEmpty());
            
            do {
                System.out.println("Ingrese su Apellido:");
                apellido = scanner.nextLine();
                if (apellido.trim().isEmpty()) {
                    System.out.println("El apellido no puede estar vacío.");
                }
            } while (apellido.trim().isEmpty());
            
            do {
                System.out.println("Ingrese su Direccion:");
                direccion = scanner.nextLine();
                if (direccion.trim().isEmpty()) {
                    System.out.println("La dirección no puede estar vacía.");
                }
            } while (direccion.trim().isEmpty());
            
            do {
                System.out.println("Ingrese su Telefono:");
                telefono = scanner.nextLine();
                if (telefono.trim().isEmpty()) {
                    System.out.println("El teléfono no puede estar vacío.");
                }
            } while (telefono.trim().isEmpty());
            
            do {
                System.out.println("Ingrese su Email:");
                email = scanner.nextLine();
                if (email.trim().isEmpty() || !email.contains("@")) {
                    System.out.println("Ingrese un email válido.");
                    email = "";
                }
            } while (email.isEmpty());
            
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine(); // Limpiar buffer
            
            // Crear el usuario
            Usuario nuevoUsuario = new Usuario(rut, name.trim(), apellido.trim(), 
                                             direccion.trim(), telefono.trim(), email.trim());
            
            // Guardarlo en el HashMap
            usuarios.put(rut, nuevoUsuario);
            
            System.out.println("Usuario registrado exitosamente!");
            System.out.println("RUT: " + rut + " - " + name + " " + apellido);
            
        } catch (Exception e) {
            System.err.println("Error durante el registro de usuario: " + e.getMessage());
            System.err.println("Por favor, intente nuevamente.");
        }
    }

    public void listarUsers() {
        System.out.println("\n ====Lista de Usuarios ====");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados!");
        } else {
            for (Usuario usuario : usuarios.values()) {
                System.out.println(usuario.getRut() + " - " + usuario.getName() + " " + usuario.getApellido() + " - " + usuario.getDireccion() + " - " + usuario.getEmail() + " - " + usuario.getTelefono());

            }
        }

    }

    public void eliminarUsers() {
        scanner.nextLine();
        System.out.println("\n ==== Eliminar usuarios ====");
        String rut;
        boolean rutValido = false;
        
        do {
            System.out.println("Ingresa el rut del usuario que deseas eliminar (o presiona Enter para cancelar): ");
            rut = scanner.nextLine().trim();
            
            // Si está vacío, cancelar operación
            if (rut.isEmpty()) {
                System.out.println("Eliminación cancelada. Volviendo al menú principal.");
                return;
            }
            
            // Validar formato básico del RUT
            if (rut.length() < 9 || rut.length() > 13) {
                System.out.println("Formato de RUT inválido. Use el formato: 12.345.678-9");
                continue;
            }
            
            // Verificar si el usuario existe
            if (!usuarios.containsKey(rut)) {
                System.out.println("No se encontró un usuario con ese RUT. Intente nuevamente.");
                continue;
            }
            
            rutValido = true;
            
        } while (!rutValido);
        
        // Si llegamos aquí, el RUT es válido y el usuario existe
        Usuario usuarioAEliminar = usuarios.get(rut);
        System.out.println("Usuario encontrado: " + usuarioAEliminar.getName() + " " + usuarioAEliminar.getApellido() + " - " + usuarioAEliminar.getRut());
        System.out.print("¿Está seguro que desea eliminar este usuario? (S/N): ");
        String eliminar = scanner.nextLine().trim();

        if (eliminar.equalsIgnoreCase("S")) {
            usuarios.remove(rut);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // Método para cargar comics desde archivo CSV
    private void cargarComics() {
        try (BufferedReader br = new BufferedReader(new FileReader("comics.csv"))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar encabezados
                }
                String[] datos = linea.split(",");
                if (datos.length >= 9) {
                    try {
                        Comic comic = new Comic(
                            datos[0], // id
                            datos[1], // titulo
                            datos[2], // autor
                            datos[3], // editorial
                            Integer.parseInt(datos[4]), // anio
                            datos[5], // genero
                            Double.parseDouble(datos[6]), // precio
                            Integer.parseInt(datos[7]), // stock
                            datos[8]  // descripcion
                        );
                        comics.add(comic);
                        
                        // Agregar a las colecciones HashSet y TreeSet
                        generosUnicos.add(comic.getGenero()); // HashSet para géneros únicos
                        autoresOrdenados.add(comic.getAutor()); // TreeSet para autores ordenados
                    } catch (NumberFormatException e) {
                        System.out.println("Error al procesar línea: " + linea);
                    }
                }
            }
            System.out.println("Se cargaron " + comics.size() + " comics desde el archivo.");
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de comics. Se iniciará con inventario vacío.");
        }
    }

    // Método para cargar usuarios desde archivo (simplificado)
    private void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 6) {
                    Usuario usuario = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                    
                    // Cargar compras si existen
                    if (datos.length > 6 && !datos[6].isEmpty()) {
                        String[] compras = datos[6].split(",");
                        for (String compra : compras) {
                            if (!compra.trim().isEmpty()) {
                                usuario.agregarCompra(compra.trim());
                            }
                        }
                    }
                    
                    // Cargar reservas si existen
                    if (datos.length > 7 && !datos[7].isEmpty()) {
                        String[] reservas = datos[7].split(",");
                        for (String reserva : reservas) {
                            if (!reserva.trim().isEmpty()) {
                                usuario.agregarReserva(reserva.trim());
                            }
                        }
                    }
                    
                    usuarios.put(usuario.getRut(), usuario);
                }
            }
            if (!usuarios.isEmpty()) {
                System.out.println("Se cargaron " + usuarios.size() + " usuarios desde el archivo.");
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de usuarios. Se iniciará sin usuarios previos.");
        }
    }

    // Método para guardar usuarios en archivo
    private void guardarUsuarios() {
        try (FileWriter fw = new FileWriter("usuarios.txt")) {
            fw.write("REPORTE DE USUARIOS - Comic Collector System\n");
            fw.write("Generado el: " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n\n");
            
            for (Usuario usuario : usuarios.values()) {
                fw.write(usuario.toReporte());
                fw.write("\n");
            }
            System.out.println("Reporte de usuarios guardado exitosamente en usuarios.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    // Método mejorado para comprar coleccionables con manejo de excepciones
    private void comprarColeccionablesConExcepciones() {
        System.out.println("\n ==== Comprar Coleccionables (Versión con Excepciones) ====");
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Registre un usuario primero.");
            return;
        }
        
        if (comics.isEmpty()) {
            System.out.println("No hay comics disponibles en el inventario.");
            return;
        }
        
        scanner.nextLine(); // Limpiar buffer
        
        try {
            // Solicitar RUT del usuario
            System.out.print("Ingrese el RUT del usuario: ");
            String rut = scanner.nextLine().trim();
            Usuario usuario = buscarUsuario(rut);
            
            // Mostrar comics disponibles
            System.out.println("\n--- Comics Disponibles ---");
            for (Comic comic : comics) {
                if (comic.getStock() > 0) {
                    System.out.println(comic.getId() + " - " + comic.getTitulo() + 
                                     " ($" + comic.getPrecio() + ") - Stock: " + comic.getStock());
                }
            }
            
            // Solicitar ID del comic
            System.out.print("Ingrese el ID del comic a comprar: ");
            String idComic = scanner.nextLine().trim();
            Comic comicSeleccionado = buscarComic(idComic);
            
            // Verificar stock
            verificarStock(comicSeleccionado);
            
            // Confirmar compra
            System.out.println("\nConfirmar compra:");
            System.out.println("Usuario: " + usuario.getName() + " " + usuario.getApellido());
            System.out.println("Comic: " + comicSeleccionado.getTitulo());
            System.out.println("Precio: $" + comicSeleccionado.getPrecio());
            System.out.print("¿Confirmar compra? (S/N): ");
            
            String confirmar = scanner.nextLine().trim();
            if (confirmar.equalsIgnoreCase("S")) {
                // Realizar compra
                comicSeleccionado.setStock(comicSeleccionado.getStock() - 1);
                usuario.agregarCompra(comicSeleccionado.getId());
                usuario.removerReserva(comicSeleccionado.getId());
                
                System.out.println("¡Compra realizada exitosamente!");
            } else {
                System.out.println("Compra cancelada.");
            }
            
        } catch (UsuarioNoEncontradoException | ComicNoEncontradoException | ComicSinStockException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(); // Para debugging
        }
    }
    private void comprarColeccionables() {
        System.out.println("\n ==== Comprar Coleccionables ====");
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Registre un usuario primero.");
            return;
        }
        
        if (comics.isEmpty()) {
            System.out.println("No hay comics disponibles en el inventario.");
            return;
        }
        
        scanner.nextLine(); // Limpiar buffer
        
        // Seleccionar usuario
        String rut;
        Usuario usuario = null;
        do {
            System.out.print("Ingrese el RUT del usuario: ");
            rut = scanner.nextLine().trim();
            usuario = usuarios.get(rut);
            if (usuario == null) {
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            }
        } while (usuario == null);
        
        // Mostrar comics disponibles
        System.out.println("\n--- Comics Disponibles ---");
        for (Comic comic : comics) {
            if (comic.getStock() > 0) {
                System.out.println(comic.getId() + " - " + comic.getTitulo() + 
                                 " ($" + comic.getPrecio() + ") - Stock: " + comic.getStock());
            }
        }
        
        // Seleccionar comic
        String idComic;
        Comic comicSeleccionado = null;
        do {
            System.out.print("Ingrese el ID del comic a comprar: ");
            idComic = scanner.nextLine().trim();
            
            for (Comic comic : comics) {
                if (comic.getId().equals(idComic) && comic.getStock() > 0) {
                    comicSeleccionado = comic;
                    break;
                }
            }
            
            if (comicSeleccionado == null) {
                System.out.println("Comic no encontrado o sin stock. Intente nuevamente.");
            }
        } while (comicSeleccionado == null);
        
        // Confirmar compra
        System.out.println("\nConfirmar compra:");
        System.out.println("Usuario: " + usuario.getName() + " " + usuario.getApellido());
        System.out.println("Comic: " + comicSeleccionado.getTitulo());
        System.out.println("Precio: $" + comicSeleccionado.getPrecio());
        System.out.print("¿Confirmar compra? (S/N): ");
        
        String confirmar = scanner.nextLine().trim();
        if (confirmar.equalsIgnoreCase("S")) {
            // Realizar compra
            comicSeleccionado.setStock(comicSeleccionado.getStock() - 1);
            
            // Agregar compra al usuario
            usuario.agregarCompra(comicSeleccionado.getId());
            
            // Remover de reservas si estaba reservado
            usuario.removerReserva(comicSeleccionado.getId());
            
            System.out.println("¡Compra realizada exitosamente!");
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    // Método para mostrar inventario
    private void mostrarInventario() {
        System.out.println("\n ==== Inventario de Comics ====");
        
        if (comics.isEmpty()) {
            System.out.println("No hay comics en el inventario.");
            return;
        }
        
        System.out.printf("%-3s %-25s %-20s %-15s %-6s %-10s %-6s%n", 
                         "ID", "Título", "Autor", "Editorial", "Año", "Precio", "Stock");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Comic comic : comics) {
            System.out.printf("%-3s %-25s %-20s %-15s %-6d $%-9.0f %-6d%n",
                             comic.getId(),
                             comic.getTitulo().length() > 25 ? comic.getTitulo().substring(0, 22) + "..." : comic.getTitulo(),
                             comic.getAutor().length() > 20 ? comic.getAutor().substring(0, 17) + "..." : comic.getAutor(),
                             comic.getEditorial().length() > 15 ? comic.getEditorial().substring(0, 12) + "..." : comic.getEditorial(),
                             comic.getAnio(),
                             comic.getPrecio(),
                             comic.getStock());
        }
    }

    // Método para gestión de coleccionables
    private void gestionColeccionables() {
        System.out.println("\n ==== Gestión de Coleccionables ====");
        System.out.println("1. Agregar nuevo comic");
        System.out.println("2. Modificar stock");
        System.out.println("3. Modificar precio");
        System.out.println("0. Volver al menú principal");
        
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    agregarNuevoComic();
                    break;
                case 2:
                    modificarStock();
                    break;
                case 3:
                    modificarPrecio();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar una opción válida!");
            scanner.next();
        }
    }

    // Método para reservar coleccionables
    private void reservarColeccionables() {
        System.out.println("\n ==== Reservar Coleccionables ====");
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Registre un usuario primero.");
            return;
        }
        
        if (comics.isEmpty()) {
            System.out.println("No hay comics disponibles para reservar.");
            return;
        }
        
        scanner.nextLine(); // Limpiar buffer
        
        // Seleccionar usuario
        String rut;
        Usuario usuario = null;
        do {
            System.out.print("Ingrese el RUT del usuario: ");
            rut = scanner.nextLine().trim();
            usuario = usuarios.get(rut);
            if (usuario == null) {
                System.out.println("Usuario no encontrado. Intente nuevamente.");
            }
        } while (usuario == null);
        
        // Mostrar comics disponibles
        System.out.println("\n--- Comics Disponibles para Reserva ---");
        for (Comic comic : comics) {
            if (comic.getStock() > 0) {
                System.out.println(comic.getId() + " - " + comic.getTitulo() + 
                                 " ($" + comic.getPrecio() + ") - Stock: " + comic.getStock());
            }
        }
        
        // Seleccionar comic
        String idComic;
        Comic comicSeleccionado = null;
        do {
            System.out.print("Ingrese el ID del comic a reservar: ");
            idComic = scanner.nextLine().trim();
            
            for (Comic comic : comics) {
                if (comic.getId().equals(idComic) && comic.getStock() > 0) {
                    comicSeleccionado = comic;
                    break;
                }
            }
            
            if (comicSeleccionado == null) {
                System.out.println("Comic no encontrado o sin stock. Intente nuevamente.");
            }
        } while (comicSeleccionado == null);
        
        // Verificar si ya está reservado
        if (usuario.tieneReserva(comicSeleccionado.getId())) {
            System.out.println("Este comic ya está reservado por este usuario.");
            return;
        }
        
        // Realizar reserva
        usuario.agregarReserva(comicSeleccionado.getId());
        System.out.println("¡Reserva realizada exitosamente!");
        System.out.println("Comic reservado: " + comicSeleccionado.getTitulo());
    }

    // Métodos auxiliares para gestión
    private void agregarNuevoComic() {
        System.out.println("\n--- Agregar Nuevo Comic ---");
        
        String id, titulo, autor, editorial, genero, descripcion;
        int anio, stock;
        double precio;
        
        System.out.print("ID: ");
        id = scanner.nextLine().trim();
        
        // Verificar que el ID no exista
        for (Comic comic : comics) {
            if (comic.getId().equals(id)) {
                System.out.println("Ya existe un comic con ese ID.");
                return;
            }
        }
        
        System.out.print("Título: ");
        titulo = scanner.nextLine().trim();
        
        System.out.print("Autor: ");
        autor = scanner.nextLine().trim();
        
        System.out.print("Editorial: ");
        editorial = scanner.nextLine().trim();
        
        try {
            System.out.print("Año: ");
            anio = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            System.out.print("Género: ");
            genero = scanner.nextLine().trim();
            
            System.out.print("Precio: ");
            precio = scanner.nextDouble();
            
            System.out.print("Stock: ");
            stock = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            System.out.print("Descripción: ");
            descripcion = scanner.nextLine().trim();
            
            Comic nuevoComic = new Comic(id, titulo, autor, editorial, anio, genero, precio, stock, descripcion);
            comics.add(nuevoComic);
            
            System.out.println("Comic agregado exitosamente!");
            
        } catch (InputMismatchException e) {
            System.out.println("Error en los datos ingresados.");
            scanner.next();
        }
    }

    private void modificarStock() {
        if (comics.isEmpty()) {
            System.out.println("No hay comics en el inventario.");
            return;
        }
        
        System.out.print("Ingrese el ID del comic: ");
        String id = scanner.nextLine().trim();
        
        Comic comic = null;
        for (Comic c : comics) {
            if (c.getId().equals(id)) {
                comic = c;
                break;
            }
        }
        
        if (comic == null) {
            System.out.println("Comic no encontrado.");
            return;
        }
        
        System.out.println("Stock actual: " + comic.getStock());
        try {
            System.out.print("Nuevo stock: ");
            int nuevoStock = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            comic.setStock(nuevoStock);
            System.out.println("Stock actualizado exitosamente!");
            
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.next();
        }
    }

    private void modificarPrecio() {
        if (comics.isEmpty()) {
            System.out.println("No hay comics en el inventario.");
            return;
        }
        
        System.out.print("Ingrese el ID del comic: ");
        String id = scanner.nextLine().trim();
        
        Comic comic = null;
        for (Comic c : comics) {
            if (c.getId().equals(id)) {
                comic = c;
                break;
            }
        }
        
        if (comic == null) {
            System.out.println("Comic no encontrado.");
            return;
        }
        
        System.out.println("Precio actual: $" + comic.getPrecio());
        try {
            System.out.print("Nuevo precio: ");
            double nuevoPrecio = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer
            
            comic.setPrecio(nuevoPrecio);
            System.out.println("Precio actualizado exitosamente!");
            
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.next();
        }
    }

    // Método para buscar un comic por ID with manejo de excepciones
    private Comic buscarComic(String id) throws ComicNoEncontradoException {
        for (Comic comic : comics) {
            if (comic.getId().equals(id)) {
                return comic;
            }
        }
        throw new ComicNoEncontradoException("No se encontró un comic con ID: " + id);
    }

    // Método para buscar un usuario por RUT con manejo de excepciones
    private Usuario buscarUsuario(String rut) throws UsuarioNoEncontradoException {
        Usuario usuario = usuarios.get(rut);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("No se encontró un usuario con RUT: " + rut);
        }
        return usuario;
    }

    // Método para verificar stock de comic con manejo de excepciones
    private void verificarStock(Comic comic) throws ComicSinStockException {
        if (comic.getStock() <= 0) {
            throw new ComicSinStockException("El comic '" + comic.getTitulo() + "' no tiene stock disponible");
        }
    }

    // Método para generar reporte de usuarios por demanda
    private void generarReporteUsuarios() {
        System.out.println("\n ==== Generar Reporte de Usuarios ====");
        
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados para generar reporte.");
            return;
        }
        
        try (FileWriter fw = new FileWriter("reporte_usuarios.txt")) {
            fw.write("=== REPORTE DE USUARIOS - COMIC COLLECTOR SYSTEM ===\n");
            fw.write("Fecha de generación: " + java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            fw.write("Total de usuarios registrados: " + usuarios.size() + "\n\n");
            
            for (Usuario usuario : usuarios.values()) {
                fw.write(usuario.toReporte());
                fw.write("\n");
            }
            
            // Estadísticas adicionales
            fw.write("\n=== ESTADÍSTICAS GENERALES ===\n");
            int totalCompras = 0;
            int totalReservas = 0;
            
            for (Usuario usuario : usuarios.values()) {
                totalCompras += usuario.getCompras().size();
                totalReservas += usuario.getReservas().size();
            }
            
            fw.write("Total de compras realizadas: " + totalCompras + "\n");
            fw.write("Total de reservas activas: " + totalReservas + "\n");
            fw.write("Promedio de compras por usuario: " + String.format("%.2f", (double)totalCompras / usuarios.size()) + "\n");
            fw.write("Promedio de reservas por usuario: " + String.format("%.2f", (double)totalReservas / usuarios.size()) + "\n");
            
            System.out.println("✅ Reporte generado exitosamente en 'reporte_usuarios.txt'");
            System.out.println("📊 Estadísticas:");
            System.out.println("   - Usuarios registrados: " + usuarios.size());
            System.out.println("   - Total compras: " + totalCompras);
            System.out.println("   - Total reservas: " + totalReservas);
            
        } catch (IOException e) {
            System.out.println("❌ Error al generar el reporte: " + e.getMessage());
        }
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    // Método para mostrar géneros únicos (HashSet) y autores ordenados (TreeSet)
    private void mostrarGenerosYAutores() {
        System.out.println("\n ==== Géneros y Autores ====");
        
        try {
            // Mostrar géneros únicos usando HashSet
            System.out.println("\n--- GÉNEROS ÚNICOS (HashSet) ---");
            if (generosUnicos.isEmpty()) {
                System.out.println("No hay géneros disponibles.");
            } else {
                System.out.println("Total de géneros únicos: " + generosUnicos.size());
                int contador = 1;
                for (String genero : generosUnicos) {
                    System.out.println(contador + ". " + genero);
                    contador++;
                }
            }
            
            // Mostrar autores ordenados usando TreeSet
            System.out.println("\n--- AUTORES ORDENADOS ALFABÉTICAMENTE (TreeSet) ---");
            if (autoresOrdenados.isEmpty()) {
                System.out.println("No hay autores disponibles.");
            } else {
                System.out.println("Total de autores: " + autoresOrdenados.size());
                int contador = 1;
                for (String autor : autoresOrdenados) {
                    System.out.println(contador + ". " + autor);
                    contador++;
                }
            }
            
            // Estadísticas adicionales
            System.out.println("\n--- ESTADÍSTICAS ---");
            System.out.println("Comics totales: " + comics.size());
            System.out.println("Géneros únicos: " + generosUnicos.size());
            System.out.println("Autores únicos: " + autoresOrdenados.size());
            
            // Mostrar el género más común
            mostrarGeneroMasComun();
            
        } catch (Exception e) {
            System.err.println("Error al mostrar géneros y autores: " + e.getMessage());
        }
    }

    // Método auxiliar para encontrar el género más común
    private void mostrarGeneroMasComun() {
        try {
            HashMap<String, Integer> conteoGeneros = new HashMap<>();
            
            // Contar ocurrencias de cada género
            for (Comic comic : comics) {
                String genero = comic.getGenero();
                conteoGeneros.put(genero, conteoGeneros.getOrDefault(genero, 0) + 1);
            }
            
            if (!conteoGeneros.isEmpty()) {
                String generoMasComun = "";
                int maxCount = 0;
                
                for (HashMap.Entry<String, Integer> entry : conteoGeneros.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        maxCount = entry.getValue();
                        generoMasComun = entry.getKey();
                    }
                }
                
                System.out.println("Género más común: " + generoMasComun + " (" + maxCount + " comics)");
            }
            
        } catch (Exception e) {
            System.err.println("Error al calcular género más común: " + e.getMessage());
        }
    }

}
