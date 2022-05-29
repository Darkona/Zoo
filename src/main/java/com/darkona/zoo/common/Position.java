package com.darkona.zoo.common;

import java.awt.*;

public class Position extends Point {

    public Position(int x, int y) {
        super(x,y);
    }

    public Position(Position pos){
        super(pos.x, pos.y);
    }

    /**
     * This constructor makes a new Position from an existing position PLUS the added X and Y.
     * @param pos Original position
     * @param x Additional X
     * @param y Additional Y
     */
    public Position(Position pos, int x, int y){
        super(pos.x + x, pos.y + y);
    }
    @Override
    public String toString(){
        return "X= " + x + ", Y= " + y;
    }

    public boolean equals(Position position){
        return position != null && position.x == x && position.y == y;
    }

}
