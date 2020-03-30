package main.java.model;

/**
 * @author WeiYi Yu
 * @date 2020-03-23
 */
public class Board {

    public static final int ROW = 15;
    public static final int COLUMN = 10;

    private Square[] squares = new Square[ROW * ROW];

    public Board() {
        initSquare();
    }

    private void initSquare() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < ROW; col++) {
                Square square = new Square();
                squares[row * ROW + col] = square;
            }
        }
    }

    public Square getSquareAt(int row, int col) {
        return squares[row * ROW + col];
    }

}
