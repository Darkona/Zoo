package com.darkona.zoo.world;

import com.darkona.zoo.animal.Animal;
import com.darkona.zoo.animal.Flier;
import com.darkona.zoo.animal.Swimmer;
import com.darkona.zoo.animal.Walker;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.simulation.Player;
import com.darkona.zoo.terrain.*;
import com.darkona.zoo.vegetation.NoVegetation;
import com.darkona.zoo.vegetation.Vegetation;
import lombok.Data;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
public class WorldCell implements Renderable {

    private Terrain floor;
    private Vegetation vegetation;
    private Animal[] entities;
    private Coordinates coordinates;
    private Player player;
    Random random;

    public WorldCell(Coordinates coordinates) {
        random = new Random();
        if(coordinates.x < 10 && coordinates.y < 10){
            floor = new Dirt(coordinates);
        }else {
            switch (random.nextInt(3)) {
                case 0:
                    floor = new Grass(coordinates);
                    break;
                case 1:
                    floor = new Dirt(coordinates);
                    break;
                case 2:
                    floor = new Water(coordinates);
                    break;
                default:
                    floor = new Dirt(coordinates);
                    break;
            }
        }
        vegetation = new NoVegetation(coordinates);
        entities = new Animal[3];
        this.coordinates = coordinates;
    }

    @SuppressWarnings("rawtypes")
    public int canPutAnimal(Animal a) {
        Map<Class, TerrainType> map = new HashMap<>();

        map.put(Swimmer.class, TerrainType.SWIMMABLE);
        map.put(Walker.class, TerrainType.WALKABLE);
        map.put(Flier.class, TerrainType.FLYABLE);

        return map.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(a)
                        && entities[entry.getValue().ordinal()] == null
                        && floor.getTerrainType() == entry.getValue())
                .findFirst()
                .map(entry -> entry.getValue().ordinal())
                .orElse(-1);
    }

    public int exists(WorldThing thing) {
        try{
            if (thing == null) return -2;
            if (thing instanceof Swimmer && entities[0].equals(thing)) return 0;
            if (thing instanceof Walker && entities[1].equals(thing)) return 1;
            if (thing instanceof Flier && entities[2].equals(thing)) return 2;
            if (thing instanceof Player && player.equals(thing)) return 3;
            if (thing instanceof Terrain && floor.equals(thing)) return 4;
            if (thing instanceof Vegetation && vegetation.equals(thing)) return 5;
            return -1;
        }catch (Exception e){
            return -3;
        }

    }

    public boolean setAnimal(Animal a) {
        int placement = canPutAnimal(a);
        if (placement > -1) {
            entities[placement] = a;
            return true;
        }
        return false;
    }


    @Override
    public void render(Graphics graphics) {
        floor.render(graphics);
        vegetation.render(graphics);
        Arrays.stream(entities).forEach(e -> {
            if (e != null) {
                e.render(graphics);
            }
        });
        if (player != null) {
            player.render(graphics);
        }
    }


    @Override
    public Image getSprite(Graphics graphics) {
        return null;
    }

    public void remove(WorldThing thing) {
        int found = exists(thing);
        if(found > -1) {
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
}
