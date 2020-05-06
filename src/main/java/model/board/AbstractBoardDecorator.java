package main.java.model.board;

import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public abstract class AbstractBoardDecorator
        implements Board {

    protected Board board;

    public AbstractBoardDecorator(Board board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        this.board = board;
    }

    @Override
    public void initBoard(int rows, int cols) {
        board.initBoard(rows, cols);
    }

    @Override
    public int getSharkSquareCount() {
        return board.getSharkSquareCount();
    }

    @Override
    public int getEagleSquareCount() {
        return board.getEagleSquareCount();
    }

    @Override
    public Piece getPiece(int row, int col) {
        return board.getPiece(row, col);
    }

    @Override
    public void setChosenPiece(Piece chosenPiece) {
        board.setChosenPiece(chosenPiece);
    }

    @Override
    public Piece getChosenPiece() {
        return board.getChosenPiece();
    }

    @Override
    public void updatePiecePosition(Move move, Piece piece) {
        board.updatePiecePosition(move, piece);
    }

    @Override
    public void updateTerritory(Move move, Player player) {
        board.updateTerritory(move, player);
    }

    @Override
    public List<int[]> getSharksPositions() {
        return board.getSharksPositions();
    }

    @Override
    public List<Move> validatePossibleMoves(List<Move> moves) {
        return board.validatePossibleMoves(moves);
    }

    @Override
    public boolean isSquareValid(int[] position) {
        return board.isSquareValid(position);
    }

    @Override
    public int getTotalRows() {
        return board.getTotalRows();
    }

    @Override
    public int getTotalCols() {
        return board.getTotalCols();
    }
}
