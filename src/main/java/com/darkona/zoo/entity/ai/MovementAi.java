package com.darkona.zoo.entity.ai;

import com.darkona.zoo.Configuration;
import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Direction;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.vegetation.Bush;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldCell;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.RedDirt;
import com.darkona.zoo.world.terrain.Water;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static com.darkona.zoo.common.Direction.*;

public class MovementAi {

    private static final Configuration configuration = Configuration.getInstance();


    public static Movement prepareDeltas(Animal animal, int speed) {
        int deltaX = Integer.compare(animal.getDestination().x - animal.getPosition().x, 0);
        int deltaY = Integer.compare(animal.getDestination().y - animal.getPosition().y, 0);
        return new Movement(deltaX, deltaY, speed);
    }

    public static void generateRandomDestination(Animal animal) {
        Random r = new Random();
        int x = r.nextInt(animal.getWorld().getSize().width);
        int y = r.nextInt(animal.getWorld().getSize().height);
        if (animal.getWorld().getField()[x][y].canPutAnimal(animal) > -1) {
            animal.setDestination(new Position(x, y));
        }
    }

    /**
     * Finds the shortest route if any from the current position of a thing to a destination.
     *
     * @param destination
     * @param thing
     * @return a list of movements
     */
    public static List<Movement> traceRouteToPosition(Position destination, WorldThing thing) {
        List<Movement> movements = new ArrayList<>();
        WorldCell current = thing.getCell();
        if (findDestinationCell(current, null, destination, movements, new HashMap<>(), null)) for (Movement m : movements)
            Logger.debug(m);
        return movements;
    }


    private static boolean findDestinationCell(WorldCell current, WorldCell prev, Position destination, List<Movement> movements,
                                               Map<Position, Boolean> visited, Direction directionFrom) {
        Position currentPos = new Position(current.getPosition());
        System.out.print("Cell at " + currentPos + " >> ");
        if (!current.isPassable()) {
            System.out.println("Not Passable at " + currentPos);
            return false;
        }
        if ((visited.get(current.getPosition()) != null && visited.get(current.getPosition()) )) return false;
        visited.put(current.getPosition(), true);

        int x = 0;
        int y = 0;
        if (prev != null) {
            x = current.getX() - prev.getX();
            y = current.getY() - prev.getY();
        }
        if (currentPos == destination) {
            System.out.println("FOUND IT!!! " + currentPos);
            movements.add(new Movement(x, y, 1));
            return true;
        }
        if(current.getVegetation().getName().equals("Bush")){
            current.setVegetation(new NoVegetation(current.getWorld(), currentPos));
        }else{
            current.setVegetation(new Bush(current.getWorld(), currentPos));
        }

        for (Map.Entry<Direction, WorldCell> e : current.getNeighbors().entrySet()) {
            WorldCell cell = e.getValue();
            if (cell != null && !cell.equals(prev)) {
                System.out.println("Moving search to> " + e.getKey().toString());
                if (findDestinationCell(cell, current, destination, movements, visited, e.getKey().getOpposite())) {
                    movements.add(new Movement(x, y, 1));
                    return true;
                }
            }
        }
        return false;
    }
}
