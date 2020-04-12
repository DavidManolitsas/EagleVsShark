package main.java.model.move;

import java.util.List;

public class HarpyEagleMove extends Move {

    public HarpyEagleMove(int startRow, int startCol, int squaresMoved, String direction) {
        super(startRow, startCol, squaresMoved, direction);
        checkAndAttach();
    }

    private void checkAndAttach(){
        // generate all info base on the direction
        switch(direction){
            // forward direction for eagle
            case "down":
                // execute each command
                for(String command : COMMANDS){
                    switch(command){
                        case "paint":
                            paintTrack();
                            // add the two squares at the top of the destination(according to the direction)
                            paintInfo.add(new int[]{destination[0] + 1, destination[1] - 1});
                            paintInfo.add(new int[]{destination[0] + 1, destination[1] + 1});
                            break;

                        case "dest":
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow + squaresMoved;
                            destination[1] = startCol;
                            break;

                        case "route":
                            recordRoute();
                            break;
                    }
                }
                break;

            // back direction for eagle
            case "up":
                // execute each command
                for(String command : COMMANDS){
                    switch(command){
                        case "paint":
                            paintTrack();
                            // add the two squares at the top of the destination(according to the direction)
                            paintInfo.add(new int[]{destination[0] - 1, destination[1] - 1});
                            paintInfo.add(new int[]{destination[0] - 1, destination[1] + 1});
                            break;
                        case "dest":
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow - squaresMoved;
                            destination[1] = startCol;
                            break;
                        case "route":
                            recordRoute();
                            break;
                    }
                }
                break;

            case "left":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintTrack();
                            // add the two squares at the top of the destination(according to the direction)
                            paintInfo.add(new int[]{destination[0] + 1, destination[1] - 1});
                            paintInfo.add(new int[]{destination[0] - 1, destination[1] - 1});
                            break;

                        case "dest":
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol - squaresMoved;
                            break;

                        case "route":
                            recordRoute();
                            break;
                    }
                }
                break;

            case "right":
                // execute each command
                for (String command : COMMANDS) {
                    switch (command) {
                        case "paint":
                            paintTrack();
                            // add the two squares at the top of the destination(according to the direction)
                            paintInfo.add(new int[]{destination[0] + 1, destination[1] + 1});
                            paintInfo.add(new int[]{destination[0] - 1, destination[1] + 1});
                            break;

                        case "dest":
                            // the destination will be check first
                            // record destination coordinate base on the steps and direction
                            destination[0] = startRow;
                            destination[1] = startCol + squaresMoved;
                            break;

                        case "route":
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

    // record the coordinate of the track
    private void paintTrack(){
        // copy all the coordinates in route
        paintInfo.addAll(route);
        // remove the first element aka the starting point
        paintInfo.remove(1);
    }

    private void recordRoute(){
        route.add(new int[]{startRow, startCol});
        route.add(destination);
    }
}
