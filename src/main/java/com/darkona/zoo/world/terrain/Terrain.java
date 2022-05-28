package com.darkona.zoo.world.terrain;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Terrain extends WorldThing implements Renderable {

    protected TerrainType terrainType;
    protected TerrainRenderer terrainRenderer;

    public Terrain(Position position, Size size, TerrainType terrainType) {
        super(position, size);
        this.terrainType = terrainType;
    }

    public abstract int getSpeedModifier();

}
