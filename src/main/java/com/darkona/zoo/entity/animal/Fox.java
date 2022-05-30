package com.darkona.zoo.entity.animal;

import com.darkona.zoo.Movement;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.render.ImageUtils;
import com.darkona.zoo.world.World;
import com.darkona.zoo.world.terrain.TerrainType;
import lombok.NoArgsConstructor;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Fox extends Animal implements Walker {

    private static final Image foxImg = ImageUtils.loadImage("/animal/fox.png");
    private final ArrayList<TerrainType> validTerrains = new ArrayList<>();
    private Stack<Movement> movements;

    private final int speed = 2;
    private int updateStep = 0;
    public Fox(World world, Position position) {
        super(world, position, new Size(1));
        this.name = "Fox";
        this.u = 5;
        this.v = 16;
        this.imageSize = new Size(20);
        this.movements = new Stack<>();
        this.validTerrains.add(TerrainType.WALKABLE);
    }

    public boolean hasDestination() {return destination != null;}

    public boolean hasMovements() {return !movements.isEmpty();}

    @Override
    public void update() {
        if (updateStep == 0){
            if (!hasDestination()) {
                Logger.info("No destination. Generating a new destination.");
                destination = MovementAi.generateRandomDestination(this, validTerrains);
                Logger.info("New destination: " + destination);
            }

            if (position.equals(destination)) {
                Logger.info("Arrived at destination. Destination is now null.");
                destination = null;
            }
            if (hasDestination() && !hasMovements()) {
                Logger.info(String.format("Fox %s has destination %s and no route, tracing shortest route there.", this, destination));
                movements = MovementAi.traceShortestRouteToDestination(destination, this, validTerrains);
                String msg = "Movements > " + Arrays.stream(movements.toArray()).map(Object::toString).collect(Collectors.joining(","));
                Logger.info(msg);
                if(movements.isEmpty()){
                    Logger.info("Empty route, eliminating destination because it's unreachable.");
                    destination = null;
                }
            }
        }
        if(updateStep == 1){
            if (hasDestination() && hasMovements()) {
                Position oldPos = new Position(position);
                move(world, destination);
                if (position.equals(oldPos)) {
                    Logger.info("Couldn't move, eliminating destination and route");
                    destination = null;
                    movements = new Stack<>();
                }
            }
        }
        updateStep = (updateStep + 1) % speed;

    }

    @Override
    public void move(World world, Position destination) {
        Movement mov = movements.pop();
        position.translate(mov.getDx(), mov.getDy());
        world.moveAnimal(this, position);
    }

    @Override
    public Image getImage() {
        return Fox.foxImg;
    }

    @Override
    public void walk(Position position, Position destination) {}

    @Override
    public String toString() {return "Fox. Id: " + id + ". Position " + position;}


}
