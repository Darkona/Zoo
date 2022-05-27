package com.darkona.zoo.simulation;

import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final World world;
    private final Image image;

    public Player(World world, Controller controller, String name){
        super(new Coordinates(5,5), new Size());
        this.controller = controller;
        this.world = world;
        this.name = name;
        this.image = ImageUtils.loadImage("/animal/PC.png");
    }

    @Override
    public void update() {
        Coordinates oldPos = new Coordinates(position.x,position.y);
        boolean moved = false;
        if(controller.isRequestingUp() &&  position.y > 0){
            position.translate(0,-1);
            moved = true;
        }
        if(controller.isRequestingDown() && position.y < world.getField()[0].length - 1){
            position.translate(0,1);
            moved = true;
        }
        if(controller.isRequestingLeft() && position.x > 0){
            position.translate(-1,0);
            moved = true;
        }
        if(controller.isRequestingRight() && position.x < world.getField().length - 1){
            position.translate(1,0);
            moved = true;
        }
        if(moved)world.moveThing(this, oldPos);
    }

    @Override
    public void render(Graphics graphics) {
      getSprite(graphics);
    }

    @Override
    public Image getSprite(Graphics graphics) {
        ImageUtils.drawImage(image, this, graphics, 5,5, new Size(15,15));
        return image;
    }
}
