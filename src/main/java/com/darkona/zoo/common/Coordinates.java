package com.darkona.zoo.common;

import java.awt.*;

public class Coordinates extends Point {

    public Coordinates(int x, int y) {
        super(x,y);
    }

    @Override
    public String toString(){
        return "X= " + x + ", Y= " + y;
    }

}
