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

public class JuegoController {

    @FXML private Button musicButton;
    @FXML private GridPane gridPane;
    private TextField[][] celdas = new TextField[6][6];
    private int[][] solucionFinal;

    @FXML
    public void initialize() {
        Music.getInstance();
        actualizarBotonMusica();

        int[][][] generado = Generador.generarTableroYSolucion();
        int[][] tableroInicial = generado[0];
        solucionFinal = generado[1];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField tf = new TextField();
                tf.setPrefSize(60, 60);
                tf.setStyle("-fx-font-size: 18; -fx-alignment: center;");
                int valor = tableroInicial[i][j];

                if (valor != 0) {
                    tf.setText(String.valueOf(valor));
                    tf.setEditable(false);
                    tf.setStyle(tf.getStyle() + "-fx-background-color: #E0E0E0;");
                }

                final int fila = i;
                final int columna = j;

                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("[1-6]?")) {
                        tf.setText(oldVal);
                        return;
                    }

                    if (!newVal.isEmpty()) {
                        int valorIngresado = Integer.parseInt(newVal);
                        int[][] tableroActual = obtenerTableroDesdeCeldas();

                        if (!Validacion.validarEntrada(tableroActual, fila, columna, valorIngresado)) {
                            tf.setStyle("-fx-border-color: red; -fx-text-fill: red;");
                        } else {
                            tf.setStyle("-fx-border-color: green; -fx-text-fill: green;");
                        }
                    } else {
                        tf.setStyle(""); // Borrar estilos si se borra valor
                    }
                });

                gridPane.add(tf, j, i);
                celdas[i][j] = tf;
            }
        }
    }

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

    @FXML
    private void musica(ActionEvent event) {
        Music.getInstance().toggleMusic();
        actualizarBotonMusica();
    }

    @FXML
    public void pistas(ActionEvent event) {
        boolean dada = Pistas.darPista(celdas, solucionFinal);
        if (!dada) {
            AlertBox.showInfo("Pistas agotadas", "Ya has utilizado todas las pistas posibles.");
        }
    }

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

    @FXML
    private void reiniciarjuego(ActionEvent event) throws IOException {
        boolean confirmado = AlertBox.showConfirmAlertBox(
                "Confirmar nuevo juego",
                "¿Estás seguro de que deseas iniciar un nuevo juego?",
                "Recuerda que se perderá el progreso actual."
        );

        if (confirmado) {
            Pistas.reiniciarPistas();

            int[][][] generado = Generador.generarTableroYSolucion();
            int[][] tableroInicial = generado[0];
            solucionFinal = generado[1];

            gridPane.getChildren().clear();

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    TextField tf = new TextField();
                    tf.setPrefSize(60, 60);
                    tf.setStyle("-fx-font-size: 18; -fx-alignment: center;");
                    int valor = tableroInicial[i][j];

                    if (valor != 0) {
                        tf.setText(String.valueOf(valor));
                        tf.setEditable(false);
                        tf.setStyle(tf.getStyle() + "-fx-background-color: #E0E0E0;");
                    }

                    final int fila = i;
                    final int columna = j;

                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal.matches("[1-6]?")) {
                            tf.setText(oldVal);
                            return;
                        }

                        if (!newVal.isEmpty()) {
                            int valorIngresado = Integer.parseInt(newVal);
                            int[][] tableroActual = obtenerTableroDesdeCeldas();

                            if (!Validacion.validarEntrada(tableroActual, fila, columna, valorIngresado)) {
                                tf.setStyle("-fx-border-color: red; -fx-text-fill: red;");
                            } else {
                                tf.setStyle("-fx-border-color: green; -fx-text-fill: green;");
                            }
                        } else {
                            tf.setStyle("");
                        }
                    });

                    gridPane.add(tf, j, i);
                    celdas[i][j] = tf;
                }
            }

            JuegoView juegoView = JuegoView.getInstance();
            JuegoView.getInstance().close();
            juegoView.show();
        }
    }

    @FXML
    private void iniciartutorial(ActionEvent event) throws IOException {
        TutorialView tutorialView = TutorialView.getInstance();
        MenuView.getInstance().close();
        tutorialView.show();
    }
}
