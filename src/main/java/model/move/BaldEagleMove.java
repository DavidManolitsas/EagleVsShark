package main.java.model.move;

import java.util.List;

public class BaldEagleMove extends Move {

    public BaldEagleMove(int startRow, int startCol, int squaresMoved, String direction) {
        super(startRow, startCol, squaresMoved, direction);
        checkAndAttach();
    }

    private void checkAndAttach(){
        // generate all info base on the direction
        switch (direction) {
            // forward direction for eagle
            case "down":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case PAINT:
                            paintGeneration();
                            break;

                        case DEST:
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow + squaresMoved;
                            destination[1] = startCol;
                            break;

                        case ROUTE:
                            recordRoute();
                            break;
                    }
                }
                break;

            case "up":
                break;

            case "left":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case PAINT:
                            paintGeneration();
                            break;

                        case DEST:
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol - squaresMoved;
                            break;

                        case ROUTE:
                            recordRoute();
                            break;
                    }
                }
                break;
            case "right":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case PAINT:
                            paintGeneration();
                            break;

                        case DEST:
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol + squaresMoved;
                            break;

                        case ROUTE:
                            recordRoute();
                            break;
                    }
                }
                break;

            case "diagonal up left":
                break;

            case "diagonal up right":
                break;
        }
    }

    // record the coordinates for the squares around the piece
    private void paintGeneration(){
        // paint the three squares at the top of the piece
        for (int col = destination[1] - 1; col <= destination[1] + 1; col++) {
            paintInfo.add(new int[]{destination[0] - 1, col});
        }
        // paint the three squares at the bottom of the piece
        for (int col = destination[1] - 1; col <= destination[1] + 1; col++) {
            paintInfo.add(new int[]{destination[0] + 1, col});
        }
        // paint the two squares on the left and right of the piece
        paintInfo.add(new int[]{destination[0], destination[1] - 1});
        paintInfo.add(new int[]{destination[0], destination[1] + 1});
    }

    private void recordRoute(){
        route.add(new int[]{startRow, startCol});
        route.add(destination);
    }
}
