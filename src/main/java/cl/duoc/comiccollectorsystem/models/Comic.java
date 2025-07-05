package cl.duoc.comiccollectorsystem.models;

public class Comic {
    private String id;
    private String titulo;
    private String autor;
    private String editorial;
    private int anio;
    private String genero;
    private double precio;
    private int stock;
    private String descripcion;

    public Comic() {
    }

    public Comic(String id, String titulo, String autor, String editorial, int anio, String genero, double precio, int stock, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.genero = genero;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnio() {
        return anio;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anio=" + anio +
                ", genero='" + genero + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
