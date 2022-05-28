package com.darkona.zoo.terrain;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Terrain extends WorldThing implements Renderable {

    protected TerrainType terrainType;
    protected TerrainRenderer terrainRenderer;

    public Terrain(Coordinates coordinates, Size size, TerrainType terrainType) {
        super(coordinates, size);
        this.terrainType = terrainType;
    }

    public abstract int getSpeedModifier();

}
