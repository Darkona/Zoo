package com.darkona.zoo.entity.ai;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.pmw.tinylog.Logger;

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

        WorldCell current = thing.getCell();
        return findDestinationCell2(current, destination);
    }

    public static Stack<Movement> traceRouteToPosition2(Position destination, WorldThing thing) {
        Stack<Movement> movements = new Stack<>();
        WorldCell current = thing.getCell();
        Queue<WorldCell> queue = new ArrayDeque<>();
        if (findDestinationCell(current, null, destination, queue, movements, new HashMap<>(), null)) {
            Logger.info("Found way to destination at " + destination);
        }
        else {
            Logger.warn("Can't find a way to destination at " + destination);
        }
        return movements;
    }

    private static boolean findDestinationCell(WorldCell current, WorldCell prev, Position destination, Queue<WorldCell> queue, Stack<Movement> movements,
            Map<Position, Boolean> visited, Direction directionFrom) {
        Position currentPos = new Position(current.getPosition());
        queue.add(current);
        if (!current.isPassable()) return false;
        if ((visited.get(current.getPosition()) != null && visited.get(current.getPosition()))) return false;
        visited.put(current.getPosition(), true);
        int x = 0, y = 0;
        if (prev != null) {
            x = current.getX() - prev.getX();
            y = current.getY() - prev.getY();
        }
        if (currentPos.equals(destination)) {
            current.setFloor(new Water(current.getWorld(), currentPos));
            movements.push(new Movement(x, y, 1));
            return true;
        }
        for (Map.Entry<Direction, WorldCell> e : current.getNeighbors().entrySet()) {
            WorldCell cell = e.getValue();
            if (cell != null && !cell.equals(prev)) {
                if (findDestinationCell(cell, current, destination, queue, movements, visited, e.getKey().getOpposite())) {
                    movements.push(new Movement(x, y, 1));
                    return true;
                }
            }
        }
        return false;
    }

    private static Stack<Movement> findDestinationCell2(WorldCell current, Position destination) {

        HashMap<Position, Boolean> visited = new HashMap<>();
        Queue<WorldCell> queue = new LinkedList<>();
        queue.add(current);
        visited.put(current.getPosition(), true);
        boolean found = false;
        Stack<Movement> movements = null;
        while (queue.peek() != null && !found) {
            movements = new Stack<>();
            WorldCell cell = queue.poll();
            for (Direction dir : cell.getNeighbors().keySet()) {
                WorldCell test = cell.getNeighbors().get(dir);
                int dx = cell.getX() - test.getX();
                int dy = cell.getY() - test.getY();
                movements.push(new Movement(dx, dy,1));
                if(visited.get(test.getPosition())) break;
                if (test.getPosition().equals(destination)) {
                    found = true;
                    break;
                }
                else if (test.isPassable()) {
                    visited.put(test.getPosition(), true);
                    queue.add(test);
                }
            }
        }
        return movements;
    }
}
