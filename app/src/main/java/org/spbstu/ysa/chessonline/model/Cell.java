package org.spbstu.ysa.chessonline.model;

import androidx.annotation.NonNull;

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

    private final int x;
    private final int y;
    private Piece piece;

    public Cell(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    @Override
    protected Cell clone() throws CloneNotSupportedException {

        Piece clonePiece = this.getPiece() == null ? null : this.getPiece().clone();
        return new Cell(this.getX(), this.getY(),clonePiece );
    }

    @Override
    public String toString() {
        return "Cell{" +
                "coordinates=" + "(" + x + " ; " + y +
                ") piece=" + piece +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return (this.x == cell.getX() && this.y == cell.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
