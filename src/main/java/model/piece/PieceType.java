package main.java.model.piece;

import java.util.List;

import main.java.ResPath;
import main.java.model.Player;
import main.java.model.board.Board;
import main.java.model.move.BaldEagleMoveFactory;
import main.java.model.move.GoblinSharkMoveFactory;
import main.java.model.move.GoldenEagleMoveFactory;
import main.java.model.move.HammerheadMoveFactory;
import main.java.model.move.HarpyEagleMoveFactory;
import main.java.model.move.Move;
import main.java.model.move.MoveFactory;
import main.java.model.move.SawSharkMoveFactory;
import main.java.util.MoveValidator;

/**
 * @author WeiYi Yu
 * @date 2020-05-19
 */
public enum PieceType {
    BALD_EAGLE(new BaldEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_BALD_EAGLE, "Bald Eagle"),
    GOLDEN_EAGLE(new GoldenEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_GOLDEN_EAGLE, "Golden Eagle"),
    HARPY_EAGLE(new HarpyEagleMoveFactory(), Player.EAGLE, ResPath.PIECE_HARPY_EAGLE, "Harpy Eagle"),
    GOBLIN_SHARK(new GoblinSharkMoveFactory(), Player.SHARK, ResPath.PIECE_GOBLIN_SHARK, "Goblin Shark"),
    HAMMERHEAD(new HammerheadMoveFactory(), Player.SHARK, ResPath.PIECE_HAMMERHEAD, "Hammerhead"),
    SAW_SHARK(new SawSharkMoveFactory(), Player.SHARK, ResPath.PIECE_SAW_SHARK, "Saw Shark");

    private Player team;
    private MoveFactory moveFactory;
    private String imgPath;
    private String name;

    PieceType(MoveFactory moveFactory, Player team, String imgPath, String name) {
        this.team = team;
        this.moveFactory = moveFactory;
        this.imgPath = imgPath;
        this.name = name;
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

    public String getName() {
        return name;
    }
}

