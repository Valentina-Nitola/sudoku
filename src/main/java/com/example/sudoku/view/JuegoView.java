package com.example.sudoku.view;

import com.example.sudoku.controller.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JuegoView extends Stage {
    private JuegoController controller;

    public JuegoView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/sudoku/view/juegoView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("SUDOKU - JUEGO");
        this.setScene(scene);
    }

    public JuegoController getController() {
        return controller;
    }

    public static JuegoView getInstance() throws IOException {
        if (JuegoViewHolder.INSTANCE == null) {
            JuegoViewHolder.INSTANCE = new JuegoView();
        }
        return JuegoViewHolder.INSTANCE;
    }

    private static class JuegoViewHolder {
        private static JuegoView INSTANCE;
    }
}
