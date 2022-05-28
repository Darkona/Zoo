package com.darkona.zoo.animal;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.common.Utils;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.render.renderer.ChickenRenderer;
import com.darkona.zoo.world.World;

import java.awt.*;
import java.util.Random;
public class Chicken extends Animal implements Walker {
    private final Random r = new Random();
    private final Configuration configuration;
    private final ChickenRenderer chickenRenderer;
    public Chicken(World world, Coordinates position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.configuration = Configuration.getInstance();
        this.chickenRenderer = new ChickenRenderer(2,2, new Size(28, 28));
    }

    private void generateRandomDestination() {
        if(configuration.isEnableLog())
            System.out.println("Generating new destination.");

        int x = r.nextInt(world.getSize().width);
        int y = r.nextInt(world.getSize().height);
        if (world.getField()[x][y].canPutAnimal(this) > -1) {
            destination = new Coordinates(x, y);
            if(configuration.isEnableLog())
                System.out.println("Found new destination " + destination);
        }
    }

    @Override
    public void update() {
        if(configuration.isEnableLog())
            System.out.println("Updating " + this);

        if (destination != null) {
            if(configuration.isEnableLog())
                System.out.println("Trying to move to destination " + destination);
            move(world, destination);
        }

        if (position.equals(destination)) {
            if(configuration.isEnableLog())
                System.out.println("Already at destination. Position: " + position + " and destination: " + destination);
            destination = null;
        }

        if (destination == null) {
            if(configuration.isEnableLog())
                System.out.println("No destination.");
            generateRandomDestination();
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
    public void move(World world, Coordinates destination) {

        Movement mov = prepareDeltas(getTerrainSpeed());
        if (mov.isMovement()) {
            //boolean m = r.nextBoolean();
            //int movX = m ? mov.getDx() : 0;
            //int movY = !m ? mov.getDy() : 0;

            int movX = mov.getDx();
            int movY = mov.getDy();

            if (world.getField()[position.x + mov.getDx()][position.y + mov.getDy()].canPutAnimal(this) > -1){
                if(configuration.isEnableLog())
                    System.out.println("Movement: " + mov + " -- Future coords are" + new Coordinates(position.x, position.y));

                Coordinates oldPos = new Coordinates(position.x, position.y);
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
