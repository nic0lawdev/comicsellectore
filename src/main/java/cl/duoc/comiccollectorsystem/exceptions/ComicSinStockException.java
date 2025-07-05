package cl.duoc.comiccollectorsystem.exceptions;

public class ComicSinStockException extends Exception {
    public ComicSinStockException(String mensaje) {
        super(mensaje);
    }
    
    public ComicSinStockException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
