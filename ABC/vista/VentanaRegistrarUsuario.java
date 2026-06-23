package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BibliotecaPublica;
import modelo.Empleado;
import modelo.Lector;

public class VentanaRegistrarUsuario {

    private final BibliotecaPublica bib;
    private final Stage owner;

    public VentanaRegistrarUsuario(BibliotecaPublica bib, Stage owner) {
        this.bib = bib;
        this.owner = owner;
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Registrar usuario");

        Label titulo = new Label("Registrar nuevo usuario");
        titulo.getStyleClass().add("titulo-encabezado");

        ToggleGroup grupoTipo = new ToggleGroup();
        RadioButton rbLector = new RadioButton("Lector");
        RadioButton rbEmpleado = new RadioButton("Empleado");
        rbLector.setToggleGroup(grupoTipo);
        rbEmpleado.setToggleGroup(grupoTipo);
        rbLector.setSelected(true);
        HBox filaTipo = new HBox(15, rbLector, rbEmpleado);

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");
        TextField campoApellido = new TextField();
        campoApellido.setPromptText("Apellido");
        TextField campoTelefono = new TextField();
        campoTelefono.setPromptText("Teléfono");
        TextField campoCorreo = new TextField();
        campoCorreo.setPromptText("Correo");

        TextField campoGenero = new TextField();
        campoGenero.setPromptText("Género preferido");
        TextField campoHoras = new TextField();
        campoHoras.setPromptText("Horas de lectura semanal");
        TextField campoVigencia = new TextField();
        campoVigencia.setPromptText("Vigencia credencial (dd/mm/yyyy)");
        VBox panelLector = new VBox(8, campoGenero, campoHoras, campoVigencia);

        TextField campoRol = new TextField();
        campoRol.setPromptText("Rol (ej. Bibliotecario)");
        TextField campoHorario = new TextField();
        campoHorario.setPromptText("Horario de trabajo");
        TextField campoIngreso = new TextField();
        campoIngreso.setPromptText("Fecha de ingreso (dd/mm/yyyy)");
        VBox panelEmpleado = new VBox(8, campoRol, campoHorario, campoIngreso);
        panelEmpleado.setVisible(false);
        panelEmpleado.setManaged(false);

        rbLector.setOnAction(e -> {
            panelLector.setVisible(true);
            panelLector.setManaged(true);
            panelEmpleado.setVisible(false);
            panelEmpleado.setManaged(false);
        });
        rbEmpleado.setOnAction(e -> {
            panelLector.setVisible(false);
            panelLector.setManaged(false);
            panelEmpleado.setVisible(true);
            panelEmpleado.setManaged(true);
        });

        Button btnGuardar = new Button("Guardar usuario");
        btnGuardar.getStyleClass().add("boton-menu");
        btnGuardar.setMaxWidth(Double.MAX_VALUE);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.getStyleClass().add("boton-salir");
        btnCerrar.setMaxWidth(Double.MAX_VALUE);
        btnCerrar.setOnAction(e -> stage.close());

        btnGuardar.setOnAction(e -> {
            String nombre = campoNombre.getText().trim();
            String apellido = campoApellido.getText().trim();
            String telefono = campoTelefono.getText().trim();
            String correo = campoCorreo.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                mostrarAlerta(AlertType.WARNING, "Campos incompletos",
                        "Por favor llena nombre, apellido, teléfono y correo.");
                return;
            }

            if (rbLector.isSelected()) {
                String genero = campoGenero.getText().trim();
                String vigencia = campoVigencia.getText().trim();
                double horas;
                try {
                    horas = campoHoras.getText().trim().isEmpty()
                            ? 0.0 : Double.parseDouble(campoHoras.getText().trim());
                } catch (NumberFormatException ex) {
                    mostrarAlerta(AlertType.WARNING, "Dato inválido",
                            "Las horas de lectura deben ser un número.");
                    return;
                }

                /*
                    double horas = 0.0;
                    if (!campoHoras.getText().trim().isEmpty()) {
                        horas = Double.parseDouble(campoHoras.getText().trim());
                    }
                */
               
                String id = bib.generarIdUsuario("L");
                Lector nuevo = new Lector(id, nombre, apellido, telefono, correo,
                        genero, horas, vigencia);
                bib.registrarLector(nuevo);
                mostrarAlerta(AlertType.INFORMATION, "Lector registrado",
                        "Se registró el lector con ID " + id + ".");
            } else {
                String rol = campoRol.getText().trim();
                String horario = campoHorario.getText().trim();
                String ingreso = campoIngreso.getText().trim();
                String id = bib.generarIdUsuario("E");
                Empleado nuevo = new Empleado(id, nombre, apellido, telefono, correo,
                        rol, horario, ingreso);
                bib.registrarEmpleado(nuevo);
                mostrarAlerta(AlertType.INFORMATION, "Empleado registrado",
                        "Se registró el empleado con ID " + id + ".");
            }

            campoNombre.clear();
            campoApellido.clear();
            campoTelefono.clear();
            campoCorreo.clear();
            campoGenero.clear();
            campoHoras.clear();
            campoVigencia.clear();
            campoRol.clear();
            campoHorario.clear();
            campoIngreso.clear();
        });

        VBox tarjeta = new VBox(10,
                titulo, filaTipo,
                campoNombre, campoApellido, campoTelefono, campoCorreo,
                panelLector, panelEmpleado,
                btnGuardar, btnCerrar);
        tarjeta.getStyleClass().add("tarjeta");
        tarjeta.setPadding(new Insets(20));

        VBox raiz = new VBox(tarjeta);
        raiz.getStyleClass().add("fondo-principal");
        raiz.setPadding(new Insets(20));

        Scene escena = new Scene(raiz, 400, 560);
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
