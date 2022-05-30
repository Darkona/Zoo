package com.darkona.zoo.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.animal.Chicken;
import com.darkona.zoo.entity.animal.Fox;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Target;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.TerrainType;
import org.pmw.tinylog.Logger;


public class Player extends WorldThing {

    private final Controller controller;
    private final ArrayList<TerrainType> validTerrains = new ArrayList<>();
    private Stack<Movement> movements;
    private Position destination;
    private int step = 0;
    private int speed = 4;

    public Player(World world, Controller controller, String name) {
        super(new Position(world.getSize().width / 2, world.getSize().height / 2), new Size(), world);
        this.controller = controller;
        this.name = name;
        this.movements = new Stack<>();
        validTerrains.add(TerrainType.WALKABLE);
        validTerrains.add(TerrainType.SWIMMABLE);
        validTerrains.add(TerrainType.FLYABLE);
    }

    private void createAnimal(String animal) {
        world.getWorldCreator().createAnimal(animal, new Position(position));
    }

    @Override
    public void update() {
        Position oldPos = new Position(position.x, position.y);
        boolean moved = false;

        if (step == 0 || step == 2 || step == 3) {
            if (movements != null && !movements.isEmpty()) {
                Movement mov = movements.pop();
                position.translate(mov.getDx(), mov.getDy());
                moved = true;
            }
            if (controller.isRequestingUp() && position.y > 0) {
                position.translate(0, -1);
                moved = true;
            }
            if (controller.isRequestingDown() && position.y < world.getField()[0].length - 1) {
                position.translate(0, 1);
                moved = true;
            }
            if (controller.isRequestingLeft() && position.x > 0) {
                position.translate(-1, 0);
                moved = true;
            }
            if (controller.isRequestingRight() && position.x < world.getField().length - 1) {
                position.translate(1, 0);
                moved = true;
            }
        }
        if (step == 1) {
            if (!movements.isEmpty() && controller.isEscape()) {
                movements = new Stack<>();
            }
            if (position.equals(destination)) {
                if (world.getCellAt(position).getVegetation().getName().equals("Target"))
                    world.getCellAt(position).setVegetation(new NoVegetation(world, destination));
                destination = null;
            }


        }

        if (controller.isA() && movements.isEmpty() && destination == null) {
            destination = MovementAi.generateRandomDestination(this, validTerrains);
            if (destination != null) {
                world.getCellAt(destination).setVegetation(new Target(world, destination));
            }
        }
        if (controller.isB() && movements.isEmpty()) {
            movements = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
            ArrayList<Movement> forPrint = new ArrayList<>(movements);
            Collections.reverse(forPrint);
            String msg = "Movements > " + forPrint.stream().map(Movement::toString).collect(Collectors.joining(","));
            Logger.info(msg);
        }
        if (controller.isSpace()) {
            Simulation.PAUSED = !Simulation.PAUSED;
        }

        if (moved) {
            world.movePlayer(this, oldPos);
        }

        if (controller.isF()) {
            createAnimal("Fox");
        }
        if (controller.isC()) {
            createAnimal("Chicken");
        }

        step = (step + 1) % speed;
    }

    @Override
    public String toString() {
        return this.name + ": " + position;
    }

}
