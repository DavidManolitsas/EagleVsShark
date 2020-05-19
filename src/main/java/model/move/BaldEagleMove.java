package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.MoveDown;
import main.java.model.move.movements.MoveLeft;
import main.java.model.move.movements.MoveRight;
import main.java.model.move.movements.Movements;
import main.java.model.move.shape.SquareShape;

import java.util.ArrayList;
import java.util.List;

public class BaldEagleMove
        extends PieceMove {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 1;
        List<Move> moveList = new ArrayList<>();
        if (isPowered) {
            squaresMoved = squaresMoved * 3;
        }

        Movements[] movements = {
                new MoveLeft(startRow, startCol, squaresMoved),
                new MoveLeft(startRow, startCol, squaresMoved + 1),
                new MoveLeft(startRow, startCol, squaresMoved + 2),
                new MoveRight(startRow, startCol, squaresMoved),
                new MoveRight(startRow, startCol, squaresMoved + 1),
                new MoveRight(startRow, startCol, squaresMoved + 2),
                new MoveDown(startRow, startCol, squaresMoved),
                new MoveDown(startRow, startCol, squaresMoved + 1),
                new MoveDown(startRow, startCol, squaresMoved + 2)
        };

        for (Movements movement : movements) {
            moveList.add(new Move(movement, new SquareShape(movement.getDestination(), false)));
        }
        return moveList;
    }
}
