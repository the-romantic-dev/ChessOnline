package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Cell[][] board = new Cell[8][8];
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null;


    public Board(){
        Piece[] whitePieces = new Piece[]{
                new Rook(true), new Knight(true), new Bishop(true),
                new Queen(true), new King(true), new Bishop(true),
                new Knight(true), new Rook(true)
        };
        Piece[] blackPieces = new Piece[]{
                new Rook(false), new Knight(false), new Bishop(false),
                new Queen(false), new King(false), new Bishop(false),
                new Knight(false), new Rook(false)
        };

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece currentPiece;
                switch (y) {
                    case 0:
                        currentPiece = whitePieces[x];
                        break;
                    case 1:
                        currentPiece = new Pawn(true, x, y);
                        break;
                    case 6:
                        currentPiece = new Pawn(false, x, y);
                        break;
                    case 7:
                        currentPiece = blackPieces[x];
                        break;
                    default:
                        currentPiece = null;
                }
                board[y][x] = new Cell(x, y, currentPiece);
            }
        }
    }

    public Cell[][] getData(){
        return board;
    }

    public Set<Cell> capturePiece(Cell cell) {
        this.currentCell = cell;
        int x = cell.getX();
        int y = cell.getY();
        if (cell.getPiece() == null) return new HashSet<>();
        Piece capturePiece = cell.getPiece();
        Set<Cell> allowedMoves = capturePiece.getAllowedCells(x, y, this);
        this.allowedMoves = allowedMoves;
        return allowedMoves;
    }

    public boolean putPiece(Cell cell) {
        if (!allowedMoves.contains(cell)) return false;

        Piece capturedPiece = currentCell.getPiece();
        currentCell.removePiece();
        cell.setPiece(capturedPiece);
        return true;
    }
}
