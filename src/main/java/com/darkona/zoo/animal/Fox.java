package com.darkona.zoo.animal;

import com.darkona.zoo.Movement;
import com.darkona.zoo.ai.MovementAi;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.render.renderer.FoxRenderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldCell;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.Random;


public class Fox extends Animal implements Walker {

    private final FoxRenderer renderer;


    public Fox(World world, Coordinates position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.renderer = new FoxRenderer(5, 16, new Size(20, 16));
    }



    @Override
    public void update() {
        if (destination != null) {
            Logger.debug("Trying to move to destination " + destination);
            move(world, destination);
        }

        if (position.equals(destination)) {
            Logger.debug("Already at destination. Position: " + position + " and destination: " + destination);
            destination = null;
        }

        if (destination == null) {
            Logger.debug("No destination. Generating new destination.");
            MovementAi.generateRandomDestination(this);
            if(destination != null) Logger.debug("New destination: " + destination);
        }
    }

    @Override
    public void render(Graphics graphics) {
        this.renderer.render(graphics, this);
    }

    private int getTerrainSpeed() {
        return currentCell.getFloor().getSpeedModifier();
    }

    @Override
    public void move(World world, Coordinates destination) {

        Movement mov = MovementAi.prepareDeltas(this, getTerrainSpeed());
        if (mov.isMovement()) {
            Random r = new Random();
            boolean m = r.nextBoolean();
            int movX = m ? mov.getDx() : 0;
            int movY = !m ? mov.getDy() : 0;

            //int movX = mov.getDx();
            //int movY = mov.getDy();
            Coordinates oldPos = new Coordinates(position.x, position.y);
            if (world.getField()[position.x + mov.getDx()][position.y + mov.getDy()].canPutAnimal(this) > -1){
                Logger.debug("Movement: " + mov + " -- Future coords are" + new Coordinates(position.x + mov.getDx(), position.y + mov.getDy()));
                position.translate(movX, movY);
                world.moveThing(this, oldPos);
            }
        }
    }

    @Override
    public void walk(Coordinates position, Coordinates destination) {

    }

    @Override
    public String toString() {
        return "Fox. Id: " + id + ". Position " + position;
    }

}
