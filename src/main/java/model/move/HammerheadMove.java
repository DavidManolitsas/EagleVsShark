package main.java.model.move;

import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.movements.MoveUp;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.TShape;

import java.util.ArrayList;
import java.util.List;

public class HammerheadMove {

    private List<Move> moveList;

    public HammerheadMove(int startRow, int startCol, int squaresMoved) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveUp(startRow, startCol, squaresMoved),
                new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                new MoveDiagonalRight(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new TShape(movement.getDestination())));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
