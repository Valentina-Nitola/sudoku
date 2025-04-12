package com.example.sudoku.model;

import java.util.*;

/**
 * Clase encargada de generar tableros de Sudoku 6x6, tanto completos como parcialmente resueltos.
 * Utiliza un algoritmo de backtracking para crear soluciones válidas y lógicas.
 * También permite obtener el tablero inicial con pistas y su respectiva solución.
 *
 * El tablero se divide en bloques de 3x2 (3 columnas por 2 filas), según las reglas del Sudoku 6x6.
 *
 * @author Valentina Nitola
 * @version 1.1.3
 */
public class Generador {

    private static final int SIZE = 6;
    private static final int BLOCK_ROWS = 3;
    private static final int BLOCK_COLS = 2;
    private static final Random rand = new Random();

    /**
     * Genera un tablero de Sudoku completamente resuelto de 6x6.
     *
     * @return Matriz 6x6 con la solución completa del tablero.
     * @throws RuntimeException si no se puede generar una solución válida.
     */
    public static int[][] generarTableroCompleto() {
        int[][] tablero = new int[SIZE][SIZE];
        if (resolverSudoku(tablero, 0, 0)) {
            return tablero;
        } else {
            throw new RuntimeException("No se pudo resolver el Sudoku.");
        }
    }

    /**
     * Genera un tablero parcialmente resuelto a partir de una solución completa.
     * Se dejan dos pistas por bloque (de 3x2) de forma aleatoria.
     *
     * @return Matriz 6x6 representando el tablero inicial con algunas celdas ya reveladas.
     */
    public static int[][] generarTableroConPistas() {
        int[][] solucion = generarTableroCompleto();
        int[][] tablero = new int[SIZE][SIZE];

        // Clonar la solución para evitar modificarla directamente
        int[][] copiaSolucion = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(solucion[i], 0, copiaSolucion[i], 0, SIZE);
        }

        // Agregar dos pistas aleatorias por bloque
        for (int blockRow = 0; blockRow < BLOCK_ROWS; blockRow++) {
            for (int blockCol = 0; blockCol < BLOCK_COLS; blockCol++) {
                int startRow = blockRow * 2;
                int startCol = blockCol * 3;

                List<int[]> celdasEnBloque = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        celdasEnBloque.add(new int[]{startRow + i, startCol + j});
                    }
                }

                Collections.shuffle(celdasEnBloque);
                for (int k = 0; k < 2; k++) {
                    int[] pos = celdasEnBloque.get(k);
                    tablero[pos[0]][pos[1]] = copiaSolucion[pos[0]][pos[1]];
                }
            }
        }

        return tablero;
    }

    /**
     * Algoritmo recursivo de backtracking para llenar el tablero con una solución válida.
     *
     * @param board Matriz del tablero actual a resolver.
     * @param row   Fila actual.
     * @param col   Columna actual.
     * @return {@code true} si se encontró una solución válida; {@code false} en caso contrario.
     */
    private static boolean resolverSudoku(int[][] board, int row, int col) {
        if (row == SIZE) return true;

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums);

        for (int num : nums) {
            if (Validacion.puedeColocar(board, row, col, num)) {
                board[row][col] = num;
                if (resolverSudoku(board, nextRow, nextCol)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Genera simultáneamente un tablero con pistas y su solución correspondiente.
     * Ideal para inicializar el juego y mantener coherencia entre vista y lógica.
     *
     * @return Arreglo tridimensional donde el primer elemento [0] es el tablero con pistas,
     *         y el segundo [1] es la solución completa.
     */
    public static int[][][] generarTableroYSolucion() {
        int[][] solucion = generarTableroCompleto();
        int[][] tablero = new int[SIZE][SIZE];

        for (int blockRow = 0; blockRow < BLOCK_ROWS; blockRow++) {
            for (int blockCol = 0; blockCol < BLOCK_COLS; blockCol++) {
                int startRow = blockRow * 2;
                int startCol = blockCol * 3;

                List<int[]> celdasEnBloque = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        celdasEnBloque.add(new int[]{startRow + i, startCol + j});
                    }
                }

                Collections.shuffle(celdasEnBloque);
                for (int k = 0; k < 2; k++) {
                    int[] pos = celdasEnBloque.get(k);
                    tablero[pos[0]][pos[1]] = solucion[pos[0]][pos[1]];
                }
            }
        }

        return new int[][][] { tablero, solucion };
    }
}
