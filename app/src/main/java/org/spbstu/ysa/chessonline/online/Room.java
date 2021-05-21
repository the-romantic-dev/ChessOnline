package org.spbstu.ysa.chessonline.online;

public class Room {
    private String password;
    private Boolean connection;
    private Boolean creatorIsWhite;
    private Move move;

    public Room() {
    }

    public Room(String password, boolean connection, boolean isWhite, Move move) {
        this.password = password;
        this.connection = connection;
        this.creatorIsWhite = isWhite;
        this.move = move;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getConnection() { return connection; }

    public void setConnection(boolean connection) { this.connection = connection; }

    public Boolean getCreatorColor() {
        return creatorIsWhite;
    }

    public void setCreatorColor(Boolean white) {
        creatorIsWhite = white;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
