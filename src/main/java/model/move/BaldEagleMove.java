package main.java.model.move;

import main.java.model.move.movements.MoveDown;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

public class BaldEagleMove {

    private List<Move> moveList;

    public BaldEagleMove(int startRow, int startCol, int squaresMoved) {
        moveList = new ArrayList<>();

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveDown(startRow, startCol, squaresMoved)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination())));
        }
    }

    public List<Move> getMoveList() {
        return moveList;
    }
}
