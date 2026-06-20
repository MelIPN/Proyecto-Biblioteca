package modelo;

public interface Prestamo {
    void prestar(Lector lector);
    void devolver();
    String obtenerEstado();
}
