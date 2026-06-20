package modelo;
import java.util.ArrayList;

public class Lector extends Persona {

    private String        preferenciaGenero;
    private double         horasLecturaSemanal;
    private String        vigenciaCredencial;   // fecha de vencimiento dd/mm/yyyy
    private ArrayList<Libro> librosPrestados;

    public Lector() {
        super();
        librosPrestados = new ArrayList<>();
    }

    public Lector(String id, String nombre, String apellido,
                  String telefono, String correo,
                  String preferenciaGenero, double horasLectura, String vigencia) {
        super(id, nombre, apellido, telefono, correo);
        this.preferenciaGenero    = preferenciaGenero;
        this.horasLecturaSemanal  = horasLectura;
        this.vigenciaCredencial   = vigencia;
        this.librosPrestados      = new ArrayList<>();
    }

    public String getPreferenciaGenero()   { return preferenciaGenero; }
    public double getHorasLecturaSemanal() { return horasLecturaSemanal; }
    public String getVigenciaCredencial()  { return vigenciaCredencial; }
    public ArrayList<Libro> getLibrosPrestados() { return librosPrestados; }

    public void setPreferenciaGenero(String g)    { this.preferenciaGenero   = g; }
    public void setHorasLecturaSemanal(double h)  { this.horasLecturaSemanal = h; }
    public void setVigenciaCredencial(String v)   { this.vigenciaCredencial  = v; }

    public void agregarLibroPrestado(Libro l)  { librosPrestados.add(l); }
    public void removerLibroPrestado(Libro l)  { librosPrestados.remove(l); }

    @Override
    public String mostrarDatos() {
        return "Lector | ID: " + id + " | " + nombre + " " + apellido
             + " | Género pref.: " + preferenciaGenero
             + " | Vigencia: " + vigenciaCredencial;
    }
}
