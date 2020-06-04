package main.java.view.board;

import javafx.scene.layout.StackPane;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.view.board.BoardView.BoardViewEventListener;

/**
 * @author WeiYi Yu
 * @date 2020-06-04
 */
public interface BoardViewI
        extends BoardModelEventListener {
    StackPane getSquareAt(int row, int col);

    void setBoardViewEventListener(BoardViewEventListener boardViewEventListener);

    void showMovePreview(Move move);

    void removeMovePreview();
}
