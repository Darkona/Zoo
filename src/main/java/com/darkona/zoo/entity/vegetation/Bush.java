package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Bush extends Vegetation {


    public Bush(World world, Position position) {
        super(world, position, new Size());
        this.terrainRenderer = new TerrainRenderer( 352, 352, new Size(32, 32));
        this.name = "Bush";
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics graphics) {
        terrainRenderer.render(graphics, this);
    }

}
