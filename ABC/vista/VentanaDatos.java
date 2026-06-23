package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;

public class VentanaDatos {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaDatos(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Datos de la biblioteca");

        Label titulo = new Label("Datos de la biblioteca");
        titulo.getStyleClass().add("titulo-encabezado");

        TextArea areaDatos = new TextArea(bib.mostrarInformacion());
        areaDatos.setEditable(false);
        areaDatos.setWrapText(true);
        areaDatos.setPrefRowCount(12);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        VBox tarjeta = new VBox(10, titulo, areaDatos, btnCerrar);
        tarjeta.getStyleClass().add("tarjeta");
        tarjeta.setPadding(new Insets(20));

        VBox raiz = new VBox(tarjeta);
        raiz.getStyleClass().add("fondo-principal");
        raiz.setPadding(new Insets(20));

        Scene escena = new Scene(raiz, 420, 420);
        escena.getStylesheets().add(getClass().getResource("/modelo/recursos/estilo.css").toExternalForm());

        stage.setScene(escena);
        stage.showAndWait();
    }
}

