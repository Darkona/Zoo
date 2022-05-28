package com.darkona.zoo.world;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.interfaces.Updatable;
import lombok.Data;

@Data
public abstract class WorldThing implements Updatable {

    protected Coordinates position;
    protected String name;
    protected Size size;

    public WorldThing(){}

    public WorldThing(Coordinates position, Size size) {
        this.position = position;
        this.size = size;
    }

    public abstract void update();

}
