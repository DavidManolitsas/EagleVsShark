package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
import main.java.model.Game;
import main.java.model.Player;
import main.java.model.move.HammerheadMove;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.view.BoardView;
import main.java.view.BoardView.BoardViewEventListener;
import main.java.view.GameInfoView;
import main.java.view.GameInfoView.GameInfoViewEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class GameViewController
        implements BoardViewEventListener,
                   GameInfoViewEventListener {

    @FXML
    private BoardView boardView;

    @FXML
    private GameInfoView gameInfoView;

    private Game game;

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
        game = Game.getInstance();
        game.setListener(gameInfoView);
        game.initGame();

        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
    }

    //region BoardView Event
    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);
        if (piece == null) {
            return;
        }

        if (!game.pieceBelongsToPlayer(piece)) {
            return;
        }

        board.setChosenPiece(piece);
        List<Move> allMoves = piece.getMovesList(row, col);
        gameInfoView.showValidMoveList(getTestMoves(row, col));
        gameInfoView.showChosenPiece(piece);
    }
    //endregion

    private List<Move> getTestMoves(int row, int col) {
        int direction;
        if (game.getTurnCount() % 2 == 0) {
            direction = 1;
        } else {
            direction = -1;
        }

        List<Move> list = new ArrayList<>();
        list.add(new HammerheadMove(row, col, 1, "") {
            @Override
            public List<Integer[]> getRoute() {
                List<Integer[]> list = new ArrayList<>();


                int[] rows = {row, row + direction, row + (2 * direction), row + (3 * direction), row + (3 * direction),
                        row + (3 * direction)};
                int[] cols = {col, col, col, col, col + 1, col - 1};

                for (int i = 0; i < rows.length; i++) {
                    Integer[] position = new Integer[2];
                    position[0] = rows[i];
                    position[1] = cols[i];
                    list.add(position);
                }
                return list;
            }

            @Override
            public int[] getFinalPosition() {
                return new int[] {row + 3 * direction, col};
            }

            @Override
            public List<Integer[]> getPaintInfo() {
                List<Integer[]> list = new ArrayList<>();

                int[] rows =
                        {getFinalPosition()[0], getFinalPosition()[0], getFinalPosition()[0],
                                getFinalPosition()[0] + 1, getFinalPosition()[0] + 1, getFinalPosition()[0] + 1,
                                getFinalPosition()[0] - 1, getFinalPosition()[0] - 1, getFinalPosition()[0] - 1};
                int[] cols = {getFinalPosition()[1], getFinalPosition()[1] + 1, getFinalPosition()[1] - 1,
                        getFinalPosition()[1], getFinalPosition()[1] + 1, getFinalPosition()[1] - 1,
                        getFinalPosition()[1], getFinalPosition()[1] + 1, getFinalPosition()[1] - 1};
                for (int i = 0; i < rows.length; i++) {
                    Integer[] position = new Integer[2];
                    position[0] = rows[i];
                    position[1] = cols[i];
                    list.add(position);
                }
                return list;
            }
        });
        return new ArrayList<>(list);
    }

    //region GameInfoView Event
    @Override
    public void onMoveListItemClicked(Move move) {
        if (move == null) {
            return;
        }

        Move lastMove = board.getPreviewMove();
        if (lastMove != null) {
            boardView.removeMovePreview(lastMove);
        }
        boardView.showMovePreview(move);
        board.setPreviewMove(move);
    }

    @Override
    public void onMoveButtonClicked(Move move) {
        if (move == null) {
            gameInfoView.showError("No move was selected");
            return;
        }

        // Remove preview
        boardView.removeMovePreview(move);
        board.setPreviewMove(null);

        // Update board
        Piece piece = board.getChosenPiece();
        Player currentPlayer = game.getCurrentPlayer();
        board.updatePiecePosition(move, piece);
        board.updateTerritory(move, currentPlayer);
        boardView.updateTerritory(move, game.getTurnCount());

        game.nextTurn();
    }


    @Override
    public void timeRanOut() {
        gameInfoView.showTimeRanOutAlert();
    }

    @Override
    public void nextPlayerTurn() {
        game.nextTurn();
    }
    //endregion
}
