package main.java.util;

import main.java.model.Player;
import main.java.model.Square;
import main.java.model.board.Board;
import main.java.model.move.Move;
import main.java.model.piece.Piece;

import java.util.List;
import java.util.Map;

/**
 * @author WeiYi Yu
 * @date 2020-05-25
 */
public class SimpleAI {

    public static Move getBestMove(Board board) {
        Move finalMove = null;
        Piece chosenPiece = null;
        int maxScore = 0;
        Map<Piece, Square> pieceSquareMap = board.getPieceSquareMap();

        for (Piece piece : pieceSquareMap.keySet()) {
            if (piece.isBelongTo(Player.SHARK)) {
                continue;
            }
            Square pieceSquare = pieceSquareMap.get(piece);

            List<Move> allPossibleMoves = piece.availableMoves(pieceSquare.getRow(),
                                                               pieceSquare.getCol(),
                                                               false,
                                                               board);

            for (Move move : allPossibleMoves) {
                Square[][] squares = getCloneSquares(board.getSquares(), board.getTotalRows(), board.getTotalCols());

                for (int[] position : move.getPaintShape().getPaintInfo()) {
                    Square square = squares[position[0]][position[1]];
                    square.setOccupiedPlayer(Player.EAGLE);
                }

                int count = getPlayerTerritoryCount(squares, Player.EAGLE);

                if (count > maxScore) {
                    maxScore = count;
                    finalMove = move;
                    chosenPiece = piece;
                }
            }
        }
        board.onPieceSelected(chosenPiece, finalMove.getFinalPosition()[0], finalMove.getFinalPosition()[1]);
        return finalMove;
    }

    private static int getPlayerTerritoryCount(Square[][] squares, Player player) {
        int count = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if (squares[row][col].getOccupiedPlayer() == player) {
                    count++;
                }
            }
        }
        return count;
    }

    private static Square[][] getCloneSquares(Square[][] originalSquares, int totalRows, int totalCols) {
        Square[][] newSquares = new Square[totalRows][totalCols];
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                Square square = new Square(row, col);
                square.setPiece(originalSquares[row][col].getPiece());
                square.setOccupiedPlayer(originalSquares[row][col].getOccupiedPlayer());
                newSquares[row][col] = square;
            }
        }
        return newSquares;
    }
}
