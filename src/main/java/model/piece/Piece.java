package main.java.model.piece;

abstract class Piece {
//    private String name;
//    private int startRow;
//    private int startCol;

    private List<Move> allMovesList;

    public Piece(int startRow, int startCol) {
//        this.startCol = startCol;
//        this.startRow = startRow;
        allMoves = new ArrayList<Move>();
    }

    public abstract List<Move> getAllMoves(int startRow, int startCol);

    public List<Move> getMoveList() {
        return allMovesList;
    }


}
