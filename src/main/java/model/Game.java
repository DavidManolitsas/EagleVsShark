package main.java.model;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.model.board.Board;
import main.java.model.board.BoardImpl;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Eagle;
import main.java.model.piece.Piece;
import main.java.model.piece.Shark;
import main.java.model.player.EaglePlayer;
import main.java.model.player.Player;
import main.java.model.player.SharkPlayer;
import main.java.util.SceneManager;

import java.util.List;

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

        void onPieceSelected(Piece piece, List<Move> allPossibleMoves);
    }

    //listener
    private GameModelEventListener listener;

    private Board board;

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

    public Game(GameBuilder gameBuilder) {
        int numOfPowerMoves = (turnCount / 2) / 4;

        this.sharkPlayer = new SharkPlayer(gameBuilder.sharkPlayerName, numOfPowerMoves);
        this.eaglePlayer = new EaglePlayer(gameBuilder.eaglePlayerName, numOfPowerMoves);
        this.turnTime = gameBuilder.timeLimit;
        this.totalSquares = gameBuilder.rows * gameBuilder.cols;
        this.totalTurns = gameBuilder.turnCount;
        this.sharkSquareCount = gameBuilder.sharkNums;
        this.eagleSquareCount = gameBuilder.eagleNums;
        this.turnCount = 0;

        board = new BoardImpl(gameBuilder.rows, gameBuilder.cols, gameBuilder.sharkNums, gameBuilder.eagleNums);
    }

    public void start() {
        board.initBoard();
        listener.gameInitialised(sharkPlayer.getPlayerName(), eaglePlayer.getPlayerName(),
                                 turnCount, totalTurns, turnTime, getSharkScore(), getEagleScore(),
                                 sharkPlayer.getRemainingPowerMoves(), eaglePlayer.getRemainingPowerMoves());

        nextTurn();
    }

    public void onSquareClicked(int row, int col, boolean isPowered) {
        Piece piece = board.getPiece(row, col);
        Piece prevChosenPiece = board.getChosenPiece();
        if (piece == null || piece == prevChosenPiece) {
            return;
        }

        if (!pieceBelongsToPlayer(piece)) {
            return;
        }

        board.onPieceSelected(piece, row, col);

        List<Move> allPossibleMoves;
        if (isPowered) {
            allPossibleMoves = piece.getAllPowerMoves(row, col);
        } else {
            allPossibleMoves = piece.getAllMoves(row, col);
        }
        allPossibleMoves = board.validatePossibleMoves(allPossibleMoves);

        listener.onPieceSelected(piece, allPossibleMoves);
    }

    public void onMoveButtonClicked(Move move) {
        // update power move count
        if (move.isPowered()) {
            updateRemainingPowerMoves();
        }

        board.onMoveButtonClicked(move, getCurrentPlayer(), turnCount);

        //the player moved their piece, change to next players turn
        updateSquareCount(board.getSharkSquareCount(), board.getEagleSquareCount());
        nextTurn();
    }

    public void onPowerMoveToggled(boolean isPowered) {
        Piece chosenPiece = board.getChosenPiece();

        if (chosenPiece == null) {
            return;
        }

        int[] position = board.getPiecePosition(chosenPiece);

        List<Move> allPossibleMoves;
        if (isPowered) {
            allPossibleMoves = chosenPiece.getAllPowerMoves(position[0], position[1]);
        } else {
            allPossibleMoves = chosenPiece.getAllMoves(position[0], position[1]);
        }
        allPossibleMoves = board.validatePossibleMoves(allPossibleMoves);

        listener.onPieceSelected(chosenPiece, allPossibleMoves);
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

    public void setListener(GameModelEventListener gameListener, BoardModelEventListener boardListener) {
        this.listener = gameListener;
        board.setListener(boardListener);
    }

    public static class GameBuilder {

        private String sharkPlayerName;
        private String eaglePlayerName;
        private int timeLimit = 60;
        private int turnCount = 40;
        private int rows = 15;
        private int cols = 10;
        private int sharkNums = 3;
        private int eagleNums = 3;

        public GameBuilder(String sharkPlayerName, String eaglePlayerName) {
            this.sharkPlayerName = sharkPlayerName;
            this.eaglePlayerName = eaglePlayerName;
        }

        public GameBuilder setSharkPlayerName(String sharkPlayerName) {
            this.sharkPlayerName = sharkPlayerName;
            return this;
        }

        public GameBuilder setEaglePlayerName(String eaglePlayerName) {
            this.eaglePlayerName = eaglePlayerName;
            return this;
        }

        public GameBuilder setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
            return this;
        }

        public GameBuilder setTurnCount(int turnCount) {
            this.turnCount = turnCount;
            return this;
        }

        public GameBuilder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public GameBuilder setCols(int cols) {
            this.cols = cols;
            return this;
        }

        public GameBuilder setSharkNums(int sharkNums) {
            this.sharkNums = sharkNums;
            return this;
        }

        public GameBuilder setEagleNums(int eagleNums) {
            this.eagleNums = eagleNums;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
