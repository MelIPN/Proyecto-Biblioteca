package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class RegistroPrestamo {

    private static int contadorPrestamos = 1000;
    public static final double CUOTA_POR_DIA_RETRASO = 5.0; 

    private final int numeroPrestamo;
    private final Libro libro;
    private final Lector lector;
    private final Empleado empleadoGestor;
    private final String domicilioEntrega;
    private final int diasSolicitados;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private boolean devuelto;

    public RegistroPrestamo(Libro libro, Lector lector, Empleado empleadoGestor,
                             String domicilioEntrega, int diasSolicitados) {
        this.numeroPrestamo          = ++contadorPrestamos;
        this.libro                   = libro;
        this.lector                  = lector;
        this.empleadoGestor          = empleadoGestor;
        this.domicilioEntrega        = domicilioEntrega;
        this.diasSolicitados         = diasSolicitados;
        this.fechaPrestamo           = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(diasSolicitados);
        this.devuelto                = false;
    }

    public int getNumeroPrestamo()              { return numeroPrestamo; }
    public Libro getLibro()                     { return libro; }
    public Lector getLector()                   { return lector; }
    public Empleado getEmpleadoGestor()         { return empleadoGestor; }
    public String getDomicilioEntrega()         { return domicilioEntrega; }
    public int getDiasSolicitados()             { return diasSolicitados; }
    public LocalDate getFechaPrestamo()         { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public LocalDate getFechaDevolucionReal()   { return fechaDevolucionReal; }
    public boolean isDevuelto()                 { return devuelto; }

    
    public double registrarDevolucion() {
        this.fechaDevolucionReal = LocalDate.now();
        this.devuelto = true;
        return calcularCuotaDemora();
    }

    public double calcularCuotaDemora() {
        LocalDate referencia = (fechaDevolucionReal != null) ? fechaDevolucionReal : LocalDate.now();
        long diasRetraso = ChronoUnit.DAYS.between(fechaDevolucionEsperada, referencia);
        if (diasRetraso <= 0) return 0.0;
        return diasRetraso * CUOTA_POR_DIA_RETRASO;
    }

    public String generarRecibo() {
        StringBuilder sb = new StringBuilder();
        sb.append("RECIBO DE PRÉSTAMO\n");
        sb.append("N° de préstamo      : ").append(numeroPrestamo).append("\n");
        sb.append("Libro                : ").append(libro.getTitulo()).append(" (").append(libro.getCodigo()).append(")\n");
        sb.append("Lector               : ").append(lector.getNombre()).append(" ").append(lector.getApellido()).append("\n");
        sb.append("Domicilio de entrega : ").append(domicilioEntrega).append("\n");
        sb.append("Atendido por         : ").append(empleadoGestor != null ? empleadoGestor.getNombre() + " " + empleadoGestor.getApellido() : "N/A").append("\n");
        sb.append("Días solicitados     : ").append(diasSolicitados).append("\n");
        sb.append("Fecha de préstamo    : ").append(fechaPrestamo).append("\n");
        sb.append("Fecha límite         : ").append(fechaDevolucionEsperada).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Préstamo #" + numeroPrestamo + " — " + libro.getTitulo()
             + " → " + lector.getNombre() + " " + lector.getApellido()
             + (devuelto ? " (Devuelto)" : " (Activo, vence " + fechaDevolucionEsperada + ")");
    }
}
