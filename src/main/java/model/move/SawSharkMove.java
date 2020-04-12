package main.java.model.move;

import java.util.List;

public class SawSharkMove extends Move {

    public SawSharkMove(int startRow, int startCol, int squaresMoved, String direction) {
        super(startRow, startCol, squaresMoved, direction);
        checkAndAttach();
    }


    private void checkAndAttach(){
        // generate all info base on the direction.
        // if the destination is exceed the board
        // the move is invalid.
        switch (direction) {
            case "down":
                break;

            case "up":
                break;

            case "left":
                break;
            case "right":
                break;

            case "diagonal up left":
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            // paint triangle shape
                            for (int i = 0; i < squaresMoved; i++) {
                                for (int m = i; m > -1; m--) {
                                    // check if the square is on the board
                                    if (startRow - i >= 0 && startCol - m >= 0) {
                                        paintInfo.add(new int[]{startRow - i, startCol - m});
                                    }
                                }
                            }
                            break;

                        case "dest":
                            destination[0] = startRow - squaresMoved;
                            destination[1] = startCol - squaresMoved;
                            break;

                        case "route":
                            // the route will determine after located destination
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
                            // paint triangle shape
                            for (int i = 0; i < squaresMoved; i++) {
                                for (int m = i; m > -1; m++) {
                                    // check if the square is on the board
                                    if (startRow - i >= 0 && startCol - m >= 0) {
                                        paintInfo.add(new int[]{startRow - i, startCol + m});
                                    }
                                }
                            }
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
}
