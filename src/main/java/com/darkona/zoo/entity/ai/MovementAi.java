package com.darkona.zoo.entity.ai;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Direction;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.world.WorldCell;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.Water;

public class MovementAi {


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

    public static Stack<Movement> traceRouteToPosition(Position destination, WorldThing thing) {
        Stack<Movement> movs = new Stack<>();
        Stack<Movement> output = new Stack<>();
        WorldCell current = thing.getCell();
        Queue<WorldCell> queue = new ArrayDeque<>();

        if (findDestinationCell(current, null, destination, queue, movs, new HashMap<>(), null)) {
            int x = 0;
            int y = 0;
            for (Movement m : movs) {
                x += m.getDx();
                y += m.getDy();
            }
            for (int i = 0; i < Math.abs(x); i++) {
                output.push(new Movement(Integer.compare(x,0), 0,1));
            }
            for (int i = 0; i < Math.abs(y); i++) {
                output.push(new Movement(0, Integer.compare(y,0), 1));
            }
        }

        Comparator<Movement> scrambler = new Comparator<Movement>() {
            final Random r = new Random();
            @Override
            public int compare(Movement o1, Movement o2) {
                return r.nextInt(3) - 1;
            }
        };
        output.sort(scrambler);
        return output;
    }

    private static boolean findDestinationCell(WorldCell current, WorldCell prev, Position destination, Queue<WorldCell> queue, Stack<Movement> movements,
            Map<Position, Boolean> visited, Direction directionFrom) {
        Position currentPos = new Position(current.getPosition());
        //System.out.print("Cell at " + currentPos + " >> ");
        queue.add(current);
        if (!current.isPassable()) {
            //System.out.println("Not Passable at " + currentPos);
            return false;
        }
        if ((visited.get(current.getPosition()) != null && visited.get(current.getPosition()))) {
            return false;
        }
        visited.put(current.getPosition(), true);

        int x = 0;
        int y = 0;
        if (prev != null) {
            x = current.getX() - prev.getX();
            y = current.getY() - prev.getY();
        }
        if (currentPos.equals(destination)) {
            //System.out.println("FOUND IT!!! " + currentPos);
            current.setFloor(new Water(current.getWorld(), currentPos));
            movements.push(new Movement(x, y, 1));
            return true;
        }

        for (Map.Entry<Direction, WorldCell> e : current.getNeighbors().entrySet()) {
            WorldCell cell = e.getValue();
            if (cell != null && !cell.equals(prev)) {
                //System.out.println("Moving search to> " + e.getKey().toString());
                if (findDestinationCell(cell, current, destination, queue, movements, visited, e.getKey().getOpposite())) {
                    //current.setFloor(new RedDirt(current.getWorld(), currentPos));
                    movements.push(new Movement(x, y, 1));
                    return true;
                }
            }
        }
        return false;
    }
}
