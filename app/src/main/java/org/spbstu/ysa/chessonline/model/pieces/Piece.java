package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {
    private final boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public abstract Set<Cell> getAllowedCells(Cell cell, Board board);

    public Set<Cell> filterAllowedMoves( Cell cellOfPiece, Board board) throws CloneNotSupportedException{
        Set<Cell> filteredMoves = new HashSet<>();
        Set<Cell> allowedMoves = this.getAllowedCells(cellOfPiece, board);
        for (Cell cell : allowedMoves) {
            Board newBoard = board.clone();
            Cell newCell = newBoard.getData()[cellOfPiece.getY()][cellOfPiece.getX()];
            Set<Cell> newAllowedMoves = newCell.getPiece().getAllowedCells(newCell, newBoard);
            newBoard.setCurrentCell(newCell);
            newBoard.setAllowedMoves(newAllowedMoves);
            newBoard.putPiece(newBoard.getData()[cell.getY()][cell.getX()]);
            if(!newBoard.isCheck(this.isWhite())) filteredMoves.add(cell);
        }
        return filteredMoves;
    }

    public abstract String getName();


    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        String pieceColor = isWhite ? "white" : "black";
        return pieceColor + "_" + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return this.toString().equals(piece.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWhite) + Objects.hash(getName());
    }
}
