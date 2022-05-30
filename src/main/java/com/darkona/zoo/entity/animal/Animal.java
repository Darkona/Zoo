package com.darkona.zoo.entity.animal;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.Renderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldCell;
import com.darkona.zoo.world.WorldThing;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public abstract class Animal extends WorldThing {

    protected int energy;
    protected Position destination;
    protected int maxEnergy;
    protected int minEnergy;
    protected Animal partner;

    protected UUID id;
    protected WorldCell currentCell;

    protected int u;
    protected int v;
    protected Size imageSize;

    public Animal(World world, Position position, Size size) {
        super(position, size, world);
        this.id = UUID.randomUUID();
    }

    public abstract void move(World world, Position destination);

    public void die(){
        currentCell.removeAnimal(this);
        position = null;
    }

    public void isBeingRendered(){
        int a = 0;
        Logger.debug(a);
    }

    public abstract Image getImage();

}
