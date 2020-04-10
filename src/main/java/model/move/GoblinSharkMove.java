package main.java.model.move;

import java.util.List;

// UPDATE: The goblin shark can go left, diagonal left, diagonal right, right,
// it can not go up and down and can only go one step.
// it still cover 8 square around it, but go diagonal it can take 5 squares each time

public class GoblinSharkMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int squaresMoved;
    private String direction;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public GoblinSharkMove(int startRow, int startCol, int squaresMoved, String direction) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.squaresMoved = squaresMoved;
        this.direction = direction;
    }

    @Override
    public int[] getFinalPosition() {
        return new int[]{destRow, destCol};
    }

    @Override
    public List<Integer[]> getRoute() {
        return route;
    }

    @Override
    public List<Integer[]> getPaintInfo() {
        return paintInfo;
    }

    @Override
    public void checkValid(){
        if (destRow < 0 || destCol < 0 || destCol > 9){
            super.isValid = false;
        }
    }

    private void checkAndAttach(){
        // generate all info base on the direction
        if (super.isValid) {
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
                                destRow = startRow;
                                destCol = startCol - squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                // record route based on the steps and direction
                                for (int col = startCol; col >= destCol; --col) {
                                    route.add(new Integer[]{startRow, col});
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
                                destRow = startRow;
                                destCol = startCol + squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                // record route based on the steps and direction
                                for (int col = startCol; col <= destRow; ++col) {
                                    route.add(new Integer[]{startRow, col});
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
                                destRow = startRow - squaresMoved;
                                destCol = startCol - squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                int col = startCol;
                                for (int row = startRow; row >= destRow; --row, --col) {
                                    route.add(new Integer[]{row, col});
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
                                destRow = startRow - squaresMoved;
                                destCol = startCol + squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                int col = startCol;
                                for (int row = startRow; row >= destRow; --row, ++col) {
                                    route.add(new Integer[]{row, col});
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    }

    // record the coordinates for the squares around the piece
    private void paintGeneration(){
        // paint the three squares at the top of the piece
        if (destRow != 0){
            for(int col = destCol - 1; col <= destCol + 1; ++col){
                if(col >= 0){
                    paintInfo.add(new Integer[]{destRow - 1, col});
                }
            }
        }
        // paint the three squares at the bottom of the piece
        if (destRow != 14){
            for(int col = destCol - 1; col <= destCol + 1; ++col){
                if(col >= 0){
                    paintInfo.add(new Integer[]{destRow + 1, col});
                }
            }
        }
        // paint the two squares on the left and right of the piece
        if(destCol - 1 >= 0){
            paintInfo.add(new Integer[]{destRow, destCol - 1});
        }
        if(destCol + 1 >= 0){
            paintInfo.add(new Integer[]{destRow, destCol + 1});
        }
    }
}
