package com.darkona.zoo.common;

import java.awt.*;

public class Position extends Point {

    public Position(int x, int y) {
        super(x,y);
    }

    @Override
    public String toString(){
        return "X= " + x + ", Y= " + y;
    }

}
