package com.darkona.zoo.vegetation;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.world.WorldThing;

public  abstract class Vegetation extends WorldThing implements Renderable {

    public Vegetation(Coordinates position, Size size) {
        super(position, size);
    }


}
