package main.java.model;


import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.model.board.Board;
import main.java.model.board.BoardImpl;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.util.BoardHelper;
import main.java.util.SceneManager;
import main.java.util.SimpleAI;

/**
 * @author David Manolitsas
 * @project OOSD-A1
 * @date 2020-04-06
 * @invariant 1. turnCount >= 1
 * 2. eagleSquareCount >= 0 && sharkSquareCount >= 0
 */
@Invariant("turnCount >= 1 && eagleSquareCount >= 0 && sharkSquareCount >= 0")
public class Game {

    public interface GameModelEventListener {
        void gameInitialised(int turnCount, int totalTurns, int turnTime, double sharkScore, double eagleScore);

        void gameInfoUpdated(int turnCount, double sharkScore, double eagleScore);

        //timer functions
        void timeRemainingChanged(int timeRemaining);

        void timeRanOut();

        void onPieceSelected(Piece piece, List<Move> allPossibleMoves);
    }

    //listener
    private List<GameModelEventListener> listeners;

    private Board board;

    private static final double WIN_PERCENTAGE = 0.6;
    private int totalTurns;
    private double totalSquares;
    private int turnTime;
    private double sharkSquareCount;
    private double eagleSquareCount;
    private int turnCount;

    private Timeline timer;
    private int timeRemaining;

    private boolean isAiMode;

    public Game(GameBuilder gameBuilder) {
        initPlayers(gameBuilder);

        this.listeners = gameBuilder.listeners;
        this.turnTime = gameBuilder.timeLimit;
        this.totalSquares = gameBuilder.rows * gameBuilder.cols;
        this.totalTurns = gameBuilder.turnCount;
        this.isAiMode = gameBuilder.isAiMode;
        this.sharkSquareCount = 0;
        this.eagleSquareCount = 0;
        this.turnCount = 0;

        board = new BoardImpl(gameBuilder.boardListener,
                              gameBuilder.rows, gameBuilder.cols,
                              gameBuilder.sharkNums, gameBuilder.eagleNums);

        for (GameModelEventListener listener : listeners) {
            listener.gameInitialised(turnCount, totalTurns, turnTime, getSharkScore(), getEagleScore());
        }

    }

    private void initPlayers(GameBuilder gameBuilder) {
        int numOfPowerMoves = (gameBuilder.turnCount / 2) / 4;
        int numOfUndoMoves = 1;

        Player.SHARK.setName(gameBuilder.sharkPlayerName);
        Player.SHARK.setRemainingPowerMoves(numOfPowerMoves);
        Player.SHARK.setUndoMoves(numOfUndoMoves);

        Player.EAGLE.setName(gameBuilder.eaglePlayerName);
        Player.EAGLE.setRemainingPowerMoves(numOfPowerMoves);
        Player.EAGLE.setUndoMoves(numOfUndoMoves);
    }

    public void start() {
        nextTurn();
    }

    public void onSquareClicked(int row, int col, boolean isPowered) {
        Piece piece = board.getPiece(row, col);
        if (piece == null) {
            return;
        }

        if (!pieceBelongsToPlayer(piece)) {
            return;
        }

        board.onPieceSelected(piece, row, col);

        List<Move> allPossibleMoves = piece.availableMoves(row, col, isPowered, board);

        for (GameModelEventListener listener : listeners) {
            listener.onPieceSelected(piece, allPossibleMoves);
        }

    }

    public void onMoveButtonClicked(Move move) {
        // update power move count
        if (move.isPowered()) {
            updateRemainingPowerMoves();
        }

        board.onMoveButtonClicked(move, getCurrentPlayer());

        //the player moved their piece, change to next players turn
        updateSquareCount(BoardHelper.getPlayerScore(Player.SHARK, board),
                          BoardHelper.getPlayerScore(Player.EAGLE, board));
        nextTurn();
    }

    public void onPowerMoveToggled(boolean isPowered) {
        Piece chosenPiece = board.getChosenPiece();

        if (chosenPiece == null) {
            return;
        }

        int[] position = board.getPiecePosition(chosenPiece);

        List<Move> allPossibleMoves = chosenPiece.availableMoves(position[0], position[1], isPowered, board);

        for (GameModelEventListener listener : listeners) {
            listener.onPieceSelected(chosenPiece, allPossibleMoves);
        }

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

            for (GameModelEventListener listener : listeners) {
                listener.gameInfoUpdated(turnCount, getSharkScore(), getEagleScore());
            }


            // In AI mode, the Eagle will be the computer
            if (isAiMode && getCurrentPlayer() == Player.EAGLE) {
                AiMove();
            }
        }
    }

    /**
     * Checks to see if a player has met the required win conditions before the final turn
     *
     * @return true if a player has captured more than 60% of the territory, other wise return false
     */
    public boolean isWinner() {
        //sharks or eagle win
        return sharkSquareCount / totalSquares > WIN_PERCENTAGE || eagleSquareCount / totalSquares > WIN_PERCENTAGE;
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
     *
     */
    @Requires("sharkSquareCount >= 0 && sharkSquareCount >= 0")
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
    @Requires("piece != null")
    public boolean pieceBelongsToPlayer(Piece piece) {
        if (turnCount % 2 == 0) {
            //eagles turn
            return piece.isBelongTo(Player.EAGLE);
        } else {
            //sharks turn
            return piece.isBelongTo(Player.SHARK);
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
            return Player.EAGLE;
        } else {
            return Player.SHARK;
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

                for (GameModelEventListener listener : listeners) {
                    listener.timeRemainingChanged(timeRemaining);
                }


                if (timeRemaining <= 0) {
                    timer.stop();
                    board.timeRantOut();
                    for (GameModelEventListener listener : listeners) {
                        listener.timeRanOut();
                    }

                }
            }));
        }
        timer.stop();

        timeRemaining = turnTime;
        timer.setCycleCount(turnTime);
        timer.playFromStart();

        for (GameModelEventListener listener : listeners) {
            listener.timeRemainingChanged(timeRemaining);
        }

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

    public void incrementTurnCount() {
        turnCount++;
    }

    public int getTurnCount() {
        return turnCount;
    }

    private void AiMove() {
        Move move = SimpleAI.getBestMove(board);
        onMoveButtonClicked(move);
    }

    public static class GameBuilder {

        private List<GameModelEventListener> listeners;
        private BoardModelEventListener boardListener;
        private String sharkPlayerName;
        private String eaglePlayerName;
        private int timeLimit = 60;
        private int turnCount = 40;
        private int rows = 15;
        private int cols = 10;
        private int sharkNums = 3;
        private int eagleNums = 3;
        private boolean isAiMode = false;

        public GameBuilder(String sharkPlayerName, String eaglePlayerName) {
            listeners = new ArrayList<GameModelEventListener>();
            this.sharkPlayerName = sharkPlayerName;
            this.eaglePlayerName = eaglePlayerName;
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

        public GameBuilder addGameEventListener(GameModelEventListener listener) {
            listeners.add(listener);
            return this;
        }

        public GameBuilder setBoardEventListener(BoardModelEventListener listener) {
            this.boardListener = listener;
            return this;
        }

        public GameBuilder setAiMode(boolean aiMode) {
            isAiMode = aiMode;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
