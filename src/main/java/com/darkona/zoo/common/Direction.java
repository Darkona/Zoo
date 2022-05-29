package com.darkona.zoo.common;

public enum Direction {

    NORTH(2), EAST(3), SOUTH(0), WEST(1);


    private final int opposite;

    Direction(int opposite){
        this.opposite = opposite;
    }

    public Direction getOpposite() {
        return Direction.values()[opposite];
    }
}
