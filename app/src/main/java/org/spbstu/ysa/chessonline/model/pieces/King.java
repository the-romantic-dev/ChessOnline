package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King extends Piece {

    private boolean isMoved = false;

    public King(boolean color) {
        super(color);
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

        Set<Cell> res = new HashSet<>();
        res.addAll(super.filterAllowedMoves(cellOfPiece, board));

        int x = cellOfPiece.getX();
        List<Integer> listOfIterator = new ArrayList<>();
        listOfIterator.add(-1);
        listOfIterator.add(1);
        if (!this.isMoved) {
            boolean areAllMovesGood = false;
            int curX = x;
            for (int iterator : listOfIterator) {
                curX = x + iterator;
                while (curX >= 0 && curX < 8) {
                    if (curX == 0 || curX == 7) {
                        if (board.getData()[0][curX].getPiece().getName().equals("Rook")) {
                            Rook rook = (Rook) board.getData()[0][curX].getPiece();
                            if (!rook.isMoved()) {
                                areAllMovesGood = true;
                                Set<Cell> moves = new HashSet();
                                moves.add(board.getData()[0][x + iterator]);
                                moves.add(board.getData()[0][x + 2 * iterator]);
                                for (Cell cell : moves) {
                                    Board newBoard = board.clone();
                                    Cell newCell = newBoard.getData()[cellOfPiece.getY()][cellOfPiece.getX()];
                                    Set<Cell> newAllowedMoves = new HashSet();
                                    newAllowedMoves.add(newBoard.getData()[0][x + iterator]);
                                    newAllowedMoves.add(newBoard.getData()[0][x + 2 * iterator]);
                                    newBoard.setCurrentCell(newCell);
                                    newBoard.setAllowedMoves(newAllowedMoves);
                                    newBoard.putPiece(newBoard.getData()[cell.getY()][cell.getX()]);
                                    areAllMovesGood = areAllMovesGood & !newBoard.isCheck(this.isWhite());
                                }
                                if (areAllMovesGood) res.add(board.getData()[0][curX]);
                                areAllMovesGood = false;
                            }
                        }
                    } else if (board.getData()[0][curX].getPiece() != null) break;
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
    public Piece clone() throws CloneNotSupportedException {
        return new King(isWhite());
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved() {
        isMoved = true;
    }
}
