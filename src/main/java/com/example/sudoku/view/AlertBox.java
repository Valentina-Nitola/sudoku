package com.example.sudoku.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;
/**
 * Clase que implementa la interfaz AlertBoxInterface para mostrar diferentes tipos de alertas al usuario.
 */
public class AlertBox implements AlertBoxInterface {
    /**
     * Muestra una alerta informativa con título, encabezado y contenido.
     *
     * @param title   Título de la ventana de alerta.
     * @param header  Encabezado del mensaje.
     * @param content Cuerpo del mensaje.
     */
    @Override
    public void showAlertBox(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * Muestra una alerta de confirmación con botones personalizados (Sí / Cancelar).
     *
     * @param title   Título de la alerta.
     * @param header  Encabezado del mensaje.
     * @param content Contenido de la alerta.
     * @return true si el usuario hace clic en "Sí", false si cancela.
     */
    @Override
    public boolean showConfirmAlertBox(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType botonAceptar = new ButtonType("Sí");
        ButtonType botonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == botonAceptar;
    }
}
