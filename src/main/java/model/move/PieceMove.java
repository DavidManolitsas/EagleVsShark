package main.java.model.move;

import main.java.model.board.Board;

import java.util.List;

public abstract class PieceMove {

    public abstract List<Move> generateMoves(int startRow, int startCol, boolean isPowered,
                                             Board board);

}
