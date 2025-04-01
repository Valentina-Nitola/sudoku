package com.example.sudoku.view;

import com.example.sudoku.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuView extends Stage {

    private MenuController controller;

    public MenuView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("menuView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("SUDOKU - MENU");
        this.setScene(scene);
    }

    public MenuController getController() {
        return controller;
    }

    public static MenuView getInstance() throws IOException {
        if (MenuViewHolder.INSTANCE == null) {
            MenuViewHolder.INSTANCE = new MenuView();
            return MenuViewHolder.INSTANCE;
        } else {
            return MenuViewHolder.INSTANCE;
        }
    }

    private static class MenuViewHolder {
        private static MenuView INSTANCE;
    }
}