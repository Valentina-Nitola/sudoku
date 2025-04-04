package com.example.sudoku.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Clase que gestiona la reproducción de música de fondo en el juego Sudoku.
 * Utiliza el patrón Singleton para asegurar una única instancia y permite controlar la reproducción.
 * La música se reproduce en bucle indefinido.
 *
 * @author Valentina Nitola
 * @version 1.0.2
 */
public class Music {

    /**
     * Instancia única de la clase {@link Music} (patrón Singleton).
     */
    private static Music instance;

    /**
     * Reproductor de medios de JavaFX utilizado para reproducir la música.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Bandera que indica si la música está actualmente activa o pausada.
     */
    private boolean isMusicOn = true;

    /**
     * Constructor privado para evitar instanciación externa.
     * Llama al método {@code iniciarMusica()} para reproducir la música al crear la instancia.
     */
    private Music() {
        iniciarMusica();
    }

    /**
     * Obtiene la instancia única de {@link Music}.
     *
     * @return instancia única de la clase.
     */
    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    /**
     * Inicia la reproducción de música cargando el archivo de sonido desde los recursos del proyecto.
     * La música se reproduce en bucle infinito.
     */
    private void iniciarMusica() {
        URL musicURL = getClass().getResource("/com/example/sudoku/sound/sounds.mp3");
        if (musicURL != null) {
            Media media = new Media(musicURL.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else {
            System.out.println("No se encontró el archivo de música.");
        }
    }

    /**
     * Alterna entre reproducir y pausar la música.
     * Si la música está encendida, se pausa; si está apagada, se reanuda.
     */
    public void toggleMusic() {
        if (mediaPlayer == null) return;
        if (isMusicOn) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
        isMusicOn = !isMusicOn;
    }

    /**
     * Detiene completamente la reproducción de la música y la desactiva.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isMusicOn = false;
        }
    }

    /**
     * Verifica si la música está actualmente activa.
     *
     * @return {@code true} si la música está encendida, {@code false} si está apagada.
     */
    public boolean isMusicOn() {
        return isMusicOn;
    }
}

