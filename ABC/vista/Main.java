package vista;

import javafx.application.Application;
import modelo.*;

public class Main extends Application {

    @Override
    public void start(javafx.stage.Stage stage) {

        BibliotecaPublica bib = new BibliotecaPublica(
            "Biblioteca Central",
            "BCN-001",
            "Av. Juárez 100, Centro",
            "55-1234-5678",
            "Lun-Vie 8:00-20:00 / Sáb 9:00-15:00",
            350,
            "Benito Juárez"
        );

        bib.agregarLibro(new Libro("L001", "Cien años de soledad",
                "Gabriel García Márquez", "Sudamericana", "Romance"));
        bib.agregarLibro(new Libro("L002", "El principito",
                "Antoine de Saint-Exupéry", "Reynal & Hitchcock", "Infantil"));
        bib.agregarLibro(new Libro("L003", "1984",
                "George Orwell", "Secker & Warburg", "Ciencia ficción"));
        bib.agregarLibro(new Libro("L004", "Dune",
                "Frank Herbert", "Chilton Books", "Ciencia ficción"));
        bib.agregarLibro(new Libro("L005", "Harry Potter y la piedra filosofal",
                "J.K. Rowling", "Bloomsbury", "Fantasía"));

        bib.registrarEmpleado(new Empleado("E001", "Mariana", "López",
                "55-0000-0001", "mlopez@biblioteca.mx",
                "Bibliotecaria", "Lun-Vie 9:00-17:00", "01/03/2023"));
        bib.registrarLector(new Lector("L001", "Carlos", "Hernández",
                "55-0000-0002", "carlos.h@correo.com",
                "Ciencia ficción", 5.0, "31/12/2026"));

        MenuPrincipal menu = new MenuPrincipal(bib);
        menu.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}