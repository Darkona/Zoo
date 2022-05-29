package com.darkona.zoo.world;

import com.darkona.zoo.common.Direction;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.interfaces.Flier;
import com.darkona.zoo.entity.interfaces.Swimmer;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Vegetation;
import com.darkona.zoo.world.terrain.*;
import lombok.Data;

import java.awt.*;
import java.util.*;

@Data
@SuppressWarnings("rawtypes")
public class WorldCell implements Renderable {

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

    public WorldCell(World world, Position position) {
        if (position.x < 10 && position.y < 10) {
            floor = new Dirt(world, position);
        } else if (position.x > 40 || position.y > 40) {
            floor = new Grass(world, position);
        } else {
            switch (random.nextInt(2)) {
                case 0:
                    floor = new Grass(world, position);
                    break;
                case 2:
                    floor = new Water(world, position);
                    break;
                default:
                    floor = new Dirt(world, position);
                    break;
            }
        }
        vegetation = new NoVegetation(world, position);
        entities = new Animal[3];
        this.position = position;
        this.world = world;
    }

    public WorldCell(World world, Terrain terrain) {
        vegetation = new NoVegetation(world, terrain.getPosition());
        entities = new Animal[3];
        this.position = terrain.getPosition();
        floor = terrain;
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


    public boolean hasPlayer(){
        return player != null;
    }

    public boolean hasPlayer(Player p){
        return player != null && player.equals(p);
    }

    public int exists(WorldThing thing) {
        try {
            if (thing == null) return -2;
            if (thing instanceof Swimmer && entities[0].equals(thing)) return 0;
            if (thing instanceof Walker && entities[1].equals(thing)) return 1;
            if (thing instanceof Flier && entities[2].equals(thing)) return 2;
            if (thing instanceof Player && hasPlayer((Player) thing)) return 3;
            if (thing instanceof Terrain && floor.equals(thing)) return 4;
            if (thing instanceof Vegetation && vegetation.equals(thing)) return 5;
            return -1;
        } catch (Exception e) {
            return -3;
        }

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
        vegetation.render(graphics);
        if(entities[0]!=null)entities[0].render(graphics);
        if(entities[1]!=null)entities[1].render(graphics);
        if(entities[2]!=null)entities[2].render(graphics);
        if (player != null) player.render(graphics);
    }


    public void remove(WorldThing thing) {
        int found = exists(thing);
        if (found > -1) {
            switch (found) {
                case 0:
                case 1:
                case 2:
                    this.entities[found] = null;
                    break;
                case 3:
                    this.player = null;
                    break;
                case 4:
                    this.floor = null;
                    break;
                case 5:
                    this.vegetation = null;
                    break;
            }
        }
    }

    public HashMap<Direction, WorldCell> getNeighbors(){
        return neighbors;
    }
    @Override
    public String toString(){
        return String.format("Cell at %s === Floor: %s", position, floor.getName());
    }

    public boolean hasAnimal(Animal a) {
        for (Animal b: entities) {
            if(b!=null && b.equals(a)){
                return true;
            }
        }
        return false;
    }
}
