module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sudoku.view to javafx.graphics, javafx.fxml;
    opens com.example.sudoku.controller to javafx.fxml;
    exports com.example.sudoku.view;
}
