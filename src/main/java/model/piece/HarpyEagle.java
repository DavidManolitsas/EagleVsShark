package main.java.model.piece;

import main.java.model.move.Move;
import main.java.model.move.MoveBuilder;
import main.java.model.move.shape.PaintShapeType;

import java.util.List;

public class HarpyEagle
        extends Eagle {

    public HarpyEagle(int startRow, int startCol) {
        super(startRow, startCol);
    }

    /**
     * Ensures:
     * moveList of HarpyEagleMove objects
     */
    @Override
    public List<Move> getAllMoves(int startRow, int startCol) {
        return new MoveBuilder(startRow, startCol, PaintShapeType.VSHAPE)
                .addAvailableStep(3)
                .addMoveUp()
                .addMoveDown()
                .addMoveLeft()
                .addMoveRight()
                .build();
    }

}
