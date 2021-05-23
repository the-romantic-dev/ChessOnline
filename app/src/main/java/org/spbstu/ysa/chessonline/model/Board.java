package org.spbstu.ysa.chessonline.model;

import android.util.Log;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Cell[][] board = new Cell[8][8];
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null;
    private Cell promotedCell = null;


    public Board() {
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
                Piece currentPiece;
                switch (y) {
                    case 0:
                        currentPiece = whitePieces[x];
                        break;
                    case 1:
                        currentPiece = whitePieces[9];
                        break;
                    case 6:
                        currentPiece = blackPieces[9];
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
            System.arraycopy(array[i], 0, board[i], 0, 8);
        }
    }

    public Cell[][] getData() {
        return board;
    }

    public Cell getPromotedCell() {
        return promotedCell;
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
            allowedMoves = capturePiece.filterAllowedMoves(cell, this);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        setAllowedMoves(allowedMoves);
        return allowedMoves;
    }

    public Set<Cell> putPiece(Cell cell) {

        if (!allowedMoves.contains(cell)) return null;

        String curPieceName = "";
        if (currentCell.getPiece() != null) curPieceName = currentCell.getPiece().getName();

        Set<Cell> res = new HashSet<>();
        res.add(currentCell);
        res.add(cell);
        //Pawns moves
        Set<Cell> setOfPawns = findAllPawns();
        //Passant realisation
        for (Cell pawnCell : setOfPawns) {
            Pawn pawn = (Pawn) pawnCell.getPiece();
            pawn.isPassantAvailable = false;
        }
        if (curPieceName.equals("Pawn")) {
                                    //if Pawn made move on 2 cell -> it can be passant
            Pawn curPawn = (Pawn) currentCell.getPiece();
            curPawn.setMoved();
            if (Math.abs(currentCell.getY() - cell.getY()) == 2) {
                curPawn.isPassantAvailable = true;
            }                       //Passant move realisation
            if (currentCell.getX() != cell.getX() && cell.getPiece() == null) {
                int i = currentCell.getPiece().isWhite() ? -1 : 1;
                Cell attackedCell = board[cell.getY() + i][cell.getX()];
                res.add(attackedCell);
                attackedCell.removePiece();
            }
                                    //Promotion -> if pawn move to BORDER_COORDINATE it have to promoted
            if(cell.getY() == curPawn.BORDER_COORDINATE) promotedCell = cell;
        }
        //Castling realisation(changing isMoved field)
        if (curPieceName.equals("King")) {
            King curKing = (King) currentCell.getPiece();
            curKing.setMoved();
        }
        if (curPieceName.equals("Rook")) {
            Rook curKing = (Rook) currentCell.getPiece();
            curKing.setMoved();
        }

        //Castling realisation(make a castling)
        if (curPieceName.equals("King") && cell.getPiece() != null &&
                cell.getPiece().isWhite() == currentCell.getPiece().isWhite()) {
            Log.d("Castling","DONE");
            Piece capturedPiece = currentCell.getPiece();
            currentCell.removePiece();
            Piece swappedRook = cell.getPiece();
            cell.removePiece();
            int i = cell.getX() == 0 ? -1 : 1;
            int y = capturedPiece.isWhite() ? 0 : 7;
            Cell newKingCell = board[y][currentCell.getX() + 2 * i];
            Cell newRookCell = board[y][currentCell.getX() + i];
            newKingCell.setPiece(capturedPiece);
            newRookCell.setPiece(swappedRook);
            res.add(newKingCell);
            res.add(newRookCell);
        } else {
            //Standard method
            Piece capturedPiece = currentCell.getPiece();
            currentCell.removePiece();
            cell.setPiece(capturedPiece);
        }
        return res;
    }

    public boolean isCheckmate(boolean isPlayerWhite) {
        if (isCheck(isPlayerWhite)) {
            Set<Cell> allowedMoves = new HashSet<>();
            Set<Cell> friendPieceCells = setOfCellsWithPiecesOneColor(isPlayerWhite);
            for (Cell cell : friendPieceCells) {
                try {
                    allowedMoves.addAll(cell.getPiece().filterAllowedMoves(cell, this));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            return allowedMoves.isEmpty();
        }
        return false;
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

    public void makePromotion(Piece promotingPiece){
        promotedCell.setPiece(promotingPiece);
        promotedCell = null;
    }

    private Set<Cell> setOfCellsWithPiecesOneColor(boolean isPlayerWhite) {
        Set<Cell> res = new HashSet<>();

        for (Cell[] column : board) {
            for (Cell cell : column) {
                Piece piece = cell.getPiece();
                if (piece != null && piece.isWhite() == isPlayerWhite) res.add(cell);
            }
        }
        return res;
    }

    private Set<Cell> findAllOpponentsMoves(boolean isOpponentWhite) {
        Set<Cell> setOfOpponentsCells = setOfCellsWithPiecesOneColor(isOpponentWhite);
        Set<Cell> res = new HashSet<>();
        for (Cell cell : setOfOpponentsCells) {
            res.addAll(cell.getPiece().getAllowedCells(cell, this));
        }
        return res;
    }

    private Set<Cell> findAllPawns() {
        Set<Cell> setOfPiece = setOfCellsWithPiecesOneColor(true);
        setOfPiece.addAll(setOfCellsWithPiecesOneColor(false));
        Set<Cell> res = new HashSet<>();
        for (Cell cell : setOfPiece) {
            if (cell.getPiece().getName().equals("Pawn")) res.add(cell);
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
