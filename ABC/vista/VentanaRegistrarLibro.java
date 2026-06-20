package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;
import modelo.Libro;

public class VentanaRegistrarLibro {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaRegistrarLibro(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Registrar libro");

        Label titulo = new Label("Registrar nuevo libro");
        titulo.getStyleClass().add("titulo-encabezado");

        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Título");

        TextField campoAutor = new TextField();
        campoAutor.setPromptText("Autor");

        TextField campoEditorial = new TextField();
        campoEditorial.setPromptText("Editorial");

        TextField campoGenero = new TextField();
        campoGenero.setPromptText("Género");

        Button btnGuardar = new Button("Guardar libro");
        btnGuardar.getStyleClass().add("boton-menu");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        btnGuardar.setOnAction(e -> {
            String tit = campoTitulo.getText().trim();
            String aut = campoAutor.getText().trim();
            String edi = campoEditorial.getText().trim();
            String gen = campoGenero.getText().trim();

            if (tit.isEmpty() || aut.isEmpty() || edi.isEmpty() || gen.isEmpty()) {
                mostrarAlerta(AlertType.WARNING, "Campos incompletos",
                        "Por favor llena todos los campos antes de guardar.");
                return;
            }

            String codigo = bib.generarCodigoLibro();
            Libro nuevo = new Libro(codigo, tit, aut, edi, gen);
            bib.agregarLibro(nuevo);

            mostrarAlerta(AlertType.INFORMATION, "Libro registrado",
                    "Se registró el libro con código " + codigo + ".");

            campoTitulo.clear();
            campoAutor.clear();
            campoEditorial.clear();
            campoGenero.clear();
        });

        VBox tarjeta = new VBox(10,
                titulo, campoTitulo, campoAutor, campoEditorial, campoGenero,
                btnGuardar, btnCerrar);
        tarjeta.getStyleClass().add("tarjeta");
        tarjeta.setPadding(new Insets(20));

        VBox raiz = new VBox(tarjeta);
        raiz.getStyleClass().add("fondo-principal");
        raiz.setPadding(new Insets(20));

        Scene escena = new Scene(raiz, 380, 380);
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
