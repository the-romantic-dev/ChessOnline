package org.spbstu.ysa.chessonline.model;

import android.util.Log;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Cell[][] board = new Cell[8][8];
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null;


    public Board() {
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

    public Board(Cell[][] array) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = array[i][j];
            }
        }
    }

    public Cell[][] getData() {
        return board;
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }

    public void setAllowedMoves(Set<Cell> set) {
        this.allowedMoves = set;
    }

    public Set<Cell> capturePiece(Cell cell) {
        setCurrentCell(cell);
        if (cell.getPiece() == null) return new HashSet<>();
        Piece capturePiece = cell.getPiece();
        Set<Cell> allowedMoves = null;
        try {
            allowedMoves = capturePiece.filterAllowedMoves(cell,this);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        setAllowedMoves(allowedMoves);
        //Log.d("PIECES",board[0][6].toString());
        return allowedMoves;
    }

    public boolean putPiece(Cell cell) {

        if (!allowedMoves.contains(cell)) return false;

        Piece capturedPiece = currentCell.getPiece();
        currentCell.removePiece();
        cell.setPiece(capturedPiece);

        return true;
    }

    public boolean isCheck(boolean isPlayerWhite) {
        Cell kingCell = this.findKingCell(isPlayerWhite);

        for (Cell[] column : board) {
            for (Cell cell : column) {
                if (cell.getPiece() != null) {
                    Set<Cell> allowedMoves = findAllOpponentsMoves(!isPlayerWhite);
                    if (allowedMoves.contains(kingCell)) return true;
                }


            }
        }
        return false;
    }

    public Cell findKingCell(boolean isPlayerWhite) {
        Set<Cell> pieces = this.setOfCellsWithPiecesOneColor(isPlayerWhite);
        for (Cell cell : pieces) {
            if (cell.getPiece().getName().equals("King")) return cell;
        }

        return null;
    }

    private Set<Cell> setOfCellsWithPiecesOneColor(boolean isPlayerWhite) {
        Set<Cell> res = new HashSet();

        for (Cell[] column : board) {
            for (Cell cell : column) {
                Piece piece = cell.getPiece();
                if (piece != null && piece.isWhite() == isPlayerWhite) res.add(cell);
            }
        }
        return res;
    }

    private Set<Cell> findAllOpponentsMoves(boolean isOpponentWhite){
        Set<Cell> setOfOpponentsCells = setOfCellsWithPiecesOneColor(isOpponentWhite);
        Set<Cell> res = new HashSet();
         for (Cell cell: setOfOpponentsCells){
             res.addAll(cell.getPiece().getAllowedCells(cell, this));
         }
         return res;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Cell[][] curBoardData = this.getData();
        Cell[][] cloneBoardData = new Cell[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cloneBoardData[i][j] = curBoardData[i][j].clone();
            }
        }
        return new Board(cloneBoardData);
    }
}
