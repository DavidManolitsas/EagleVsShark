package main.java.controller;

import javafx.fxml.FXML;
import main.java.model.Board;
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

    private Board board;

    @FXML
    public void initialize() {
        board = new Board(boardView);
        boardView.setBoardViewEventListener(this);
        gameInfoView.setGameInfoViewEventListener(this);
    }

    //region BoardView Event
    @Override
    public void onSquareClicked(int row, int col) {
        Piece piece = board.getPiece(row, col);

        if (piece != null) {
            board.setChosenPiece(piece);
            List<Move> allMoves = piece.getAllMoves(row, col);
            gameInfoView.showValidMoveList(allMoves);
        }

        // Test code
        Move move = new Move(row, col, 1, "") {
            @Override
            public List<Integer[]> getRoute() {
                List<Integer[]> list = new ArrayList<>();

                int[] rows = {row - 1, row - 2, row - 3, row - 3, row - 3};
                int[] cols = {col, col, col, col + 1, col - 1};

                for (int i = 0; i < rows.length; i++) {
                    Integer[] position = new Integer[2];
                    position[0] = rows[i];
                    position[1] = cols[i];
                    list.add(position);
                }
                return list;
            }
        };

        onMoveListItemClicked(move);

    }
    //endregion

    //region GameInfoView Event
    @Override
    public void onMoveListItemClicked(Move move) {
        boardView.showMoveRoute(move);
    }

    @Override
    public void onMoveButtonClicked(Move move) {

    }
    //endregion
}
