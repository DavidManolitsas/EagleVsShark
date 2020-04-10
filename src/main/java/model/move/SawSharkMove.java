package main.java.model.move;

import java.util.List;

public class SawSharkMove extends Move {
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int squaresMoved;
    private String direction;
    private List<Integer[]> paintInfo;
    private List<Integer[]> route;

    public SawSharkMove(int startRow, int startCol, int squaresMoved, String direction) {
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
        // generate all info base on the direction.
        // if the destination is exceed the board
        // the move is invalid.
        if (super.isValid){
            switch(direction){
                case "down":
                    break;

                case "up":
                    break;

                case "left":
                    break;
                case "right":
                    break;

                case "diagonal up left":
                    for(String command : COMMANDS) {
                        switch (command) {
                            case "paint":
                                // paint triangle shape
                                for(int i = 0; i < squaresMoved; ++i){
                                    for(int m = i; m > -1; --m){
                                        // check if the square is on the board
                                        if (startRow - i >= 0 && startCol - m >= 0){
                                            paintInfo.add(new Integer[]{startRow - i, startCol - m});
                                        }
                                    }
                                }
                                break;
                            case "dest":
                                destRow = startRow - squaresMoved;
                                destCol = startCol - squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                // the route will determine after located destination
                                int col = startCol;
                                for(int row = startRow; row >= destRow; --row, --col){
                                    route.add(new Integer[]{row, col});
                                }
                                break;
                        }
                    }
                    break;
                case "diagonal up right":
                    for(String command : COMMANDS) {
                        switch (command) {
                            case "paint":
                                // paint triangle shape
                                for(int i = 0; i < squaresMoved; ++i){
                                    for(int m = i; m > -1; ++m){
                                        // check if the square is on the board
                                        if (startRow - i >= 0 && startCol - m >= 0){
                                            paintInfo.add(new Integer[]{startRow - i, startCol + m});
                                        }
                                    }
                                }
                                break;
                            case "dest":
                                destRow = startRow - squaresMoved;
                                destCol = startCol + squaresMoved;
                                checkValid();
                                break;
                            case "route":
                                int col = startCol;
                                for(int row = startRow; row >= destRow; --row, ++col ){
                                    route.add(new Integer[]{row, col});
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    }
}
