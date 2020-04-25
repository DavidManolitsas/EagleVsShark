package main.java.model;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.model.piece.Eagle;
import main.java.model.piece.Piece;
import main.java.model.piece.Shark;
import main.java.util.SceneManager;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 * @invariant 1. turnCount >= 1
 * 2. eagleSquareCount >= 0 && sharkSquareCount >= 0
 */
public class Game {

    public interface GameModelEventListener {
        void gameInitialised(String eaglePlayerName, String sharkPlayerName,
                             int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore);

        void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore);

        //timer functions
        void timeRemainingChanged(int timeRemaining);

        void timeRanOut();
    }

    //listener
    private GameModelEventListener listener;

    private static final double TOTAL_SQUARES = 150;
    private static final int TOTAL_TURNS = 40;
    private static final double WIN_PERCENTAGE = 0.6;
    private int turnTime;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private double sharkSquareCount;
    private double eagleSquareCount;
    private int turnCount;

    private Timeline timer;
    private int timeRemaining;

    /**
     * Singleton Game constructor
     */
    public Game(GameModelEventListener listener) {
        this.listener = listener;
        this.sharkSquareCount = 0;
        this.eagleSquareCount = 0;
        this.turnCount = 0;
    }

    public void initialiseGame(String sharkPlayerName, String eaglePlayerName, int turnTime) {
        initPlayers(sharkPlayerName, eaglePlayerName);
        setTurnTime(turnTime);

        listener.gameInitialised(sharkPlayerName,
                                 eaglePlayerName,
                                 turnCount, TOTAL_TURNS, turnTime, getSharkScore(), getEagleScore());
    }

    /**
     * Check to see if the game has finished, if not continue to the next players turn
     *
     * @ensure 1. if game is not over, turn is incremented by 1
     * 2. otherwise, the game is ended
     */
    public void nextTurn() {
        if (turnCount >= TOTAL_TURNS || isWinner()) {
            stopTimer();
            endGame();
        } else {
            incrementTurnCount();
            startTimer();
            listener.gameInfoUpdated(turnCount, getSharkScore(), getEagleScore());
        }
    }

    /**
     * Checks to see if a player has met the required win conditions before the final turn
     *
     * @return true if a player has captured more than 60% of the territory, other wise return false
     */
    public boolean isWinner() {
        if (sharkSquareCount / TOTAL_SQUARES > WIN_PERCENTAGE || eagleSquareCount / TOTAL_SQUARES > WIN_PERCENTAGE) {
            //sharks or eagle win
            return true;
        }
        return false;
    }

    /**
     * Reset the square count and turn count
     */
    public void resetGame() {
        updateSquareCount(0, 0);
        setTurnCount(1);
    }

    /**
     * Check to see which player has the most number of squares captured to declare them the winner.
     * Then show the end of game stage and announce the winner.
     */
    public void endGame() {
        resetGame();
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
     * Updates the number of squares each player controls
     *
     * @param sharkSquareCount
     *         number of squares the shark player controls
     * @param eagleSquareCount
     *         number of squares the eagle player controls
     *
     * @require 1. int sharkSquareCount >= 0 && sharkSquareCount >= 0
     * @ensure 1. the sharkSquareCount and eagleSquareCount are updated
     */
    public void updateSquareCount(int sharkSquareCount, int eagleSquareCount) {
        this.sharkSquareCount = sharkSquareCount;
        this.eagleSquareCount = eagleSquareCount;
    }

    /**
     * @return the percentage of territory the sharks control
     */
    public double getSharkScore() {
        return sharkSquareCount / TOTAL_SQUARES;
    }

    /**
     * @return the percentage of territory the eagles control
     */
    public double getEagleScore() {
        return eagleSquareCount / TOTAL_SQUARES;
    }

    /**
     * On a given turn check to see if a piece belongs to the player of whose turn it is
     *
     * @param piece
     *         The chosen piece that the player has selected
     *
     * @return true if the piece belongs to the player whose turn it is, otherwise return false
     *
     * @require 1. piece != null
     * @ensure 1. Determines whether the piece parsed in belongs to the current player or not
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

    /**
     * Returns the current player
     *
     * @return the player whose turn it is
     *
     * @require 1. eaglePlayer != null && sharkPlayer != null
     * @ensure 1. the returned Player != null
     */
    public Player getCurrentPlayer() {
        if (turnCount % 2 == 0) {
            return eaglePlayer;
        } else {
            return sharkPlayer;
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                //decrement time
                timeRemaining--;

                listener.timeRemainingChanged(timeRemaining);

                if (timeRemaining <= 0) {
                    timer.stop();
                    listener.timeRanOut();
                }
            }));
        }
        timer.stop();

        timeRemaining = turnTime;
        timer.setCycleCount(turnTime);
        timer.playFromStart();

        listener.timeRemainingChanged(timeRemaining);
    }

    public void pauseTimer() {
        timer.pause();
    }

    public void resumeTimer() {
        timer.play();
    }

    public void stopTimer() {
        timer.stop();
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

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    private void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    private void initPlayers(String sharkPlayerName, String eaglePlayerName) {
        this.sharkPlayer = new SharkPlayer(sharkPlayerName);
        this.eaglePlayer = new EaglePlayer(eaglePlayerName);
    }
}
