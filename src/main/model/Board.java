package main.model;

public class Board {

    private int wide;
    private int area;

    public Board(int wide) {
        this.wide = wide;
        area = wide * wide;
    }

}
