package com.darkona.zoo.entity.animal;

import com.darkona.zoo.Movement;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.render.renderer.entity.ChickenRenderer;
import com.darkona.zoo.world.World;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.Random;
public class Chicken extends Animal implements Walker {

    private final ChickenRenderer chickenRenderer;
    public Chicken(World world, Position position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.chickenRenderer = new ChickenRenderer(2,2, new Size(28, 28));
    }

    @Override
    public void update() {
        Logger.debug("Updating " + this);

        if (destination != null) {
            Logger.debug("Trying to move to destination " + destination);
            move(world, destination);
        }

        assert destination != null;
        if (position.equals(destination)) {
            Logger.debug("Already at destination. Position: " + position + " and destination: " + destination);
            destination = null;
        }

        if (destination == null) {
            Logger.debug("No destination.");
            MovementAi.generateRandomDestination(this);
        }
    }


    @Override
    public void render(Graphics graphics) {
       chickenRenderer.render(graphics, this);
    }

    private int getTerrainSpeed() {
        return world.getField()[position.x][position.y].getFloor().getSpeedModifier();
    }

    @Override
    public void move(World world, Position destination) {

        Movement mov = MovementAi.prepareDeltas(this, getTerrainSpeed());
        if (mov.isMovement()) {
            Random r = new Random();
            boolean m = r.nextBoolean();
            int movX = m ? mov.getDx() : 0;
            int movY = !m ? mov.getDy() : 0;

            //int movX = mov.getDx();
            //int movY = mov.getDy();

            if (world.getField()[position.x + mov.getDx()][position.y + mov.getDy()].canPutAnimal(this) > -1){
                Logger.debug("Movement: " + mov + " -- Future coords are" + new Position(position.x, position.y));
                Position oldPos = new Position(position.x, position.y);
                position.translate(movX, movY);
                world.moveThing(this, oldPos);
            }
        }
    }


    @Override
    public void walk(Position position, Position destination) {

    }

    @Override
    public String toString() {
        return "Fox. Id: " + id + ". Position " + position;
    }
}
