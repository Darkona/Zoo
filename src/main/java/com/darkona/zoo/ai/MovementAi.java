package com.darkona.zoo.ai;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.animal.Animal;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.world.World;
import org.pmw.tinylog.Logger;

import java.util.Random;

public class MovementAi {

    private static final Configuration configuration = Configuration.getInstance();

    public static void generateRandomDestination(Animal animal) {
        Random r = new Random();
        if(configuration.isEnableLog()) Logger.debug("Generating new destination.");
        int x = r.nextInt(animal.getWorld().getSize().width);
        int y = r.nextInt(animal.getWorld().getSize().height);
        if (animal.getWorld().getField()[x][y].canPutAnimal(animal) > -1) {
            animal.setDestination(new Coordinates(x, y));
            if(configuration.isEnableLog()) Logger.debug("Found new destination: " + animal.getDestination());
        }else{
            if(configuration.isEnableLog()) Logger.debug("Destination invalid: " + new Coordinates(x,y));
        }
    }
}
