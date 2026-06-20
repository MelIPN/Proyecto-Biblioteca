package vista;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;
import modelo.Empleado;
import modelo.Lector;
import modelo.Libro;
import modelo.RegistroPrestamo;

public class VentanaPrestamo {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaPrestamo(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Realizar préstamo");

        Label titulo = new Label("Realizar préstamo");
        titulo.getStyleClass().add("titulo-encabezado");

        Label lblLibro = new Label("Libro disponible:");
        lblLibro.getStyleClass().add("texto-normal");
        ComboBox<Libro> comboLibro = new ComboBox<>(
                FXCollections.observableArrayList(bib.obtenerLibrosDisponibles()));
        comboLibro.setMaxWidth(Double.MAX_VALUE);

        Label lblLector = new Label("Lector:");
        lblLector.getStyleClass().add("texto-normal");
        ComboBox<Lector> comboLector = new ComboBox<>(
                FXCollections.observableArrayList(bib.getLectores()));
        comboLector.setMaxWidth(Double.MAX_VALUE);

        Label lblEmpleado = new Label("Atendido por (empleado):");
        lblEmpleado.getStyleClass().add("texto-normal");
        ComboBox<Empleado> comboEmpleado = new ComboBox<>(
                FXCollections.observableArrayList(bib.getEmpleados()));
        comboEmpleado.setMaxWidth(Double.MAX_VALUE);

        TextField campoDomicilio = new TextField();
        campoDomicilio.setPromptText("Domicilio de entrega");

        TextField campoDias = new TextField();
        campoDias.setPromptText("Días solicitados (ej. 7)");

        Button btnConfirmar = new Button("Confirmar préstamo");
        btnConfirmar.getStyleClass().add("boton-menu");
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        btnConfirmar.setOnAction(e -> {
            Libro libro = comboLibro.getValue();
            Lector lector = comboLector.getValue();
            Empleado empleado = comboEmpleado.getValue();
            String domicilio = campoDomicilio.getText().trim();
            String diasTexto = campoDias.getText().trim();

            if (libro == null || lector == null || domicilio.isEmpty() || diasTexto.isEmpty()) {
                mostrarAlerta(AlertType.WARNING, "Campos incompletos",
                        "Selecciona un libro, un lector y completa domicilio y días.");
                return;
            }

            int dias;
            try {
                dias = Integer.parseInt(diasTexto);
            } catch (NumberFormatException ex) {
                mostrarAlerta(AlertType.WARNING, "Dato inválido",
                        "Los días solicitados deben ser un número entero.");
                return;
            }

            try {
                libro.prestar(lector, empleado);
            } catch (IllegalStateException ex) {
                mostrarAlerta(AlertType.ERROR, "No disponible", ex.getMessage());
                return;
            }

            RegistroPrestamo registro = new RegistroPrestamo(
                    libro, lector, empleado, domicilio, dias);
            bib.registrarPrestamo(registro);
            lector.agregarLibroPrestado(libro);

            mostrarAlerta(AlertType.INFORMATION, "Préstamo registrado",
                    registro.generarRecibo());

            comboLibro.setItems(FXCollections.observableArrayList(bib.obtenerLibrosDisponibles()));
            comboLibro.setValue(null);
            comboLector.setValue(null);
            comboEmpleado.setValue(null);
            campoDomicilio.clear();
            campoDias.clear();
        });

        VBox tarjeta = new VBox(8,
                titulo,
                lblLibro, comboLibro,
                lblLector, comboLector,
                lblEmpleado, comboEmpleado,
                campoDomicilio, campoDias,
                btnConfirmar, btnCerrar);
        tarjeta.getStyleClass().add("tarjeta");
        tarjeta.setPadding(new Insets(20));

        VBox raiz = new VBox(tarjeta);
        raiz.getStyleClass().add("fondo-principal");
        raiz.setPadding(new Insets(20));

        Scene escena = new Scene(raiz, 420, 560);
        escena.getStylesheets().add(
                getClass().getResource("/modelo/recursos/estilo.css").toExternalForm());

        stage.setScene(escena);
        stage.showAndWait();
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
