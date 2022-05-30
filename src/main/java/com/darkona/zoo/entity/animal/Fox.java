package com.darkona.zoo.entity.animal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.render.renderer.entity.FoxRenderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.terrain.TerrainType;
import org.pmw.tinylog.Logger;

public class Fox extends Animal implements Walker {

    private final FoxRenderer renderer;
    private final ArrayList<TerrainType> validTerrains = new ArrayList<>();
    private Stack<Movement> movements;

    public Fox(World world, Position position) {
        super(world, position, new Size(1));
        this.name = "Fox";
        this.renderer = new FoxRenderer(5, 16, new Size(20, 16));
        this.movements = new Stack<>();
        this.validTerrains.add(TerrainType.WALKABLE);
    }

    @Override
    public void update() {
        Position oldPos = new Position(position);
        if (position.equals(destination)) {
            destination = null;
        }

        if (destination != null && movements.isEmpty()) {
            movements = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
        }

        if (destination == null) {
            destination = MovementAi.generateRandomDestination(this, validTerrains);
        }

        if (destination != null && !movements.isEmpty()) {
            move(world, destination);
            if(position.equals(oldPos)) destination = null;
        }

    }

    @Override
    public void render(Graphics graphics) {
        if(renderer != null )renderer.render(graphics, this);
    }

    @Override
    public void move(World world, Position destination) {
        if (movements != null && !movements.isEmpty()) {
            Movement mov = movements.pop();
            position.translate(mov.getDx(), mov.getDy());
            world.moveAnimal(this, position);
        }
    }

    @Override
    public void walk(Position position, Position destination) {

    }

    @Override
    public String toString() {
        return "Fox. Id: " + id + ". Position " + position;
    }

}
