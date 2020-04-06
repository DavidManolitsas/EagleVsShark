package main.java.model;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 */
class Game {

    private Board board;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private int turnCount;

    public Game() {
        this.turnCount = 0;
    }


    public void startGame() {

    }

    public void endGame() {

    }

    public void trackPlayerTurn() {

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getSharkPlayer() {
        return sharkPlayer;
    }

    public void setSharkPlayer(Player sharkPlayer) {
        this.sharkPlayer = sharkPlayer;
    }

    public Player getEaglePlayer() {
        return eaglePlayer;
    }

    public void setEaglePlayer(Player eaglePlayer) {
        this.eaglePlayer = eaglePlayer;
    }

    public int incrementTurnCount() {
        return turnCount++;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }
}
