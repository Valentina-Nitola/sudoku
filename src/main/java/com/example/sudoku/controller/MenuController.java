package com.example.sudoku.controller;

import com.example.sudoku.model.Music;
import com.example.sudoku.view.TutorialView;
import com.example.sudoku.view.JuegoView;
import com.example.sudoku.view.MenuView;
import com.example.sudoku.view.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Clase controladora del menú principal del juego Sudoku.
 * Gestiona los eventos asociados a los botones del menú, como iniciar juego, ver el tutorial y activar/desactivar la música.
 *
 * @author Valentina Nitola
 * @version 1.1.3
 */
public class MenuController {

    /**
     * Botón que permite activar o desactivar la música.
     */
    @FXML
    private Button musicButton;

    /**
     * Método que se ejecuta al cargar la vista del menú.
     * Inicializa la música y actualiza el estado del botón de música.
     */
    @FXML
    public void initialize() {
        Music.getInstance();
        actualizarBotonMusica();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón de música.
     * Alterna entre encender y apagar la música, y actualiza la imagen del botón.
     *
     * @param event Evento generado al hacer clic en el botón.
     */
    @FXML
    private void musica(ActionEvent event) {
        Music.getInstance().toggleMusic();
        actualizarBotonMusica();
    }

    /**
     * Actualiza la imagen del botón de música dependiendo si la música está activada o no.
     * Ajusta también el tamaño del ícono para mantener el diseño visual definido en Scene Builder.
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
        } else {
            System.out.println("No se encontró la imagen: " + imgPath);
        }
    }

    /**
     * Abre la vista del juego principal al hacer clic en el botón correspondiente.
     *
     * @param event Evento generado al hacer clic en el botón "Iniciar juego".
     * @throws IOException Si ocurre un error al cargar la vista del juego.
     */
    @FXML
    private void iniciarjuego(ActionEvent event) throws IOException {
        AlertBox alertBox = new AlertBox();

        boolean confirmado = alertBox.showConfirmAlertBox(
                "Confirmar nuevo juego",
                "¿Estás seguro de que deseas iniciar un nuevo juego?",
                "Recuerda leer primero las isntucciones en (?)"
        );

        if (confirmado) {
            System.out.println("Iniciar juego");
            JuegoView juegoView = JuegoView.getInstance();
            MenuView.getInstance().close();
            juegoView.show();
        } else {
            System.out.println("La usuaria canceló el nuevo juego.");
        }


    }

    /**
     * Abre la vista del tutorial al hacer clic en el botón correspondiente.
     *
     * @param event Evento generado al hacer clic en el botón "Tutorial".
     * @throws IOException Si ocurre un error al cargar la vista del tutorial.
     */
    @FXML
    private void iniciartutorial(ActionEvent event) throws IOException {
        System.out.println("Iniciar tutorial");
        TutorialView tutorialView = TutorialView.getInstance();
        MenuView.getInstance().close();
        tutorialView.show();
    }
}
