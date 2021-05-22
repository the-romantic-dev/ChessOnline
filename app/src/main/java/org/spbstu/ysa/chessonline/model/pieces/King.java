package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King extends Piece {

    private boolean isMoved = false;

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public Set<Cell> getAllowedCells(Cell cell, Board boardClass) {
        int x = cell.getX();
        int y = cell.getY();
        Set<Cell> res = new HashSet<>();

        Cell[][] board = boardClass.getData();

        List<Integer> listOfIterator = new ArrayList<>();
        listOfIterator.add(-1);
        listOfIterator.add(0);
        listOfIterator.add(1);

        for (int iteratorX : listOfIterator) {
            for (int iteratorY : listOfIterator) {
                if (iteratorX == 0 && iteratorY == 0) continue;
                int curX = x + iteratorX;
                int curY = y + iteratorY;
                if (curX >= 0 && curX <= 7 && curY >= 0 && curY <= 7
                        && (board[curY][curX].getPiece() == null
                        || board[curY][curX].getPiece().isWhite() != this.isWhite()))
                    res.add(new Cell(curX, curY));
            }
        }

        return res;
    }

    @Override
    public Set<Cell> filterAllowedMoves(Cell cellOfPiece, Board board) throws CloneNotSupportedException {

        Set<Cell> res = new HashSet<>(super.filterAllowedMoves(cellOfPiece, board));
        int y = isWhite() ? 0 : 7;
        int x = cellOfPiece.getX();
        List<Integer> listOfIterator = new ArrayList<>();
        listOfIterator.add(-1);
        listOfIterator.add(1);
        if (!this.isMoved) {
            boolean areAllMovesGood;
            int curX;
            for (int iterator : listOfIterator) {
                curX = x + iterator;
                while (curX >= 0 && curX < 8) {
                    if (curX == 0 || curX == 7) {
                        Piece checkingPiece = board.getData()[y][curX].getPiece();
                        if (checkingPiece != null && checkingPiece.getName().equals("Rook")) {
                            Rook rook = (Rook) board.getData()[y][curX].getPiece();
                            if (!rook.isMoved()) {
                                areAllMovesGood = true;
                                Set<Cell> moves = new HashSet<>();
                                moves.add(board.getData()[y][x + iterator]);
                                moves.add(board.getData()[y][x + 2 * iterator]);
                                for (Cell cell : moves) {
                                    Board newBoard = board.clone();
                                    Cell newCell = newBoard.getData()[cellOfPiece.getY()][cellOfPiece.getX()];
                                    Set<Cell> newAllowedMoves = new HashSet<>();
                                    newAllowedMoves.add(newBoard.getData()[y][x + iterator]);
                                    newAllowedMoves.add(newBoard.getData()[y][x + 2 * iterator]);
                                    newBoard.setCurrentCell(newCell);
                                    newBoard.setAllowedMoves(newAllowedMoves);
                                    newBoard.putPiece(newBoard.getData()[cell.getY()][cell.getX()]);
                                    areAllMovesGood = areAllMovesGood & !newBoard.isCheck(this.isWhite());
                                }
                                if (areAllMovesGood) res.add(board.getData()[y][curX]);
                                areAllMovesGood = false;
                            }
                        }
                    } else if (board.getData()[y][curX].getPiece() != null) break;
                    curX = curX + iterator;
                }
            }
        }
        return res;
    }

    @Override
    public String getName() {
        return "King";
    }

    @Override
    public Piece clone() {
        return new King(isWhite());
    }


    public void setMoved() {
        isMoved = true;
    }
}
