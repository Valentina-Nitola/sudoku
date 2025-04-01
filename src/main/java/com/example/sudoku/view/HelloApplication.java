package com.example.sudoku.view;

import com.example.sudoku.view.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MenuView menuView = MenuView.getInstance();
        menuView.show();

    }
    public static void main(String[] args) {
        launch();
    }
}