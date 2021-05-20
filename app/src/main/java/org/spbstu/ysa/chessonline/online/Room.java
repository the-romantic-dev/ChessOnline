package org.spbstu.ysa.chessonline.online;

public class Room {
    private String password;
    //Вместо chessBoard будет объект с состоянием шахматного поля
    private String chessBoard;
    private Boolean connection;
    private Boolean creatorIsWhite;

    public Room() {
    }

    public Room(String password, String chessBoard, boolean connection, boolean isWhite) {
        this.password = password;
        this.chessBoard = chessBoard;
        this.connection = connection;
        this.creatorIsWhite = isWhite;
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

    public Boolean getCreatorColor() {
        return creatorIsWhite;
    }

    public void setCreatorColor(Boolean white) {
        creatorIsWhite = white;
    }
}
