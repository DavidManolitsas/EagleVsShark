package main.java.model;


/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 */
public class Game {

    public interface GameModelEventListener {
        void gameInitialised(String eaglePlayerName, String sharkPlayerName,
                             int turnCount, int totalTurns, double sharkScore, double eagleScore);

        void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore);
    }

    private static final double TOTAL_SQUARES = 150;

    private Board board;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private double sharkSquareCount;
    private double eagleSquareCount;
    private int turnCount;
    private static final int TOTAL_TURNS = 30;

    private GameModelEventListener listener;

    private static final Game INSTANCE = new Game();

    public static Game getInstance() {
        return INSTANCE;
    }

    public void nextTurn() {
        incrementTurnCount();
        if (turnCount == TOTAL_TURNS) {
            //TODO: game ends
        }
        listener.gameInfoUpdated(turnCount, sharkSquareCount, eagleSquareCount);
    }

    public void trackPlayerTurn() {

    }

    public void updateSquareCount(int sharkSquareCount, int eagleSquareCount) {
        this.sharkSquareCount = sharkSquareCount;
        this.eagleSquareCount = eagleSquareCount;
    }

    public void endGame() {

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

    public void incrementTurnCount() {
        turnCount++;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public double getSharkScore() {
        return sharkSquareCount / TOTAL_SQUARES;
    }

    public double getEagleScore() {
        return eagleSquareCount / TOTAL_SQUARES;
    }

    public void setPlayers(String sharkPlayerName, String eaglePlayerName) {
        this.sharkPlayer = new Player(sharkPlayerName);
        this.eaglePlayer = new Player(eaglePlayerName);
    }

    public GameModelEventListener getListener() {
        return listener;
    }

    public void setListener(GameModelEventListener listener) {
        this.listener = listener;
    }

    public void initGame() {
        this.sharkSquareCount = 0;
        this.eagleSquareCount = 0;
        this.turnCount = 1;

        listener.gameInitialised(sharkPlayer.getPlayerName(),
                                 eaglePlayer.getPlayerName(),
                                 turnCount, TOTAL_TURNS, getSharkScore(), getEagleScore());
    }
}
