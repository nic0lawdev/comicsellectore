package cl.duoc.comiccollectorsystem.exceptions;

public class ComicNoEncontradoException extends Exception {
    public ComicNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public ComicNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
