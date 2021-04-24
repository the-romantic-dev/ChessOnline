package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.Piece;

public class Cell {
    /**
     * Клетка(Cell)
     * хранит свою координату по иксу (CoordinatesX) и по игрику (CoordinatesY), и какая фигура на ней стоит.(Piece)
     * методы:
     * получить X или Y координату;
     * Взять фигуру
     * Поставить фигуру
     */

    private final Pair<Integer, Integer> coordinates;
    private Piece piece;

    public Cell(int x, int y, Piece piece){
        coordinates = new Pair<>(x,y);
        this.piece = piece;
    }

    public int getCoordinatesX() {
        return coordinates.getX();
    }

    public int getCoordinatesY() {
        return coordinates.getY();
    }

    public Piece takePiece(){
        return this.piece;
    }

    public void putPiece(Piece piece){
        this.piece = piece;
    }

}
