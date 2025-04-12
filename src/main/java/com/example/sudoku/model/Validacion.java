package com.example.sudoku.model;

/**
 * Clase que proporciona métodos de validación para verificar
 * si un número puede colocarse en una celda específica del Sudoku 6x6
 * y para comprobar si el tablero está completamente resuelto de forma válida.
 *
 * Las reglas de validación consideran filas, columnas y bloques de 3x2.
 *
 * @author Valentina Nitola
 * @version 1.1.4
 */
public class Validacion {

    /**
     * Verifica si un número se puede colocar en una posición dada del tablero
     * respetando las reglas del Sudoku 6x6:
     * - El número no debe repetirse en la fila.
     * - El número no debe repetirse en la columna.
     * - El número no debe repetirse en el bloque 3x2 correspondiente.
     *
     * @param board   El tablero actual del Sudoku.
     * @param fila    Fila en la que se quiere colocar el número.
     * @param columna Columna en la que se quiere colocar el número.
     * @param numero  Número a verificar.
     * @return {@code true} si se puede colocar el número; {@code false} si viola alguna regla.
     */
    public static boolean puedeColocar(int[][] board, int fila, int columna, int numero) {
        for (int i = 0; i < 6; i++) {
            if (board[fila][i] == numero || board[i][columna] == numero)
                return false;
        }

        int startRow = (fila / 2) * 2;
        int startCol = (columna / 3) * 3;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == numero) return false;
            }
        }

        return true;
    }

    /**
     * Verifica si un número se puede colocar en una celda sin contar su valor actual
     * (útil para validación en tiempo real).
     *
     * @param board   El tablero actual.
     * @param fila    Fila del número.
     * @param columna Columna del número.
     * @param numero  Número a validar.
     * @return {@code true} si no hay conflicto con otras celdas; {@code false} si hay error.
     */
    public static boolean validarEntrada(int[][] board, int fila, int columna, int numero) {
        // Guardar el valor original y borrar temporalmente la celda actual
        int original = board[fila][columna];
        board[fila][columna] = 0;

        boolean esValido = puedeColocar(board, fila, columna, numero);

        // Restaurar el valor original
        board[fila][columna] = original;

        return esValido;
    }

    /**
     * Comprueba si el tablero está completamente y correctamente lleno.
     * Cada celda debe tener un valor distinto de cero y cumplir las reglas del Sudoku.
     *
     * @param board El tablero a verificar.
     * @return {@code true} si el tablero está completo y es válido; {@code false} en caso contrario.
     */
    public static boolean isComplete(int[][] board) {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                if (board[i][j] == 0 || !puedeColocar(board, i, j, board[i][j]))
                    return false;
        return true;
    }
}
