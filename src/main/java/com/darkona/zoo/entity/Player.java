package com.darkona.zoo.entity;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.render.renderer.entity.PlayerRenderer;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import lombok.ToString;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final PlayerRenderer playerRenderer;
    private Stack<Movement> movs;

    public Player(World world, Controller controller, String name){
        super(new Position(world.getSize().width / 2,world.getSize().height / 2), new Size(), world);
        this.controller = controller;
        this.name = name;
        this.playerRenderer = new PlayerRenderer();
        this.movs = new Stack<>();
    }

    @Override

    public void update() {
        Position oldPos = new Position(position.x,position.y);
        boolean moved = false;
        if(movs != null && !movs.isEmpty()){
            Movement mov = movs.pop();
            Logger.debug("Movement: " + mov + " -- Future coords are" + new Position(position.x + mov.getDx(), position.y + mov.getDy()));
            position.translate(mov.getDx(), mov.getDy());
            world.movePlayer(this, oldPos);
        }
        if(controller.isA() && movs.isEmpty()){
            Logger.debug("A pressed. Player: " + this);
            Position des;
            int count = 0;
             do {
               des = new Position(new Random().nextInt(world.getWidth()), new Random().nextInt(world.getHeight()));
               count++;
             } while(count < 20 || !world.getCellAt(des).isPassable());

            Logger.debug("Destination: " + des);
            movs = MovementAi.traceRouteToPosition(des, this);
        }
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

    @Override
    public String toString(){
        return this.name + ": " + position;
    }

}
