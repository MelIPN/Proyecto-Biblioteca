package vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;
import modelo.Libro;

public class VentanaCatalogo {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaCatalogo(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Catálogo de libros");

        Label titulo = new Label("Catálogo de libros");
        titulo.getStyleClass().add("titulo-encabezado");

        TextField campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por título...");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.getStyleClass().add("boton-menu");

        Button btnMostrarTodos = new Button("Mostrar todos");
        btnMostrarTodos.getStyleClass().add("boton-menu");

        HBox filaBusqueda = new HBox(8, campoBusqueda, btnBuscar, btnMostrarTodos);

        TableView<Libro> tabla = new TableView<>();

        TableColumn<Libro, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Libro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setPrefWidth(160);

        TableColumn<Libro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAutor.setPrefWidth(140);

        TableColumn<Libro, String> colEditorial = new TableColumn<>("Editorial");
        colEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));

        TableColumn<Libro, String> colGenero = new TableColumn<>("Género");
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        TableColumn<Libro, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d ->
                new javafx.beans.property.SimpleStringProperty(d.getValue().obtenerEstado()));
        colEstado.setPrefWidth(160);

        tabla.getColumns().add(colCodigo);
        tabla.getColumns().add(colTitulo);
        tabla.getColumns().add(colAutor);
        tabla.getColumns().add(colEditorial);
        tabla.getColumns().add(colGenero);
        tabla.getColumns().add(colEstado);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        cargarTodos(tabla);

        btnBuscar.setOnAction(e -> {
            String texto = campoBusqueda.getText().trim();
            if (texto.isEmpty()) {
                cargarTodos(tabla);
            } else {
                ObservableList<Libro> resultados =
                        FXCollections.observableArrayList(bib.buscarPorTitulo(texto));
                tabla.setItems(resultados);
            }
        });

        btnMostrarTodos.setOnAction(e -> {
            campoBusqueda.clear();
            cargarTodos(tabla);
        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        VBox encabezado = new VBox(8, titulo, filaBusqueda);
        encabezado.setPadding(new Insets(15, 20, 10, 20));

        BorderPane raiz = new BorderPane();
        raiz.getStyleClass().add("fondo-principal");
        raiz.setTop(encabezado);
        raiz.setCenter(tabla);

        Label lblTotal = new Label("Total de libros registrados: " + bib.getTotalLibros());
        
        VBox pie = new VBox(10, lblTotal, btnCerrar);
        pie.setPadding(new Insets(10, 20, 15, 20));
        raiz.setBottom(pie);

        Scene escena = new Scene(raiz, 700, 450);
        escena.getStylesheets().add(
                getClass().getResource("/modelo/recursos/estilo.css").toExternalForm());

        stage.setScene(escena);
        stage.showAndWait();
    }

    private void cargarTodos(TableView<Libro> tabla) {
        tabla.setItems(FXCollections.observableArrayList(bib.getCatalogo()));
    }
}
