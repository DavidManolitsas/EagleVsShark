package main.java.model;


import main.java.model.piece.Eagle;
import main.java.model.piece.Piece;
import main.java.model.piece.Shark;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 */
public class Game {

    public interface GameModelEventListener {
        void gameInitialised(String eaglePlayerName, String sharkPlayerName,
                             int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore);

        void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore);

        //timer functions
        void stopTimer();

        void startTimer();

        void deleteTimer();
    }

    //listener
    private GameModelEventListener listener;

    private static final double TOTAL_SQUARES = 150;
    private static final int TOTAL_TURNS = 40;
    private int turnTime;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private double sharkSquareCount;
    private double eagleSquareCount;
    private int turnCount;

    private static final Game INSTANCE = new Game();

    public static Game getInstance() {
        return INSTANCE;
    }

    /**
     *
     */
    public void nextTurn() {
        if (turnCount >= TOTAL_TURNS) {
            endGame();
        } else if (isWinner()) {
            endGame();
        } else {
            incrementTurnCount();
            listener.gameInfoUpdated(turnCount, getSharkScore(), getEagleScore());
        }
    }


    public boolean isWinner() {
        if (sharkSquareCount / TOTAL_SQUARES > 0.6) {
            //sharks win
            return true;
        } else if (eagleSquareCount / TOTAL_SQUARES > 0.6) {
            //eagles win
            return true;
        }
        return false;
    }

    public void endGame() {
        if (sharkSquareCount > eagleSquareCount) {
            //sharks win the game
            SceneManager.getInstance().showEndGame("The Sharks Win!");
        } else if (eagleSquareCount > sharkSquareCount) {
            //eagles win the game
            SceneManager.getInstance().showEndGame("The Eagles Win!");
        } else {
            //its a draw
            SceneManager.getInstance().showEndGame("It was a draw!");
        }
    }

    /**
     * @param sharkSquareCount
     * @param eagleSquareCount
     */
    public void updateSquareCount(int sharkSquareCount, int eagleSquareCount) {
        this.sharkSquareCount = sharkSquareCount;
        this.eagleSquareCount = eagleSquareCount;
    }

    /**
     *
     * @param piece
     * @return
     */
    public boolean pieceBelongsToPlayer(Piece piece) {
        if (turnCount % 2 == 0) {
            //eagles turn
            return piece instanceof Eagle;
        } else {
            //sharks turn
            return piece instanceof Shark;
        }
    }

    public Player getCurrentPlayer() {
        if (turnCount % 2 == 0) {
            return eaglePlayer;
        } else {
            return sharkPlayer;
        }
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

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    public double getSharkScore() {
        return sharkSquareCount / TOTAL_SQUARES;
    }

    public double getEagleScore() {
        return eagleSquareCount / TOTAL_SQUARES;
    }

    public void setPlayers(String sharkPlayerName, String eaglePlayerName) {
        this.sharkPlayer = new SharkPlayer(sharkPlayerName);
        this.eaglePlayer = new EaglePlayer(eaglePlayerName);
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
                                 turnCount, TOTAL_TURNS, turnTime, getSharkScore(), getEagleScore());
    }
}
