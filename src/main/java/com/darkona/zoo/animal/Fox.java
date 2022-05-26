package com.darkona.zoo.animal;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.common.Utils;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.world.World;

import java.awt.*;
import java.util.Random;

import static com.darkona.zoo.Main.ENABLE_LOG;

public class Fox extends Animal implements Walker {

    private final Random r = new Random();


    public Fox(World world, Coordinates position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.image = ImageUtils.loadImage("/animal/fox.png");

    }

    private void generateRandomDestination() {
        if(ENABLE_LOG)System.out.println("Generating new destination.");
        int x = r.nextInt(world.getSize().width);
        int y = r.nextInt(world.getSize().height);
        if (world.getField()[x][y].canPutAnimal(this) > -1) {
            destination = new Coordinates(x, y);
            if(ENABLE_LOG)System.out.println("Found new destination " + destination);
        }
    }

    @Override
    public void update() {
        if(ENABLE_LOG) System.out.println("Updating " + this);
        if (destination != null) {
            if(ENABLE_LOG)System.out.println("Trying to move to destination " + destination);
            move(world, destination);
        }
        if (position.equals(destination)) {
            if(ENABLE_LOG) System.out.println("Already at destination. Position: " + position + " and destination: " + destination);
            destination = null;
        }
        if (destination == null) {
            if(ENABLE_LOG)System.out.println("No destination.");
            generateRandomDestination();
        }
    }

    @Override
    public Image getSprite(Graphics graphics) {
        try {
            ImageUtils.drawImage(image, this, graphics, 5, 16, new Size(20, 16));
            return image;
        } catch (Exception e) {
            graphics.setColor(Color.ORANGE);
            Utils.fillRect(graphics, this);
            return null;
        }
    }


    @Override
    public void render(Graphics graphics) {
        getSprite(graphics);

    }

    private int getTerrainSpeed() {
        return world.getField()[position.x][position.y].getFloor().getSpeedModifier();
    }

    @Override
    public void move(World world, Coordinates destination) {

        Movement mov = prepareDeltas(getTerrainSpeed());
        if (mov.isMovement()) {
            boolean m = r.nextBoolean();
            int movX = m ? mov.getDx() : 0;
            int movY = !m ? mov.getDy() : 0;

            if (world.getField()[position.x + mov.getDx()][position.y + mov.getDy()].canPutAnimal(this) > -1){
                if(ENABLE_LOG) System.out.println("Movement: " + mov + " -- Future coords are" + new Coordinates(position.x, position.y));
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
