package com.example.sudoku.model;

import javafx.scene.control.TextField;

/**
 * Clase encargada de gestionar el sistema de pistas dentro del juego Sudoku.
 * Permite al usuario revelar valores correctos en celdas vacías hasta un máximo definido.
 * También permite reiniciar el contador de pistas al iniciar un nuevo juego.
 *
 * Cada pista revela el valor correcto en una celda vacía del tablero y lo marca visualmente.
 *
 * @author Valentina Nitola
 * @version 1.1.3
 */
public class Pistas {

    /**
     * Contador de pistas utilizadas durante la partida.
     */
    private static int pistasUsadas = 0;

    /**
     * Número máximo de pistas permitidas por partida.
     */
    private static final int MAX_PISTAS = 23;

    /**
     * Reinicia el contador de pistas a cero.
     * Se utiliza al comenzar una nueva partida.
     */
    public static void reiniciarPistas() {
        pistasUsadas = 0;
    }

    /**
     * Intenta aplicar una pista al tablero.
     * Busca la primera celda vacía, la rellena con el valor correcto
     * y cambia su color de fondo para identificarla como pista.
     *
     * @param celdas   Matriz de TextField que representa las celdas del tablero.
     * @param solucion Matriz de enteros que representa la solución completa del tablero.
     * @return {@code true} si se pudo dar una pista; {@code false} si ya se alcanzó el máximo.
     */
    public static boolean darPista(TextField[][] celdas, int[][] solucion) {
        if (pistasUsadas >= MAX_PISTAS) return false;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (celdas[i][j].getText().isEmpty()) {
                    celdas[i][j].setText(String.valueOf(solucion[i][j]));
                    celdas[i][j].setStyle("-fx-background-color: #C8E6C9;"); // Color verde claro
                    pistasUsadas++;
                    return true;
                }
            }
        }
        return false;
    }
}
