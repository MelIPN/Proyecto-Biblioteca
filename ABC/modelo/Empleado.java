package modelo;

public class Empleado extends Persona {

    private String rol;              // Ej: "Bibliotecario", "Recepción", "Administrador"
    private String horarioTrabajo;    // Ej: "Lun-Vie 9:00-17:00"
    private String fechaIngreso;

    public Empleado() {
        super();
    }

    public Empleado(String id, String nombre, String apellido,
                     String telefono, String correo,
                     String rol, String horarioTrabajo, String fechaIngreso) {
        super(id, nombre, apellido, telefono, correo);
        this.rol            = rol;
        this.horarioTrabajo = horarioTrabajo;
        this.fechaIngreso   = fechaIngreso;
    }

    public String getRol()                  { return rol; }
    public String getHorarioTrabajo()       { return horarioTrabajo; }
    public String getFechaIngreso()         { return fechaIngreso; }

    public void setRol(String rol)                    { this.rol = rol; }
    public void setHorarioTrabajo(String horario)      { this.horarioTrabajo = horario; }
    public void setFechaIngreso(String fechaIngreso)    { this.fechaIngreso = fechaIngreso; }

    @Override
    public String mostrarDatos() {
        return "Empleado | ID: " + id + " | " + nombre + " " + apellido
             + " | Rol: " + rol
             + " | Horario: " + horarioTrabajo;
    }
}
