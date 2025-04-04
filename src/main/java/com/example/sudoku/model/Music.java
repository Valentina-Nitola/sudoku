package com.example.sudoku.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class Music {
    private static Music instance;
    private MediaPlayer mediaPlayer;
    private boolean isMusicOn = true;

    private Music() {
        iniciarMusica();
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

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

    public void toggleMusic() {
        if (mediaPlayer == null) return;
        if (isMusicOn) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
        isMusicOn = !isMusicOn;
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isMusicOn = false;
        }
    }
}
