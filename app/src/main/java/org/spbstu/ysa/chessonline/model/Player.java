package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private boolean isWhite;
    private final Cell[][] board = new Cell[8][8];
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null;
    private boolean turn = true;


    public Player(boolean isWhite) {
        this.isWhite = isWhite;
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
                switch (y) {
                    case 0:
                        board[y][x] = new Cell(x, y, whitePieces[x]);
                        break;
                    case 1:
                        board[y][x] = new Cell(x, y, new Pawn(true, x, y));
                        break;
                    case 6:
                        board[y][x] = new Cell(x, y, new Pawn(false, x, y));
                        break;
                    case 7:
                        board[y][x] = new Cell(x, y, blackPieces[x]);
                        break;
                    default:
                        board[y][x] = new Cell(x, y);
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

    public boolean putPiece(Cell cell) {
        if (!allowedMoves.contains(cell)) return false;

        Piece capturedPiece = currentCell.getPiece();
        currentCell.removePiece();
        cell.setPiece(capturedPiece);
        return true;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setColor(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isCheck() {
        Cell kingCell = this.findKingCell();

        for (Cell[] column : board) {
            for (Cell cell : column) {
                Set<Cell> allowedMoves = cell.getPiece().getAllowedCells(cell.getX(), cell.getY(), board);
                if (allowedMoves.contains(kingCell)) return true;
            }
        }
        return false;
    }

    public Cell findKingCell() {
        for (Cell[] column : board) {
            for (Cell cell : column) {
                if (cell.getPiece().getName().equals("King")) return cell;
            }
        }
        return null;
    }

    public boolean isCheckmate() {
        Set<Cell> allAllowedMoves = new HashSet<>();

        for (Cell[] column : board) {
            for (Cell cell : column) {
                Piece curPiece = cell.getPiece();
                if (curPiece.isWhite() == this.isWhite)
                    allAllowedMoves.addAll(curPiece.getAllowedCells(cell.getX(),cell.getY(),board));
            }
        }

        return this.isCheck() && allAllowedMoves.isEmpty();
    }
}
