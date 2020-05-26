package main.java.model;

import main.java.model.obstacles.ObstacleType;
import main.java.model.piece.Piece;

/**
 * Invariant:
 * 1. row >= 0 && col >= 0
 * 2. row < Board.ROW && col < Board.COL
 */
public class Square {

    private int row;

    private int col;

    private Piece piece;

    private Player occupiedPlayer;

    private ObstacleType obstacle;

    /**
     * Requires:
     * 1. row >= 0 && col >= 0
     * 2. row < Board.ROW && col < Board.COL
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        occupiedPlayer = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player getOccupiedPlayer() {
        return occupiedPlayer;
    }

    public void setOccupiedPlayer(Player occupiedPlayer) {
        this.occupiedPlayer = occupiedPlayer;
    }

    public void setObstacle(ObstacleType obstacle) {
        this.obstacle = obstacle;
    }

    public ObstacleType getObstacle() {
        return obstacle;
    }

    public boolean hasObstacle() {
        return obstacle != null;
    }

    public boolean isBlocked(Piece piece) {
        if (obstacle == null || piece == null) {
            return false;
        }

        switch (obstacle) {
            case ROCK -> {
                return piece.isBelongTo(Player.SHARK);
            }
            case TREE -> {
                return piece.isBelongTo(Player.EAGLE);
            }
            case FOREST -> {
                return true;
            }
        }
        return false;
    }
}
