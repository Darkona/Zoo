package com.darkona.zoo.world;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Updatable;
import lombok.Data;

@Data
public abstract class WorldThing implements Updatable {

    protected Position position;
    protected String name;
    protected Size size;

    public WorldThing(){}

    public WorldThing(Position position, Size size) {
        this.position = position;
        this.size = size;
    }

    public abstract void update();

}
