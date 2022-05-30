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
        String out = "";
        if(dx > 0) out += "East";
        if(dx < 0) out += "West";
        if(dy > 0) out += "South";
        if(dy < 0) out += "North";
        return out;
    }
}
