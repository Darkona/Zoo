package com.darkona.zoo.entity.vegetation;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.TerrainRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;

public class Bush extends Vegetation {

    public Bush(World world, Position position) {
        super(world, position, new Size());
        this.u = this.v = 352;
        this.imageSize = new Size(32);
        this.name = "Bush";
    }
}
