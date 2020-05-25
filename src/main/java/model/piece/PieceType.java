package main.java.model.piece;

import main.java.ResPath;
import main.java.model.Player;
import main.java.model.board.Board;
import main.java.model.move.*;
import main.java.util.MoveValidator;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-19
 */
public enum PieceType {
    BALD_EAGLE(new BaldEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_BALD_EAGLE),
    GOLDEN_EAGLE(new GoldenEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_GOLDEN_EAGLE),
    HARPY_EAGLE(new HarpyEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_HARPY_EAGLE),
    GOBLIN_SHARK(new GoblinSharkMoveFactory(), Player.SHARK, ResPath.PIECE_GOBLIN_SHARK),
    HAMMERHEAD(new HammerheadMoveFactory(), Player.SHARK, ResPath.PIECE_HAMMERHEAD),
    SAW_SHARK(new SawSharkMoveFactory(), Player.SHARK, ResPath.PIECE_SAW_SHARK);

    private Player team;
    private MoveFactory moveFactory;
    private String imgPath;

    PieceType(MoveFactory moveFactory, Player team, String imgPath) {
        this.team = team;
        this.moveFactory = moveFactory;
        this.imgPath = imgPath;
    }

    public Piece createPiece(int row, int col) {
        return new Piece(this, row, col);
    }

    public List<Move> availableMoves(int startRow, int startCol, boolean isPowered, Piece movingPiece, Board board) {
        List<Move> moves = moveFactory.generateMoves(startRow, startCol, isPowered, board);
        return MoveValidator.validateMoves(moves, movingPiece, board);
    }

    public String getImgPath() {
        return imgPath;
    }

    public boolean isBelongTo(Player player) {
        return team == player;
    }

    public Player getTeam() {
        return team;
    }
}

