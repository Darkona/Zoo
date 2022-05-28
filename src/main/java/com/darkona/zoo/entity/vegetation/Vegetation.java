package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.world.WorldThing;

public  abstract class Vegetation extends WorldThing implements Renderable {

    public Vegetation(Position position, Size size) {
        super(position, size);
    }


}
