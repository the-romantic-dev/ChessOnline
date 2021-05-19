package org.spbstu.ysa.chessonline.model.pieces;

import android.util.Log;

import androidx.annotation.NonNull;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {
    private boolean color;

    public Piece(boolean color) {
        this.color = color;
    }

    public boolean isWhite() {
        return this.color;
    }

    public abstract Set<Cell> getAllowedCells(Cell cell, Board board);

    public Set<Cell> filterAllowedMoves( Cell cellOfPiece, Board board) throws CloneNotSupportedException{
        Set<Cell> filteredMoves = new HashSet();
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
    public String toString() {
        String pieceColor = color ? "white" : "black";
        return pieceColor + "_" + getName();
    }
}
