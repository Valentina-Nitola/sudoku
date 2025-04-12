package com.example.sudoku.controller;

import com.example.sudoku.model.Generador;
import com.example.sudoku.model.Music;
import com.example.sudoku.model.Pistas;
import com.example.sudoku.model.Validacion;
import com.example.sudoku.util.AlertBox;
import com.example.sudoku.view.JuegoView;
import com.example.sudoku.view.MenuView;
import com.example.sudoku.view.TutorialView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

/**
 * Clase controladora que gestiona la lógica y los eventos del juego Sudoku.
 * Controla la interacción entre la vista del tablero de juego y los modelos relacionados con la validación, pistas, y música.
 * Permite realizar las operaciones de validación de tablero, reinicio del juego y dar pistas.
 *
 * @author Tu Nombre
 * @version 1.0.0
 */
public class JuegoController {

    /**
     * Botón para activar o desactivar la música del juego.
     */
    @FXML private Button musicButton;

    /**
     * GridPane donde se muestra el tablero de juego del Sudoku.
     */
    @FXML private GridPane gridPane;

    /**
     * Label que muestra la cantidad de números '6' ingresados en el tablero.
     */
    @FXML private Label numero6;

    /**
     * Matriz de celdas que contiene las referencias a los TextField donde los jugadores ingresan sus respuestas.
     */
    private TextField[][] celdas = new TextField[6][6];

    /**
     * Matriz que contiene la solución final del Sudoku, la cual se utiliza para validar las respuestas del jugador.
     */
    private int[][] solucionFinal;

