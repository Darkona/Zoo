package com.darkona.zoo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movement {

    int dx;
    int dy;
    private double speed;

    public boolean isMovement(){
        return dx != 0 || dy != 0;
    }

    @Override
    public String toString(){
        return "DX= " + dx + ", DY=" + dy;
    }
}
