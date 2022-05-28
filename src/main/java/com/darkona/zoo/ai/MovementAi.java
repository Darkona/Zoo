package com.darkona.zoo.ai;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.Movement;
import com.darkona.zoo.animal.Animal;
import com.darkona.zoo.common.Coordinates;
import org.pmw.tinylog.Logger;

import java.util.Random;

public class MovementAi {

    private static final Configuration configuration = Configuration.getInstance();

    public static Movement prepareDeltas(Animal animal, int speed) {
        int deltaX = Integer.compare(animal.getDestination().x - animal.getPosition().x, 0);
        int deltaY = Integer.compare(animal.getDestination().y - animal.getPosition().y, 0);
        return new Movement(deltaX * speed, deltaY * speed);
    }
    public static void generateRandomDestination(Animal animal) {
        Random r = new Random();
        int x = r.nextInt(animal.getWorld().getSize().width);
        int y = r.nextInt(animal.getWorld().getSize().height);
        if (animal.getWorld().getField()[x][y].canPutAnimal(animal) > -1) {
            animal.setDestination(new Coordinates(x, y));
        }
    }
}
