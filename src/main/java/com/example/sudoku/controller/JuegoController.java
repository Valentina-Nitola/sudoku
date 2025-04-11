package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

public class JuegoController {

    private Sudoku juego;

    @FXML
    private GridPane tableroGrid; // debe estar en tu FXML

    @FXML
    public void initialize() {
        juego = new Sudoku();  // 🔗 Aquí conectas el modelo

        // Mostrarlo en pantalla (opcional inicial)
        dibujarTablero();
    }

    private void dibujarTablero() {
        tableroGrid.getChildren().clear();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextField celda = new TextField();
                celda.setPrefWidth(50);
                celda.setPrefHeight(50);

                int valor = juego.getCelda(fila, columna);
                if (valor != 0) {
                    celda.setText(String.valueOf(valor));
                }

                int finalFila = fila;
                int finalColumna = columna;

                celda.setOnAction(e -> {
                    String input = celda.getText();
                    try {
                        int nuevoValor = Integer.parseInt(input);
                        juego.setCelda(finalFila, finalColumna, nuevoValor);
                    } catch (NumberFormatException ex) {
                        celda.setText(""); // borra si no es número
                    }
                });

                tableroGrid.add(celda, columna, fila);
            }
        }
    }
}
