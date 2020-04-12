package main.java.model.move;

import java.util.List;

public class HammerheadMove extends Move {


    public HammerheadMove(int startRow, int startCol, int squaresMoved, String direction) {
        super(startRow, startCol, squaresMoved, direction);
        checkAndAttach();
    }

    private void checkAndAttach(){
        // generate all info base on the direction
        switch (direction) {
            case "down":
                break;

            case "up":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintGeneration();
                            break;
                        case "dest":
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow - squaresMoved;
                            destination[1] = startCol;
                            break;
                        case "route":
                            // record route based on the steps and direction
                            for (int row = startRow; row <= destination[0]; row--) {
                                route.add(new int[]{row, startCol});
                            }
                            break;
                    }
                }
                break;

            case "left":
                break;

            case "right":
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


    // record the coordinates for shape "T" paint
    private void paintGeneration(){
        for(int row = destination[0]; row >= destination[0] - 3; row--){
            paintInfo.add(new int[]{row, destination[1]});
        }
        paintInfo.add(new int[]{destination[0] - 3, destination[1] + 1});
        paintInfo.add(new int[]{destination[0] - 3, destination[1] - 1});
    }
}
