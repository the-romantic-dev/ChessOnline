package org.spbstu.ysa.chessonline.online;

public class Room {
    private String password;
    //Вместо chessBoard будет объект с состоянием шахматного поля
    private String chessBoard;
    private Boolean connection;

    public Room() {
    }

    public Room(String password, String chessBoard, boolean connection) {
        this.password = password;
        this.chessBoard = chessBoard;
        this.connection = connection;
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

    public boolean getConnection() { return connection; }

    public void setConnection(boolean connection) { this.connection = connection; }
}
