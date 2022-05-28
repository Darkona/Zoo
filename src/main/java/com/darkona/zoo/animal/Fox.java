package com.darkona.zoo.animal;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.Movement;
import com.darkona.zoo.ai.MovementAi;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.FoxRenderer;
import com.darkona.zoo.world.World;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.Random;


public class Fox extends Animal implements Walker {

    private final Configuration configuration;
    private final FoxRenderer renderer;

    public Fox(World world, Coordinates position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.configuration = Configuration.getInstance();
        this.renderer = new FoxRenderer(5, 16, new Size(20, 16));
    }



    @Override
    public void update() {
        if(configuration.isEnableLog()) Logger.debug("Updating " + this);

        if (destination != null) {
            if(configuration.isEnableLog()) Logger.debug("Trying to move to destination " + destination);
            move(world, destination);
        }

        if (position.equals(destination)) {
            if(configuration.isEnableLog()) Logger.debug("Already at destination. Position: " + position + " and destination: " + destination);
            destination = null;
        }

        if (destination == null) {
            if(configuration.isEnableLog()) Logger.debug("No destination.");
            MovementAi.generateRandomDestination(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.renderer.render(graphics, this);
    }

    private int getTerrainSpeed() {
        return world.getField()[position.x][position.y].getFloor().getSpeedModifier();
    }

    @Override
    public void move(World world, Coordinates destination) {

        Movement mov = prepareDeltas(getTerrainSpeed());
        if (mov.isMovement()) {
            //boolean m = r.nextBoolean();
            //int movX = m ? mov.getDx() : 0;
            //int movY = !m ? mov.getDy() : 0;

            int movX = mov.getDx();
            int movY = mov.getDy();
            Coordinates oldPos = new Coordinates(position.x, position.y);
            if (world.getField()[position.x + mov.getDx()][position.y + mov.getDy()].canPutAnimal(this) > -1){
                if(configuration.isEnableLog())
                    System.out.println("Movement: " + mov + " -- Future coords are"
                            + new Coordinates(position.x + mov.getDx(), position.y + mov.getDy()));
                position.translate(movX, movY);
                world.moveThing(this, oldPos);
            }
        }
    }

    private Movement prepareDeltas(int speed) {

        int deltaX = destination.x - position.x;
        int deltaY = destination.y - position.y;
        if (deltaX < 0) deltaX = -1;
        if (deltaX > 0) deltaX = 1;
        if (deltaY < 0) deltaY = -1;
        if (deltaY > 0) deltaY = 1;

        return new Movement(deltaX * speed, deltaY * speed);
    }

    @Override
    public void walk(Coordinates position, Coordinates destination) {

    }

    @Override
    public String toString() {
        return "Fox. Id: " + id + ". Position " + position;
    }

}
