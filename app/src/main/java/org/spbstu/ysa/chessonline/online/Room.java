package org.spbstu.ysa.chessonline.online;

public class Room {
    private String password;
    //Вместо chessBoard будет объект с состоянием шахматного поля
    private String chessBoard;

    public Room() {
    }

    public Room(String password, String chessBoard) {
        this.password = password;
        this.chessBoard = chessBoard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(String chessBoard) {
        this.chessBoard = chessBoard;
    }
}
