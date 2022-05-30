package com.darkona.zoo.world;

import com.darkona.zoo.common.Direction;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.ai.interfaces.Updatable;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.interfaces.Flier;
import com.darkona.zoo.entity.interfaces.Swimmer;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Vegetation;
import com.darkona.zoo.world.terrain.*;
import lombok.Data;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.*;

@Data
@SuppressWarnings("rawtypes")
public class WorldCell implements Renderable, Updatable {

    private final World world;
    private final Random random = new Random();
    private static final Map<Class, TerrainType> terrainMap = new HashMap<>();
    static {
        terrainMap.put(Swimmer.class, TerrainType.SWIMMABLE);
        terrainMap.put(Walker.class, TerrainType.WALKABLE);
        terrainMap.put(Flier.class, TerrainType.FLYABLE);
    }
    private Terrain floor;
    private Vegetation vegetation;
    private Animal[] entities;
    private Position position;
    private Player player;

    HashMap<Direction, WorldCell> neighbors = new HashMap<>();
    private WorldCell north;
    private WorldCell east;
    private WorldCell south;
    private WorldCell west;

    private boolean[] willRender = new boolean[5];

    public WorldCell(World world, Terrain terrain) {
        this.vegetation = new NoVegetation(world, terrain.getPosition());
        this.entities = new Animal[3];
        this.position = terrain.getPosition();
        this.floor = terrain;
        this.world = world;
    }

    public void fillNeighbors(WorldCell[][] field) {
        west = position.x - 1 >= 0 ? field[position.x - 1][position.y] : null;
        east = position.x + 1 < field.length ? field[position.x + 1][position.y] : null;

        north = position.y - 1 >= 0 ? field[position.x][position.y - 1] : null;
        south = position.y + 1 < field[0].length ? field[position.x][position.y + 1] : null;

        if(north != null) neighbors.put(Direction.NORTH, north);
        if(east != null) neighbors.put(Direction.EAST, east);
        if(south != null) neighbors.put(Direction.SOUTH, south);
        if(west != null) neighbors.put(Direction.WEST, west);
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }

    public boolean isPassable() {
        return floor.getTerrainType() != TerrainType.IMPASSABLE;
    }

    public int canPutAnimal(Animal a) {
        return terrainMap.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(a) && entities[entry.getValue().ordinal()] == null && floor.getTerrainType() == entry.getValue())
                .findFirst().map(entry -> entry.getValue().ordinal())
                .orElse(-1);
    }

    public boolean hasPlayer(Player p){
        return player != null && player.equals(p);
    }

    public boolean setAnimal(Animal a) {
        int placement = canPutAnimal(a);
        if (placement > -1) {
            entities[placement] = a;
            a.setCurrentCell(this);
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics graphics) {
        floor.render(graphics);
        if(willRender[0]) vegetation.render(graphics);
        if(willRender[1]) entities[0].render(graphics);
        if(willRender[2]) entities[1].render(graphics);
        if(willRender[3]) entities[2].render(graphics);
        if(willRender[4]) player.render(graphics);
    }

    public HashMap<Direction, WorldCell> getNeighbors(){
        return neighbors;
    }

    @Override
    public String toString(){
        return String.format("Cell at %s === Floor: %s", position, floor.getName());
    }

    public boolean hasAnimal(Animal a) {
        if(entities[0] != null && entities[0].equals(a)) return true;
        if(entities[1] != null && entities[1].equals(a)) return true;
        return entities[2] != null && entities[2].equals(a);
    }

    public void removeAnimal(Animal a) {
        if(entities[0] != null && entities[0].equals(a)) entities[0] = null;
        if(entities[1] != null && entities[1].equals(a)) entities[1] = null;
        if(entities[2] != null && entities[2].equals(a)) entities[2] = null;
    }

    @Override
    public void update() {
        willRender[0] = vegetation != null;
        willRender[1] = entities[0] != null;
        willRender[2] = entities[1] != null;
        willRender[3] = entities[2] != null;
        willRender[4] = player != null;
    }
}
