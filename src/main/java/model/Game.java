package main.java.model;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 */
public class Game {

    public interface GameModelEventListener {
        void updateGameInfo(int turnCount, int sharkSquareCount, int eagleSquareCount);
    }

    private Board board;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private int sharkSquareCount;
    private int eagleSquareCount;
    private int turnCount;

    public Game() {

        this.sharkSquareCount = 0;
        this.eagleSquareCount = 0;
        this.turnCount = 1;
    }

    private static final Game INSTANCE = new Game();

    public static Game getInstance() {
        return INSTANCE;
    }

    public void endGame() {

    }

    public void trackPlayerTurn() {

    }

    public void updateSquareCount(int sharkSquareCount, int eagleSquareCount) {
        this.sharkSquareCount = sharkSquareCount;
        this.eagleSquareCount = eagleSquareCount;
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

    public Player getEaglePlayer() {
        return eaglePlayer;
    }

    public int incrementTurnCount() {
        return turnCount++;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public int getSharkSquareCount() {
        return sharkSquareCount;
    }

    public int getEagleSquareCount() {
        return eagleSquareCount;
    }

    public void setPlayers(String sharkPlayerName, String eaglePlayerName) {
        this.sharkPlayer = new Player(sharkPlayerName);
        this.eaglePlayer = new Player(eaglePlayerName);
    }
}
