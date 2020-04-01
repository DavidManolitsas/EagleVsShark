package main.java.model.piece;

abstract class Piece {
    private List<Move> allMovesList;

    public Piece() {
        allMovesList = new ArrayList<Move>();
    }

    public abstract List<Move> getAllMoves(int startRow, int startCol);

    public List<Move> getMoveList() {
        return allMovesList;
    }


}
