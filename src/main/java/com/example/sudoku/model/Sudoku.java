package com.example.sudoku.model;

public class Sudoku {
    private int[][] tablero;


    public Sudoku(){
        tablero = new int[6][6];
        inicializarTablero();
    }
    private void inicializarTablero(){
        for(int fila = 0; fila < 6; fila++){
            for(int column = 0; column < 6; column++){
                tablero[fila][column] = 0;
            }
        }
    }

    public void setCelda(int fila, int columna, int valor){
        tablero[fila][columna] = valor;
    }

    public int getCelda(int fila, int columna){
        return tablero[fila][columna];
    }

    public void imprimirTablero(){
        for(int fila = 0; fila < 6;fila++){
            for (int columna = 0; columna < 6; columna++) {
                System.out.print(tablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
