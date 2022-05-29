package com.darkona.zoo.world;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Updatable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public abstract class WorldThing implements Updatable {

    protected final UUID id;
    protected Position position;
    protected String name;
    protected Size size;
    protected World world;

    public WorldThing(){
        id = UUID.randomUUID();
    }
    public WorldThing(Position position, Size size, World world) {
        this.position = position;
        this.size = size;
        this.id = UUID.randomUUID();
        this.world = world;
    }

    public abstract void update();

    public boolean equals(WorldThing other){
        return other.id == this.id && other.position == this.position && other.name.equals(this.name);
    }

    public WorldCell getCell(){
        return world.getCellAt(position);
    }
}
