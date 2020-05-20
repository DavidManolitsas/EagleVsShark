package main.java.model.piece;

import main.java.ResPath;
import main.java.model.Player;
import main.java.model.board.Board;
import main.java.model.move.*;

import java.util.List;

/**
 * @author WeiYi Yu
 * @date 2020-05-19
 */
public enum PieceType {
    BALD_EAGLE(new BaldEagleMove(), Player.EAGLE, ResPath.PIECE_BALD_EAGLE),
    GOLDEN_EAGLE(new GoldenEagleMove(), Player.EAGLE, ResPath.PIECE_GOLDEN_EAGLE),
    HARPY_EAGLE(new HarpyEagleMove(), Player.EAGLE, ResPath.PIECE_HARPY_EAGLE),
    GOBLIN_SHARK(new GoblinSharkMove(), Player.SHARK, ResPath.PIECE_GOBLIN_SHARK),
    HAMMERHEAD(new HammerheadMove(), Player.SHARK, ResPath.PIECE_HAMMERHEAD),
    SAW_SHARK(new SawSharkMove(), Player.SHARK, ResPath.PIECE_SAW_SHARK);

    private Player team;
    private PieceMove moveGenerator;
    private String imgPath;

    PieceType(PieceMove moveGenerator, Player team, String imgPath) {
        this.team = team;
        this.moveGenerator = moveGenerator;
        this.imgPath = imgPath;
    }

    public Piece createPiece(int row, int col) {
        return new Piece(this, row, col);
    }

    public List<Move> availableMoves(int startRow, int startCol, boolean isPowered, Board board) {
        List<Move> moves = moveGenerator.generateMoves(startRow, startCol, isPowered, board);
        return board.validatePossibleMoves(moves);
    }

    public String getImgPath() {
        return imgPath;
    }

    public boolean isBelongTo(Player player) {
        return team == player;
    }
}

