package modelo;

import java.util.ArrayList;


public abstract class Biblioteca {

    protected String nombre;
    protected String numeroBiblioteca;
    protected String direccion;
    protected String telefono;
    protected String horario;
    protected int afluenciaEstimada;

    protected ArrayList<Libro> catalogoLibros;
    protected ArrayList<Empleado> empleados;
    protected ArrayList<Lector> lectores;
    protected ArrayList<RegistroPrestamo> prestamos;

    
    public Biblioteca() {
        this.catalogoLibros = new ArrayList<>();
        this.empleados      = new ArrayList<>();
        this.lectores       = new ArrayList<>();
        this.prestamos      = new ArrayList<>();
    }

    public Biblioteca(String nombre, String numeroBiblioteca, String direccion,
                      String telefono, String horario, int afluenciaEstimada) {
        this.nombre              = nombre;
        this.numeroBiblioteca    = numeroBiblioteca;
        this.direccion           = direccion;
        this.telefono            = telefono;
        this.horario             = horario;
        this.afluenciaEstimada   = afluenciaEstimada;
        this.catalogoLibros      = new ArrayList<>();
        this.empleados           = new ArrayList<>();
        this.lectores            = new ArrayList<>();
        this.prestamos           = new ArrayList<>();
    }

    public String getNombre()             { return nombre; }
    public String getNumeroBiblioteca()   { return numeroBiblioteca; }
    public String getDireccion()          { return direccion; }
    public String getTelefono()           { return telefono; }
    public String getHorario()            { return horario; }
    public int    getAfluenciaEstimada()  { return afluenciaEstimada; }
    public ArrayList<Libro> getCatalogo() { return catalogoLibros; }
    public ArrayList<Empleado> getEmpleados() { return empleados; }
    public ArrayList<Lector> getLectores()    { return lectores; }
    public ArrayList<RegistroPrestamo> getPrestamos() { return prestamos; }

    public void setNombre(String nombre)                       { this.nombre = nombre; }
    public void setNumeroBiblioteca(String numeroBiblioteca)   { this.numeroBiblioteca = numeroBiblioteca; }
    public void setDireccion(String direccion)                 { this.direccion = direccion; }
    public void setTelefono(String telefono)                   { this.telefono = telefono; }
    public void setHorario(String horario)                     { this.horario = horario; }
    public void setAfluenciaEstimada(int afluenciaEstimada)    { this.afluenciaEstimada = afluenciaEstimada; }

    public void agregarLibro(Libro libro) { catalogoLibros.add(libro); }

    public boolean eliminarLibro(String codigoLibro) {
        return catalogoLibros.removeIf(l -> l.getCodigo().equalsIgnoreCase(codigoLibro));
    }

    public Libro buscarPorCodigo(String codigo) {
        for (Libro l : catalogoLibros) {
            if (l.getCodigo().equalsIgnoreCase(codigo)) return l;
        }
        return null;
    }

    public ArrayList<Libro> buscarPorTitulo(String titulo) {
        ArrayList<Libro> resultados = new ArrayList<>();
        for (Libro l : catalogoLibros) {
            if (l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                resultados.add(l);
        }
        return resultados;
    }

    public ArrayList<Libro> obtenerLibrosDisponibles() {
        ArrayList<Libro> disponibles = new ArrayList<>();
        for (Libro l : catalogoLibros) {
            if (l.isDisponible()) disponibles.add(l);
        }
        return disponibles;
    }

    public int getTotalLibros() { return catalogoLibros.size(); }

    /** Genera automáticamente un código único para un libro nuevo. */
    public String generarCodigoLibro() {
        return "L" + String.format("%03d", catalogoLibros.size() + 1);
    }

    public void registrarEmpleado(Empleado e) { empleados.add(e); }
    public void registrarLector(Lector l)     { lectores.add(l); }

    public Lector buscarLectorPorId(String id) {
        for (Lector l : lectores) if (l.getId().equalsIgnoreCase(id)) return l;
        return null;
    }

    public Empleado buscarEmpleadoPorId(String id) {
        for (Empleado e : empleados) if (e.getId().equalsIgnoreCase(id)) return e;
        return null;
    }

    
    public String generarIdUsuario(String prefijo) {
        int total = (prefijo.equals("E") ? empleados.size() : lectores.size()) + 1;
        return prefijo + String.format("%03d", total);
    }

    public void registrarPrestamo(RegistroPrestamo r) { prestamos.add(r); }

    public RegistroPrestamo buscarPrestamoActivoPorLibro(String codigoLibro) {
        for (RegistroPrestamo r : prestamos) {
            if (!r.isDevuelto() && r.getLibro().getCodigo().equalsIgnoreCase(codigoLibro)) {
                return r;
            }
        }
        return null;
    }

    public abstract String mostrarInformacion();
}
