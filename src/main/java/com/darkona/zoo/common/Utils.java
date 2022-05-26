package com.darkona.zoo.common;

import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

import static com.darkona.zoo.simulation.Simulation.SCALE;

public class Utils
{
    public static WorldThing getNearestObject(World world, double x, double y, int i, Class<? extends WorldThing> foodClass) {
        return null;
    }

    public static void fillRect(Graphics graphics, WorldThing thing){
        graphics.fillRect(thing.getPosition().x  * SCALE,thing.getPosition().y  * SCALE, thing.getSize().width  * SCALE,
                thing.getSize().height  * SCALE);
    }
}
