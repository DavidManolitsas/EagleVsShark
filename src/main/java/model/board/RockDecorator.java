package main.java.model.board;

import main.java.model.move.Move;

import java.util.*;

/**
 * @author WeiYi Yu
 * @date 2020-05-06
 */
public class RockDecorator
        extends AbstractBoardDecorator {

    private static final int ROCKS_NUM = 10;

    private Map<String, int[]> rockSquareMap;

    public RockDecorator(Board board) {
        super(board);
        rockSquareMap = new HashMap<>();
    }

    @Override
    public void initBoard(int rows, int cols) {
        super.initBoard(rows, cols);
        generateObstacles();
        getEventListener().onRocksAdded(rockSquareMap.values());
    }

    @Override
    public List<Move> validatePossibleMoves(List<Move> moves) {
        List<Move> validatedMoves = super.validatePossibleMoves(moves);

        validatedMoves.removeIf(move -> !isSquareValid(move.getFinalPosition()));
        return validatedMoves;
    }

    @Override
    public boolean isSquareValid(int[] position) {
        return super.isSquareValid(position) && !rockSquareMap.containsKey(Arrays.toString(position));
    }

    private void generateObstacles() {
        Random random = new Random();
        while (rockSquareMap.size() < ROCKS_NUM) {
            int row = random.nextInt(getTotalRows());
            int col = random.nextInt(getTotalCols());
            int[] position = new int[] {row, col};
            if (isSquareValid(position)) {
                rockSquareMap.put(Arrays.toString(position), position);
            }
        }
    }
}
