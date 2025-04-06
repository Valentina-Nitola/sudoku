package com.example.sudoku.controller;

import com.example.sudoku.view.JuegoView;
import com.example.sudoku.view.TutorialView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class TutorialController {
    /**
     * Abre la vista del juego principal al hacer clic en el botón correspondiente.
     *
     * @param event Evento generado al hacer clic en el botón "Iniciar juego".
     * @throws IOException Si ocurre un error al cargar la vista del juego.
     */
    @FXML
    private void iniciarjuego(ActionEvent event) throws IOException {
        System.out.println("Iniciar juego");
        JuegoView juegoView = JuegoView.getInstance();
        TutorialView.getInstance().close();
        juegoView.show();
    }
}
