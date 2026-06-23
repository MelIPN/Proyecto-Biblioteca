package vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;

public class MenuPrincipal {

    private BibliotecaPublica bib;

    public MenuPrincipal(BibliotecaPublica bib) {
        this.bib = bib;
    }

    public void mostrar(Stage stage) {

        Label titulo = new Label(" " + bib.getNombre());
        titulo.getStyleClass().add("titulo-encabezado");

        Label subtitulo = new Label("Selecciona una opción");
        subtitulo.getStyleClass().add("subtitulo");

        VBox encabezado = new VBox(4, titulo, subtitulo);
        encabezado.getStyleClass().add("encabezado");
        encabezado.setAlignment(Pos.CENTER);

        Button btnCatalogo  = crearBoton("Mostrar catálogo de libros");
        Button btnUsuarios  = crearBoton("Registrar usuarios");
        Button btnLibros    = crearBoton("Registrar libros");
        Button btnPrestamo  = crearBoton("Realizar préstamo");
        Button btnDevolver  = crearBoton("Devolver libro");
        Button btnDatos     = crearBoton("Datos de la biblioteca");

        btnCatalogo.setOnAction(e -> new VentanaCatalogo(bib, stage).mostrar());
        btnUsuarios.setOnAction(e -> new VentanaRegistrarUsuario(bib, stage).mostrar());
        btnLibros.setOnAction(e -> new VentanaRegistrarLibro(bib, stage).mostrar());
        btnPrestamo.setOnAction(e -> new VentanaPrestamo(bib, stage).mostrar());
        btnDevolver.setOnAction(e -> new VentanaDevolver(bib, stage).mostrar());
        btnDatos.setOnAction(e -> new VentanaDatos(bib, stage).mostrar());

        Button btnSalir = new Button("Salir");
        btnSalir.getStyleClass().add("boton-salir");
        btnSalir.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setOnAction(e -> stage.close());

        VBox contenedorBotones = new VBox(10,
                btnCatalogo, btnUsuarios, btnLibros,
                btnPrestamo, btnDevolver, btnDatos, btnSalir);
        contenedorBotones.setPadding(new Insets(20));

        VBox raiz = new VBox(encabezado, contenedorBotones);
        raiz.getStyleClass().add("fondo-principal");

        Scene escena = new Scene(raiz, 400, 480);
        escena.getStylesheets().add(
                getClass().getResource("/modelo/recursos/estilo.css").toExternalForm());

        stage.setTitle("Biblioteca - Menú Principal");
        stage.setScene(escena);
        stage.show();
    }

    private Button crearBoton(String texto) {
        Button boton = new Button(texto);
        boton.getStyleClass().add("boton-menu");
        boton.setMaxWidth(Double.MAX_VALUE);
        return boton;
    }
}