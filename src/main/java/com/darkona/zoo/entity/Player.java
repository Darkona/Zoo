package com.darkona.zoo.entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.Controller;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.animal.Fox;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Target;
import com.darkona.zoo.render.renderer.entity.PlayerRenderer;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.WorldThing;
import com.darkona.zoo.world.terrain.TerrainType;


public class Player extends WorldThing implements Renderable {

    private final Controller controller;
    private final PlayerRenderer playerRenderer;
    private final ArrayList<TerrainType> validTerrains = new ArrayList<>();
    private Stack<Movement> movs;
    private Position destination;

    public Player(World world, Controller controller, String name) {
        super(new Position(world.getSize().width / 2, world.getSize().height / 2), new Size(), world);
        this.controller = controller;
        this.name = name;
        this.playerRenderer = new PlayerRenderer();
        this.movs = new Stack<>();
        validTerrains.add(TerrainType.WALKABLE);
        validTerrains.add(TerrainType.SWIMMABLE);
        validTerrains.add(TerrainType.FLYABLE);
    }

    @Override
    public void update() {
        Position oldPos = new Position(position.x, position.y);
        boolean moved = false;

        if (!movs.isEmpty() && controller.isEscape()) {
            movs = new Stack<>();
        }

        if (movs != null && !movs.isEmpty()) {
            Movement mov = movs.pop();
            position.translate(mov.getDx(), mov.getDy());
            moved = true;
        }
        if (position.equals(destination)) {
            world.getCellAt(position).setVegetation(new NoVegetation(world, destination));
            destination = null;
        }
        if (controller.isA() && movs.isEmpty() && destination == null) {
            destination = MovementAi.generateRandomDestination(this, validTerrains);
            if (destination != null) {
                world.getCellAt(destination).setVegetation(new Target(world, destination));
            }
        }

        if (controller.isB() && movs.isEmpty()) {
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
        }

        if (controller.isSpace()) {
            Simulation.PAUSED = !Simulation.PAUSED;
        }
        if (controller.isRequestingUp() && position.y > 0) {
            destination = new Position(position, 0, -1);
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
            moved = true;
        }
        if (controller.isRequestingDown() && position.y < world.getField()[0].length - 1) {
            destination = new Position(position, 0, 1);
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
            moved = true;
        }
        if (controller.isRequestingLeft() && position.x > 0) {
            destination = new Position(position, -1, 0);
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
            moved = true;
        }
        if (controller.isRequestingRight() && position.x < world.getField().length - 1) {
            destination = new Position(position, 1, 0);
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
            moved = true;
        }
        if (moved) {
            world.movePlayer(this, oldPos);
        }
        if (controller.isF()) {
            world.setAnimal(new Fox(world, new Position(position)));
        }
    }

    @Override
    public void render(Graphics graphics) {
        playerRenderer.render(graphics, this);
    }

    @Override
    public String toString() {
        return this.name + ": " + position;
    }

}
