package modelo;


public class Libro implements Prestamo {

    private String  codigo;       
    private String  titulo;
    private String  autor;
    private String  editorial;
    private String  genero;
    private boolean disponible;

    private Lector  lectorActual;

    private Empleado empleadoGestor;

    public Libro() {
        this.disponible   = true;
        this.lectorActual = null;
    }

    public Libro(String codigo, String titulo, String autor,
                 String editorial, String genero) {
        this.codigo       = codigo;
        this.titulo       = titulo;
        this.autor        = autor;
        this.editorial    = editorial;
        this.genero       = genero;
        this.disponible   = true;
        this.lectorActual = null;
    }

    // Getters
    public String   getCodigo()         { return codigo; }
    public String   getTitulo()         { return titulo; }
    public String   getAutor()          { return autor; }
    public String   getEditorial()      { return editorial; }
    public String   getGenero()         { return genero; }
    public boolean  isDisponible()      { return disponible; }
    public Lector   getLectorActual()   { return lectorActual; }
    public Empleado getEmpleadoGestor() { return empleadoGestor; }

    // Setters
    public void setCodigo(String codigo)            { this.codigo         = codigo; }
    public void setTitulo(String titulo)            { this.titulo         = titulo; }
    public void setAutor(String autor)              { this.autor          = autor; }
    public void setEditorial(String editorial)      { this.editorial      = editorial; }
    public void setGenero(String genero)            { this.genero         = genero; }
    public void setDisponible(boolean disponible)   { this.disponible     = disponible; }
    public void setLectorActual(Lector lector)      { this.lectorActual   = lector; }
    public void setEmpleadoGestor(Empleado e)       { this.empleadoGestor = e; }


    @Override
    public void prestar(Lector lector) {
        if (!disponible) {
            throw new IllegalStateException("El libro '" + titulo + "' no está disponible.");
        }
        this.disponible   = false;
        this.lectorActual = lector;
    }

    /**Sobrecarga*/
    public void prestar(Lector lector, Empleado empleado) {
        prestar(lector);
        this.empleadoGestor = empleado;
    }

    @Override
    public void devolver() {
        this.disponible   = true;
        this.lectorActual = null;
    }

    @Override
    public String obtenerEstado() {
        if (disponible) return "Disponible";
        String nombre = (lectorActual != null) ? lectorActual.getNombre() : "desconocido";
        return "Prestado a: " + nombre;
    }

    /* 
        String nombre = "desconocido"
        if (lectorActual != null) {
            nombre = lectorActual.getNombre();
        }
        return "Prestado a: " + nombre;
    */

    @Override
    public String toString() {
        return "[" + codigo + "] " + titulo + " — " + autor
             + " (" + genero + ") | " + obtenerEstado();
    }
}
