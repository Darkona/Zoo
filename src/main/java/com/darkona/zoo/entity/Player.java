package com.darkona.zoo.entity;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.entity.PlayerRenderer;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;

import java.awt.*;

public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final World world;
    private final PlayerRenderer playerRenderer;

    public Player(World world, Controller controller, String name){
        super(new Position(world.getSize().width / 2,world.getSize().height / 2), new Size());
        this.controller = controller;
        this.world = world;
        this.name = name;
        this.playerRenderer = new PlayerRenderer();

    }

    @Override
    public void update() {
        Position oldPos = new Position(position.x,position.y);
        boolean moved = false;
        if(controller.isSpace()){
            Simulation.PAUSED = !Simulation.PAUSED;
        }
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
        playerRenderer.render(graphics, this);
    }
}