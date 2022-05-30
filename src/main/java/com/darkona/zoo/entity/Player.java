package com.darkona.zoo.entity;

import java.awt.*;
import java.util.Random;
import java.util.Stack;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Target;
import com.darkona.zoo.render.renderer.entity.PlayerRenderer;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.Water;
import org.pmw.tinylog.Logger;


public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final PlayerRenderer playerRenderer;
    private Stack<Movement> movs;
    private Position destination;

    public Player(World world, Controller controller, String name) {
        super(new Position(world.getSize().width / 2, world.getSize().height / 2), new Size(), world);
        this.controller = controller;
        this.name = name;
        this.playerRenderer = new PlayerRenderer();
        this.movs = new Stack<>();
    }


    private void findRandomDestination(){
        try {
            int count = 0;
            do {
                destination  = new Position(new Random().nextInt(world.getWidth()), new Random().nextInt(world.getHeight()));
            } while (++count < 20 || !world.getCellAt(destination).isPassable());
        } catch (Exception e) {
            Logger.error("Error!");
            e.printStackTrace();
        }
    }


    @Override
    public void update() {
        Position oldPos = new Position(position.x, position.y);
        boolean moved = false;


        if (!movs.isEmpty() && controller.isEscape()) movs = new Stack<>();

        if (movs != null && !movs.isEmpty()) {
            Movement mov = movs.pop();
            position.translate(mov.getDx(), mov.getDy());
            world.movePlayer(this, oldPos);
        }
        if(position.equals(destination)){
            world.getCellAt(position).setVegetation(new NoVegetation(world, destination));
            destination = null;
        }
        if (controller.isA() && movs.isEmpty() && destination == null) {
           findRandomDestination();
           world.getCellAt(destination).setVegetation(new Target(world, destination));
        }

        if (controller.isB() && movs.isEmpty()) {
           movs = MovementAi.traceRouteToPosition2(destination, this);
        }

        if (controller.isSpace()) {
            Simulation.PAUSED = !Simulation.PAUSED;
        }
        if (controller.isRequestingUp() && position.y > 0) {
            position.translate(0, -1);
            moved = true;
        }
        if (controller.isRequestingDown() && position.y < world.getField()[0].length - 1) {
            position.translate(0, 1);
            moved = true;
        }
        if (controller.isRequestingLeft() && position.x > 0) {
            position.translate(-1, 0);
            moved = true;
        }
        if (controller.isRequestingRight() && position.x < world.getField().length - 1) {
            position.translate(1, 0);
            moved = true;
        }
        if (moved) {
            world.moveThing(this, oldPos);
        }
    }

    @Override
    public void render(Graphics graphics) {
        playerRenderer.render(graphics, this);
    }

    @Override
    public String toString() {
        return this.name + ": " + position;
    }

}
