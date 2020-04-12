package main.java.model.move;

import java.util.List;

// UPDATE: The goblin shark can go left, diagonal left, diagonal right, right,
// it can not go up and down and can only go one step.
// it still cover 8 square around it, but go diagonal it can take 5 squares each time

public class GoblinSharkMove extends Move {

    public GoblinSharkMove(int startRow, int startCol, int squaresMoved, String direction) {
        super(startRow, startCol, squaresMoved, direction);
    }


    private void checkAndAttach(){
        // generate all info base on the direction
        switch (direction) {
            case "down":
                break;

            case "up":
                break;

            case "left":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintGeneration();
                            break;

                        case "dest":
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol - squaresMoved;
                            break;

                        case "route":
                            // record route based on the steps and direction
                            for (int col = startCol; col >= destination[1]; col--) {
                                route.add(new int[]{startRow, col});
                            }
                            break;
                    }
                }
                break;
            case "right":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintGeneration();
                            break;

                        case "dest":
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol + squaresMoved;
                            break;

                        case "route":
                            // record route based on the steps and direction
                            for (int col = startCol; col <= destination[0]; col++) {
                                route.add(new int[]{startRow, col});
                            }
                            break;
                    }
                }
                break;

            case "diagonal up left":
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintGeneration();
                            break;

                        case "dest":
                            destination[0] = startRow - squaresMoved;
                            destination[1] = startCol - squaresMoved;
                            break;

                        case "route":
                            int col = startCol;
                            for (int row = startRow; row >= destination[0]; row--, col--) {
                                route.add(new int[]{row, col});
                            }
                            break;
                    }
                }
                break;
            case "diagonal up right":
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintGeneration();
                            break;

                        case "dest":
                            destination[0] = startRow - squaresMoved;
                            destination[1] = startCol + squaresMoved;
                            break;

                        case "route":
                            int col = startCol;
                            for (int row = startRow; row >= destination[0]; row--, col++) {
                                route.add(new int[]{row, col});
                            }
                            break;
                    }
                }
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
}
