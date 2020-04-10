package main.java.model.move;

import java.util.List;

public class HammerheadMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int squaresMoved;
    private String direction;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public HammerheadMove(int startRow, int startCol, int squaresMoved, String direction) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.squaresMoved = squaresMoved;
        this.direction = direction;
        checkAndAttach();
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
                    // execute each command
                    for (String command : COMMANDS) {
                        switch (command) {
                            case "paint":
                                paintGeneration();
                                break;
                            case "dest":
                                // record destination coordinate base on the steps and direction
                                destRow = startRow - squaresMoved;
                                destCol = startCol;
                                checkValid();
                                break;
                            case "route":
                                // record route based on the steps and direction
                                for (int row = startRow; row <= destRow; --row) {
                                    route.add(new Integer[]{row, startCol});
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

    // record the coordinates for shape "T" paint
    private void paintGeneration(){
        for(int row = destRow; row >= destRow - 3; row--){
            if(row >= 0){
                paintInfo.add(new Integer[]{row, destCol});
            }
        }
        if (destCol + 1 <= 9){
            paintInfo.add(new Integer[]{destRow - 3, destCol + 1});
        }
        if (destCol - 1 >= 0){
            paintInfo.add(new Integer[]{destRow - 3, destCol - 1});
        }
    }
}
