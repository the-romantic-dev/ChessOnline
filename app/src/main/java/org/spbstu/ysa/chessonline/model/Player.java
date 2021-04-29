package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private boolean color;
    private final Cell[][] board = new Cell[8][8];
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null ;

    public Player(boolean color) {
        this.color = color;
        Piece[] whitePieces = new Piece[]{
                new Rook(true), new Knight(true), new Bishop(true),
                new Queen(true), new King(true), new Bishop(true),
                new Knight(true), new Rook(true), new Pawn(true)
        };
        Piece[] blackPieces = new Piece[]{
                new Rook(false), new Knight(false), new Bishop(false),
                new Queen(false), new King(false), new Bishop(false),
                new Knight(false), new Rook(false), new Pawn(false)
        };
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                switch (y) {
                    case 0:
                        board[y][x] = new Cell(x, y, whitePieces[x]);
                        break;
                    case 1:
                        board[y][x] = new Cell(x,y,whitePieces[8]);
                        break;
                    case 6:
                        board[y][x] = new Cell(x,y,blackPieces[8]);
                        break;
                    case 7:
                        board[y][x] = new Cell(x, y, blackPieces[x]);
                        break;
                    default:
                        board[y][x] = new Cell(x,y);
                }
            }
        }
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public Set<Cell> capturePiece(Cell cell) {
        this.currentCell = cell;
        int x = cell.getX();
        int y = cell.getY();
        if (cell.getPiece() == null) return new HashSet<>();
        Piece capturePiece = cell.getPiece();
        Set<Cell> allowedMoves = capturePiece.getAllowedCells(x, y, this.board);
        this.allowedMoves = allowedMoves;
        return allowedMoves;
    }

    public boolean putPiece(Cell cell){
        if(!allowedMoves.contains(cell)) return false;

        Piece capturedPiece = currentCell.getPiece();
        currentCell.removePiece();
        cell.setPiece(capturedPiece);
        return true;
    }
}
