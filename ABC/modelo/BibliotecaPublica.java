package modelo;


public class BibliotecaPublica extends Biblioteca {

    private String municipio;

    public BibliotecaPublica() {
        super();
    }

    public BibliotecaPublica(String nombre, String numeroBiblioteca, String direccion,
                              String telefono, String horario, int afluenciaEstimada,
                              String municipio) {
        super(nombre, numeroBiblioteca, direccion, telefono, horario, afluenciaEstimada);
        this.municipio = municipio;
    }

    public String getMunicipio()              { return municipio; }
    public void   setMunicipio(String m)      { this.municipio = m; }

    @Override
    public String mostrarInformacion() {
        return "DATOS DE LA BIBLIOTECA\n"
             + "Nombre              : " + nombre            + "\n"
             + "Número              : " + numeroBiblioteca  + "\n"
             + "Municipio           : " + municipio         + "\n"
             + "Dirección           : " + direccion         + "\n"
             + "Teléfono            : " + telefono          + "\n"
             + "Horario             : " + horario           + "\n"
             + "Afluencia estimada  : " + afluenciaEstimada + " personas/día\n"
             + "Total libros        : " + getTotalLibros()  + " libros\n"
             + "Total empleados     : " + empleados.size()  + "\n"
             + "Total lectores      : " + lectores.size();
    }

    @Override
    public String toString() {
        return mostrarInformacion();
    }
}
