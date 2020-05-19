package main.java.model.move;

import main.java.model.board.Board;
import main.java.model.move.movements.MoveDiagonalLeft;
import main.java.model.move.movements.MoveDiagonalRight;
import main.java.model.move.shape.TriangleShape;

import java.util.ArrayList;
import java.util.List;

/*
 * Precondition: none
 *              Starting point of the piece
 * Postcondition: a list of move
 */
public class SawSharkMove
        extends PieceMove {

    @Override
    public List<Move> generateMoves(int startRow, int startCol, boolean isPowered, Board board) {
        int squaresMoved = 2;
        int width = 3;
        List<Move> moveList = new ArrayList<>();
        if (isPowered) {
            squaresMoved += 1;
            width += 1;
        }
        moveList.add(new Move(new MoveDiagonalLeft(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_LEFT)));
        moveList.add(new Move(new MoveDiagonalRight(startRow, startCol, squaresMoved),
                              new TriangleShape(startRow, startCol, width, TriangleShape.DIRECTION_RIGHT)));

        return moveList;
    }
}
