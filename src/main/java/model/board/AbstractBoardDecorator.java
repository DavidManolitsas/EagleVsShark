package main.java.model.board;

import java.util.List;

import main.java.model.Square;
import main.java.model.board.BoardImpl.BoardModelEventListener;
import main.java.model.move.Move;
import main.java.model.piece.Piece;
import main.java.model.player.Player;

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
    public void initBoard(int rows, int cols, int sharks, int eagles) {
        board.initBoard(rows, cols, sharks, eagles);
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

    @Override
    public BoardModelEventListener getEventListener() {
        return board.getEventListener();
    }

    @Override
    public Square[] getTopRow() {
        return board.getTopRow();
    }

    @Override
    public Square[] getBottomRow() {
        return board.getBottomRow();
    }

    @Override
    public Square getSquareAt(int row, int col) {
        return board.getSquareAt(row, col);
    }

    @Override
    public Square getSelectedSquare() {
        return board.getSelectedSquare();
    }

    @Override
    public void setSelectedSquare(Square selectedSquare) {
        board.setSelectedSquare(selectedSquare);
    }
}
