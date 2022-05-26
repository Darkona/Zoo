package com.darkona.zoo.simulation;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.common.Utils;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final World world;

    public Player(World world, Controller controller, String name){
        super(new Coordinates(5,5), new Size());
        this.controller = controller;
        this.world = world;
        this.name = name;
    }

    @Override
    public void update() {
        Coordinates oldPos = new Coordinates(position.x,position.y);
        boolean moved = false;
        if(controller.isRequestingUp()){
            position.translate(0,-1);
            moved = true;
        }
        if(controller.isRequestingDown()){
            position.translate(0,1);
            moved = true;
        }
        if(controller.isRequestingLeft()){
            position.translate(-1,0);
            moved = true;
        }
        if(controller.isRequestingRight()){
            position.translate(1,0);
            moved = true;
        }
        if(moved)world.moveThing(this, oldPos);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0x2176CB));
        Utils.fillRect(graphics, this);
    }

    @Override
    public Image getSprite(Graphics graphics) {
        return null;
    }
}
