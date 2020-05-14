package main.java.model;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.model.piece.Eagle;
import main.java.model.piece.Piece;
import main.java.model.piece.Shark;
import main.java.model.player.EaglePlayer;
import main.java.model.player.Player;
import main.java.model.player.SharkPlayer;
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
                             int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore,
                             int sharkPowerMoves, int eaglePowerMoves);

        void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore, int sharkPowerMoves,
                             int eaglePowerMoves);

        //timer functions
        void timeRemainingChanged(int timeRemaining);

        void timeRanOut();
    }

    //listener
    private GameModelEventListener listener;


    private static final double WIN_PERCENTAGE = 0.6;
    private int totalTurns;
    private double totalSquares;
    private int turnTime;
    private Player sharkPlayer;
    private Player eaglePlayer;
    private double sharkSquareCount;
    private double eagleSquareCount;
    private int turnCount;

    private Timeline timer;
    private int timeRemaining;

    public Game(GameModelEventListener listener) {
        this.listener = listener;
        this.sharkSquareCount = 0;
        this.eagleSquareCount = 0;
        this.turnCount = 0;
    }

    /**
     * This overloaded method initialises a custom game where the player can set the time limit
     * for each turn as
     *
     * @param sharkPlayerName
     *         name of the shark player
     * @param eaglePlayerName
     *         name of the eagle player
     * @param timeLimit
     *         amount of time per turn
     */
    public void initialiseGame(String sharkPlayerName, String eaglePlayerName, int timeLimit, int turnCount, int rows,
                               int cols) {

        int numOfPowerMoves = (turnCount / 2) / 4;

        this.sharkPlayer = new SharkPlayer(sharkPlayerName, numOfPowerMoves);
        this.eaglePlayer = new EaglePlayer(eaglePlayerName, numOfPowerMoves);
        this.turnTime = timeLimit;
        this.totalSquares = rows * cols;
        this.totalTurns = turnCount;

        listener.gameInitialised(sharkPlayerName,
                                 eaglePlayerName,
                                 turnCount, totalTurns, turnTime, getSharkScore(), getEagleScore(),
                                 sharkPlayer.getRemainingPowerMoves(), eaglePlayer.getRemainingPowerMoves());
    }

    /**
     * Check to see if the game has finished, if not continue to the next players turn
     *
     * @ensure 1. if game is not over, turn is incremented by 1
     * 2. otherwise, the game is ended
     */
    public void nextTurn() {
        if (turnCount >= totalTurns || isWinner()) {
            stopTimer();
            endGame();
        } else {
            incrementTurnCount();
            startTimer();
            listener.gameInfoUpdated(turnCount, getSharkScore(), getEagleScore(), sharkPlayer.getRemainingPowerMoves(),
                                     eaglePlayer.getRemainingPowerMoves());
        }
    }

    /**
     * Checks to see if a player has met the required win conditions before the final turn
     *
     * @return true if a player has captured more than 60% of the territory, other wise return false
     */
    public boolean isWinner() {
        if (sharkSquareCount / totalSquares > WIN_PERCENTAGE || eagleSquareCount / totalSquares > WIN_PERCENTAGE) {
            //sharks or eagle win
            return true;
        }
        return false;
    }

    /**
     * Check to see which player has the most number of squares captured to declare them the winner.
     * Then show the end of game stage and announce the winner.
     */
    public void endGame() {
        if (sharkSquareCount > eagleSquareCount) {
            //sharks win the game
            SceneManager.getInstance().showEndGame("The Sharks Win!", getSharkScore(), getEagleScore());
        } else if (eagleSquareCount > sharkSquareCount) {
            //eagles win the game
            SceneManager.getInstance().showEndGame("The Eagles Win!", getSharkScore(), getEagleScore());
        } else {
            //its a draw
            SceneManager.getInstance().showEndGame("It was a draw!", getSharkScore(), getEagleScore());
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
        return sharkSquareCount / totalSquares;
    }

    /**
     * @return the percentage of territory the eagles control
     */
    public double getEagleScore() {
        return eagleSquareCount / totalSquares;
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

    public void updateRemainingPowerMoves() {
        Player currentPlayer = getCurrentPlayer();
        System.out.println(currentPlayer.getPlayerName());
        currentPlayer.setRemainingPowerMoves(currentPlayer.getRemainingPowerMoves() - 1);
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
}
