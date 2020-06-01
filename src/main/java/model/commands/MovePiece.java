package main.java.model.commands;

import main.java.model.Player;
import main.java.model.Square;
import main.java.model.board.Board;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-06-02
 */
public class MovePiece
        implements Command {

    private Board board;
    private Move move;
    private Piece piece;
    private Player player;
    private AttackPieceInfo attackPieceInfo;

    private List<Player> occupiedPlayerHistory;

    public MovePiece(Board board, Move move, Piece piece, Player player) {
        this.board = board;
        this.move = move;
        this.piece = piece;
        this.player = player;
    }

    @Override
    public void execute() {
        // update power move count
        if (move.isPowered()) {
            player.setRemainingPowerMoves(player.getRemainingPowerMoves() - 1);
        }

        saveSquareOccupiedHistory();
        attackPieceInfo = board.onMoveButtonClicked(move, player);
    }

    @Override
    public void undo() {
        if (move.isPowered()) {
            player.setRemainingPowerMoves(player.getRemainingPowerMoves() + 1);
        }

        board.undoMove(move, piece, player, occupiedPlayerHistory, attackPieceInfo);
    }


    private void saveSquareOccupiedHistory() {
        occupiedPlayerHistory = new ArrayList<>();
        for (int[] position : move.getPaintShape().getPaintInfo()) {
            Square square = board.getSquareAt(position[0], position[1]);
            occupiedPlayerHistory.add(square.getOccupiedPlayer());
        }
    }
}
