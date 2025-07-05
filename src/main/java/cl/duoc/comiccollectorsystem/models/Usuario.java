package cl.duoc.comiccollectorsystem.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String rut;
    private String name;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private List<String> compras; // IDs de comics comprados
    private List<String> reservas; // IDs de comics reservados

    public Usuario() {
        this.compras = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public Usuario(String rut, String name, String apellido, String direccion, String telefono, String email) {
        this.rut = rut;
        this.name = name;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.compras = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public String getName() {
        return name;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getCompras() {
        return compras;
    }

    public List<String> getReservas() {
        return reservas;
    }

    // Setters adicionales
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompras(List<String> compras) {
        this.compras = compras;
    }

    public void setReservas(List<String> reservas) {
        this.reservas = reservas;
    }

    // Métodos para gestionar compras y reservas
    public void agregarCompra(String comicId) {
        if (!compras.contains(comicId)) {
            compras.add(comicId);
        }
    }

    public void agregarReserva(String comicId) {
        if (!reservas.contains(comicId)) {
            reservas.add(comicId);
        }
    }

    public void removerReserva(String comicId) {
        reservas.remove(comicId);
    }

    public boolean tieneReserva(String comicId) {
        return reservas.contains(comicId);
    }

    public boolean tieneCompra(String comicId) {
        return compras.contains(comicId);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "rut='" + rut + '\'' +
                ", name='" + name + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", compras=" + compras.size() +
                ", reservas=" + reservas.size() +
                '}';
    }

    // Método para generar el reporte detallado del usuario para el archivo TXT
    public String toReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("USUARIO: ").append(rut).append("\n");
        reporte.append("Nombre: ").append(name).append(" ").append(apellido).append("\n");
        reporte.append("Email: ").append(email).append("\n");
        reporte.append("Teléfono: ").append(telefono).append("\n");
        reporte.append("Dirección: ").append(direccion).append("\n");
        
        reporte.append("--- COMPRAS ---\n");
        if (compras.isEmpty()) {
            reporte.append("Sin compras realizadas\n");
        } else {
            for (String comicId : compras) {
                reporte.append("Comic ID: ").append(comicId).append("\n");
            }
        }
        
        reporte.append("--- RESERVAS ---\n");
        if (reservas.isEmpty()) {
            reporte.append("Sin reservas realizadas\n");
        } else {
            for (String comicId : reservas) {
                reporte.append("Comic ID: ").append(comicId).append("\n");
            }
        }
        
        reporte.append("========================================\n");
        return reporte.toString();
    }
}

