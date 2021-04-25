package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.Piece;

import java.util.Objects;

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

    public Cell(int x, int y) {
        coordinates = new Pair<>(x,y);
    }

    public int getX() {
        return coordinates.getX();
    }

    public int getY() {
        return coordinates.getY();
    }

    public Piece takePiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "coordinates=" + coordinates +
                ", piece=" + piece +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return coordinates.equals(cell.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
