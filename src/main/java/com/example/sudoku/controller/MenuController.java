package com.example.sudoku.controller;

import com.example.sudoku.model.Music;
import com.example.sudoku.view.TutorialView;
import com.example.sudoku.view.JuegoView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.io.IOException;

public class MenuController {

    @FXML
    private Button musicButton;
    private boolean isMusicOn = true;

    @FXML
    public void initialize() {
        Music.getInstance(); // Inicia la música si no está iniciada
        actualizarBotonMusica();
    }

    @FXML
    private void musica(ActionEvent event) {
        Music.getInstance().toggleMusic();
        isMusicOn = !isMusicOn;
        actualizarBotonMusica();
    }

    private void actualizarBotonMusica() {
        musicButton.setText(isMusicOn ? "Música ON" : "Música OFF");
    }


    @FXML
    private void iniciarjuego(ActionEvent event) throws IOException {
        JuegoView juegoView = JuegoView.getInstance();
        juegoView.show();
    }
    @FXML
    private void iniciartutorial(ActionEvent event) throws IOException {
        TutorialView tutorialView = TutorialView.getInstance();
        tutorialView.show();
    }
}
