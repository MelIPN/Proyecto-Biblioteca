package vista;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;
import modelo.Libro;
import modelo.RegistroPrestamo;

import java.util.ArrayList;

public class VentanaDevolver {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaDevolver(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Devolver libro");

        Label titulo = new Label("Devolver libro");
        titulo.getStyleClass().add("titulo-encabezado");

        Label lblLibro = new Label("Libro actualmente prestado:");
        lblLibro.getStyleClass().add("texto-normal");

        ComboBox<Libro> comboLibro = new ComboBox<>(
                FXCollections.observableArrayList(librosPrestados()));
        comboLibro.setMaxWidth(Double.MAX_VALUE);

        Label resultado = new Label();
        resultado.getStyleClass().add("texto-normal");
        resultado.setWrapText(true);

        Button btnConfirmar = new Button("Confirmar devolución");
        btnConfirmar.getStyleClass().add("boton-menu");
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        btnConfirmar.setOnAction(e -> {
            Libro libro = comboLibro.getValue();
            if (libro == null) {
                mostrarAlerta(AlertType.WARNING, "Selecciona un libro",
                        "Elige el libro que se va a devolver.");
                return;
            }

            RegistroPrestamo registro = bib.buscarPrestamoActivoPorLibro(libro.getCodigo());
            if (registro == null) {
                mostrarAlerta(AlertType.ERROR, "Sin préstamo activo",
                        "No se encontró un préstamo activo para ese libro.");
                return;
            }

            double multa = registro.registrarDevolucion();
            if (libro.getLectorActual() != null) {
                libro.getLectorActual().removerLibroPrestado(libro);
            }
            libro.devolver();

            String mensaje = "Libro devuelto: " + libro.getTitulo() + "\n"
                    + (multa > 0
                       ? "Multa por retraso: $" + String.format("%.2f", multa)
                       : "Sin retraso, no se generó multa.");
            resultado.setText(mensaje);
            mostrarAlerta(AlertType.INFORMATION, "Devolución registrada", mensaje);

            comboLibro.setItems(FXCollections.observableArrayList(librosPrestados()));
            comboLibro.setValue(null);
        });

        VBox tarjeta = new VBox(10,
                titulo, lblLibro, comboLibro, btnConfirmar, resultado, btnCerrar);
        tarjeta.getStyleClass().add("tarjeta");
        tarjeta.setPadding(new Insets(20));

        VBox raiz = new VBox(tarjeta);
        raiz.getStyleClass().add("fondo-principal");
        raiz.setPadding(new Insets(20));

        Scene escena = new Scene(raiz, 400, 380);
        escena.getStylesheets().add(
                getClass().getResource("/modelo/recursos/estilo.css").toExternalForm());

        stage.setScene(escena);
        stage.showAndWait();
    }

    private ArrayList<Libro> librosPrestados() {
        ArrayList<Libro> prestados = new ArrayList<>();
        for (Libro l : bib.getCatalogo()) {
            if (!l.isDisponible()) {
                prestados.add(l);
            }
        }
        return prestados;
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
