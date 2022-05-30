package com.darkona.zoo.entity.ai;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Direction;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldCell;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.TerrainType;
import com.darkona.zoo.world.terrain.Water;
import org.pmw.tinylog.Logger;

public class MovementAi {


    public static Movement prepareDeltas(Animal animal, int speed) {
        int deltaX = Integer.compare(animal.getDestination().x - animal.getPosition().x, 0);
        int deltaY = Integer.compare(animal.getDestination().y - animal.getPosition().y, 0);
        return new Movement(deltaX, deltaY, speed);
    }

    public static Position generateRandomDestination(WorldThing thing, List<TerrainType> passables) {
        World w = thing.getWorld();
        Position destination;
        try {
            int count = 0;
            do {
                destination  = new Position(new Random().nextInt(w.getWidth() ), new Random().nextInt(w.getHeight() ));
            } while (++count < 20 || !passables.contains(w.getCellAt(destination).getFloor().getTerrainType()));
            return destination;
        } catch (Exception e) {
            Logger.error("Error!");
            e.printStackTrace();
            return null;
        }
    }

    public static Stack<Movement> traceWorstRouteToDestination(Position destination, WorldThing thing) {
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
        if (current == null) {
            return false;
        }
        Position currentPos = new Position(current.getPosition());
        queue.add(current);
        if (!current.isPassable()) {
            return false;
        }
        if ((visited.get(current.getPosition()) != null && visited.get(current.getPosition()))) {
            return false;
        }
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


    public static Stack<Movement> traceShortestRouteToDestination(Position destination, WorldThing thing, List<TerrainType> validTerrains) {
        WorldCell current = thing.getCell();
        return findDestinationCell2(current, destination, validTerrains);
    }

    private static Stack<Movement> findDestinationCell2(WorldCell current, Position destination, List<TerrainType> validTerrains) {
        Position initialPosition = new Position(current.getPosition());
        Set<Position> visited = new HashSet<>();
        Queue<WorldCell> queue = new LinkedList<>();
        Stack<Movement> movements = new Stack<>();
        //movements.push();
        queue.add(current);
        visited.add(current.getPosition());
        HashMap<Position, Position> previous = new HashMap<>();
        Position foundPos = null;
        while (queue.peek() != null) {
            WorldCell cell = queue.poll();
            if (cell.getPosition().equals(destination)) {
                foundPos = cell.getPosition();
                break;
            }
            for (WorldCell test : cell.getNeighbors().values()) {
                if (!visited.contains(test.getPosition()) && validTerrains.contains(test.getFloor().getTerrainType())) {
                    visited.add(test.getPosition());
                    queue.add(test);
                    previous.put(test.getPosition(), cell.getPosition());
                }
            }
        }
        try {
            if (foundPos != null) {
                Position search = previous.get(foundPos);
                Position curPos = foundPos;
                while (search != null && !initialPosition.equals(search)) {
                    int dX = curPos.x - search.x;
                    int dY = curPos.y - search.y;
                    movements.push(new Movement(dX, dY, 1));
                    curPos = search;
                    search = previous.get(curPos);
                }
                if(search != null) {
                    int dX = curPos.x - search.x;
                    int dY = curPos.y - search.y;
                    movements.push(new Movement(dX, dY, 1));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return movements;
    }
}
