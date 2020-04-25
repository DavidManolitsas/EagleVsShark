package main.java.model.piece;

import main.java.model.move.Move;
import main.java.model.move.MoveBuilder;
import main.java.model.move.shape.PaintShapeType;

import java.util.List;


public class Hammerhead
        extends Shark {

    public Hammerhead(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of HammerheadMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new MoveBuilder(startRow, startCol, PaintShapeType.TSHAPE)
                .addAvailableStep(1)
                .addMoveUp()
                .addMoveDiagonalLeft()
                .addMoveDiagonalRight()
                .build();
    }

}