    /**
     * Método que se ejecuta al cargar la vista del juego.
     * Inicializa el estado del botón de música, genera un tablero con solución y configura las celdas del tablero.
     */
    @FXML
    public void initialize() {
        Music.getInstance();  // Inicializa el estado de la música.
        actualizarBotonMusica();  // Actualiza la imagen del botón de música.

        // Genera el tablero con solución.
        int[][][] generado = Generador.generarTableroYSolucion();
        int[][] tableroInicial = generado[0];
        solucionFinal = generado[1];

        // Configura las dos primeras filas con los valores de la solución.
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                tableroInicial[i][j] = solucionFinal[i][j];  // Usa los valores de la solución para las dos primeras filas.
            }
        }

        // Configura las celdas con los valores generados.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField tf = new TextField();
                tf.setPrefSize(60, 60);
                tf.setStyle("-fx-font-size: 18; -fx-alignment: center;");
                int valor = tableroInicial[i][j];

                // Si la celda tiene un valor predefinido (de las dos primeras filas), mostrarlo y no permitir edición.
                if (valor != 0) {
                    tf.setText(String.valueOf(valor));
                    tf.setEditable(false);
                    tf.setStyle(tf.getStyle() + "-fx-background-color: #E0E0E0;");
                } else {
                    // Si la celda está vacía, permitir la edición.
                    tf.setEditable(true);
                }

                final int fila = i;
                final int columna = j;

                // Listener para cambios de texto en las celdas.
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("[1-6]?")) {
                        tf.setText(oldVal);
                        return;
                    }

                    if (!newVal.isEmpty()) {
                        int valorIngresado = Integer.parseInt(newVal);
                        int[][] tableroActual = obtenerTableroDesdeCeldas();

                        // Validar si la entrada es correcta.
                        if (!Validacion.validarEntrada(tableroActual, fila, columna, valorIngresado)) {
                            tf.setStyle("-fx-border-color: red; -fx-text-fill: red;");
                        } else {
                            tf.setStyle("-fx-border-color: green; -fx-text-fill: green;");
                        }
                    } else {
                        tf.setStyle(""); // Limpiar estilos si el valor es borrado.
                    }

                    // Actualizar el contador de números 6 al cambiar.
                    actualizarNumero6();
                });

                gridPane.add(tf, j, i);  // Añadir la celda al gridPane.
                celdas[i][j] = tf;  // Guardar la referencia a la celda.
            }
        }

        // Actualizar el contador de números 6 al iniciar.
        actualizarNumero6();
    }

    /**
     * Obtiene el tablero actual desde las celdas del juego, considerando los valores ingresados por el jugador.
     *
     * @return Un arreglo bidimensional de enteros representando el estado actual del tablero.
     */
    private int[][] obtenerTableroDesdeCeldas() {
        int[][] tablero = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String texto = celdas[i][j].getText();
                tablero[i][j] = texto.matches("[1-6]") ? Integer.parseInt(texto) : 0;
            }
        }
        return tablero;
    }

    /**
     * Actualiza el estado del botón de música, alternando entre activado y desactivado.
     * Muestra el ícono correspondiente en el botón según el estado de la música.
     */
    private void actualizarBotonMusica() {
        boolean isMusicOn = Music.getInstance().isMusicOn();
        String imgPath = isMusicOn
                ? "/com/example/sudoku/img/onBTN.png"
                : "/com/example/sudoku/img/offBTN.png";

        URL imgURL = getClass().getResource(imgPath);
        if (imgURL != null) {
            Image img = new Image(imgURL.toString());
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);
            musicButton.setGraphic(imageView);
        }
    }

    /**
     * Alterna entre activar y desactivar la música al hacer clic en el botón de música.
     * Actualiza el ícono del botón para reflejar el cambio de estado.
     *
     * @param event Evento generado al hacer clic en el botón.
     */
    @FXML
    private void musica(ActionEvent event) {
        Music.getInstance().toggleMusic();
        actualizarBotonMusica();
    }

    /**
     * Método que proporciona una pista al jugador, ayudando a completar una celda del tablero.
     * Muestra un mensaje si no hay más pistas disponibles.
     *
     * @param event Evento generado al hacer clic en el botón de pistas.
     */
    @FXML
    public void pistas(ActionEvent event) {
        boolean dada = Pistas.darPista(celdas, solucionFinal);
        if (!dada) {
            AlertBox.showInfo("Pistas agotadas", "Ya has utilizado todas las pistas posibles.");
        }
    }

    /**
     * Valida si el tablero ha sido completado correctamente.
     * Si es correcto, muestra un mensaje de éxito. Si hay errores, marca las celdas erróneas.
     *
     * @param event Evento generado al hacer clic en el botón de validar tablero.
     */
    @FXML
    public void validarTablero(ActionEvent event) {
        boolean esCorrecto = true;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String texto = celdas[i][j].getText();
                int valor = texto.isEmpty() ? 0 : Integer.parseInt(texto);

                if (valor != solucionFinal[i][j]) {
                    esCorrecto = false;
                    celdas[i][j].setStyle("-fx-border-color: red;");
                } else if (celdas[i][j].isEditable()) {
                    celdas[i][j].setStyle("");
                }
            }
        }

        if (esCorrecto) {
            AlertBox.showInfo("¡Felicidades!", "Has completado correctamente el Sudoku.");
        } else {
            AlertBox.showError("Error", "El tablero tiene errores o está incompleto.");
        }
    }

    /**
     * Reinicia el juego, generando un nuevo tablero y restableciendo las celdas.
     * Muestra un cuadro de confirmación antes de reiniciar.
     *
     * @param event Evento generado al hacer clic en el botón de reiniciar juego.
     * @throws IOException Si ocurre un error al cargar la vista del juego.
     */
    @FXML
    private void reiniciarjuego(ActionEvent event) throws IOException {
        boolean confirmado = AlertBox.showConfirmAlertBox(
                "Confirmar nuevo juego",
                "¿Estás seguro de que deseas iniciar un nuevo juego?",
                "Recuerda que se perderá el progreso actual."
        );

        if (confirmado) {
            Pistas.reiniciarPistas();

            // Generar un nuevo tablero con solución.
            int[][][] generado = Generador.generarTableroYSolucion();
            int[][] tableroInicial = generado[0];
            solucionFinal = generado[1];

            // Configurar las dos primeras filas con los valores de la solución.
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    tableroInicial[i][j] = solucionFinal[i][j];
                }
            }

            gridPane.getChildren().clear();  // Limpiar el GridPane.
            // Regenerar las celdas en el tablero con los nuevos valores.
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    TextField tf = new TextField();
                    tf.setPrefSize(60, 60);
                    tf.setStyle("-fx-font-size: 18; -fx-alignment: center;");
                    int valor = tableroInicial[i][j];

                    // Si la celda tiene un valor predefinido, mostrarlo y no permitir edición.
                    if (valor != 0) {
                        tf.setText(String.valueOf(valor));
                        tf.setEditable(false);
                        tf.setStyle(tf.getStyle() + "-fx-background-color: #E0E0E0;");
                    } else {
                        tf.setEditable(true);  // Permitir la edición si la celda está vacía.
                    }

                    final int fila = i;
                    final int columna = j;

                    // Listener para el cambio de texto en las celdas.
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal.matches("[1-6]?")) {
                            tf.setText(oldVal);
                            return;
                        }

                        if (!newVal.isEmpty()) {
                            int valorIngresado = Integer.parseInt(newVal);
                            int[][] tableroActual = obtenerTableroDesdeCeldas();

                            // Validar si la entrada es correcta.
                            if (!Validacion.validarEntrada(tableroActual, fila, columna, valorIngresado)) {
                                tf.setStyle("-fx-border-color: red; -fx-text-fill: red;");
                            } else {
                                tf.setStyle("-fx-border-color: green; -fx-text-fill: green;");
                            }
                        } else {
                            tf.setStyle(""); // Limpiar estilos si el valor es borrado.
                        }

                        // Actualizar la cantidad de números 6 al cambiar.
                        actualizarNumero6();
                    });

                    gridPane.add(tf, j, i);  // Añadir la celda al gridPane.
                    celdas[i][j] = tf;  // Guardar la referencia a la celda.
                }
            }

            // Actualizar el contador de números 6 después de generar el nuevo tablero.
            actualizarNumero6();

            // Cerrar la vista actual y abrir la nueva vista del juego.
            JuegoView juegoView = JuegoView.getInstance();
            JuegoView.getInstance().close();
            juegoView.show();
        }
    }

    /**
     * Inicia el tutorial del juego.
     *
     * @param event Evento generado al hacer clic en el botón de tutorial.
     * @throws IOException Si ocurre un error al cargar la vista del tutorial.
     */
    @FXML
    private void iniciartutorial(ActionEvent event) throws IOException {
        TutorialView tutorialView = TutorialView.getInstance();
        MenuView.getInstance().close();
        tutorialView.show();
    }

    /**
     * Actualiza el contador de números '6' en el tablero y lo muestra en el label correspondiente.
     */
    private void actualizarNumero6() {
        int[][] tableroActual = obtenerTableroDesdeCeldas();
        int cantidad6 = Generador.contarNumeros6(tableroActual);  // Método en el modelo Generador.
        numero6.setText("Números 6: " + cantidad6);  // Actualiza el texto del Label.
    }
}
