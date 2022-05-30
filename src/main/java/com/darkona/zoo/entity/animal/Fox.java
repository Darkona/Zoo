package com.darkona.zoo.entity.animal;

import com.darkona.zoo.Movement;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.render.renderer.entity.FoxRenderer;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.terrain.TerrainType;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;


public class Fox extends Animal implements Walker {

    private final FoxRenderer renderer;
    private Stack<Movement> movs;
    private final ArrayList<TerrainType> validTerrains = new ArrayList<>();

    public Fox(World world, Position position) {
        super(world, position, new Size());
        this.name = "Fox";
        this.renderer = new FoxRenderer(5, 16, new Size(20, 16));
        this.movs = new Stack<>();
        this.validTerrains.add(TerrainType.WALKABLE);
    }

    @Override
    public void update() {
        if(position.equals(destination)) destination = null;

        if(destination != null && movs.isEmpty())
            movs = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);

        if (destination == null) {
            Logger.debug("No destination. Generating new destination.");
            destination = MovementAi.generateRandomDestination(this, validTerrains);
            if (destination != null) {
                Logger.debug("New destination: " + destination);
            }
        }
        if(destination != null) move(world, destination);
    }

    @Override
    public void render(Graphics graphics) {
        renderer.render(graphics, this);
    }

    @Override
    public void move(World world, Position destination) {
        if (movs != null && !movs.isEmpty()) {
            Movement mov = movs.pop();
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
