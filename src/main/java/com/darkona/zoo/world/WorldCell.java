package com.darkona.zoo.world;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.interfaces.Flier;
import com.darkona.zoo.entity.interfaces.Swimmer;
import com.darkona.zoo.entity.interfaces.Walker;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.vegetation.NoVegetation;
import com.darkona.zoo.entity.vegetation.Vegetation;
import com.darkona.zoo.world.terrain.*;
import lombok.Data;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@SuppressWarnings("rawtypes")
public class WorldCell implements Renderable {

    private Terrain floor;
    private Vegetation vegetation;
    private Animal[] entities;
    private Position position;
    private Player player;
    Random random;
    Map<Class, TerrainType> terrainMap;

    public WorldCell(Position position) {
        random = new Random();
        terrainMap = new HashMap<>();
        terrainMap.put(Swimmer.class, TerrainType.SWIMMABLE);
        terrainMap.put(Walker.class, TerrainType.WALKABLE);
        terrainMap.put(Flier.class, TerrainType.FLYABLE);
        if(position.x < 10 && position.y < 10){
            floor = new Dirt(position);
        }else if(position.x > 40 || position.y > 40){
            floor = new Grass(position);
        }else{
            switch (random.nextInt(2)) {
                case 0: floor = new Grass(position); break;
                case 2: floor = new Water(position);break;
                default: floor = new Dirt(position);break;
            }
        }
        vegetation = new NoVegetation(position);
        entities = new Animal[3];
        this.position = position;
    }

    public WorldCell(Terrain terrain){
        random = new Random();
        terrainMap = new HashMap<>();
        terrainMap.put(Swimmer.class, TerrainType.SWIMMABLE);
        terrainMap.put(Walker.class, TerrainType.WALKABLE);
        terrainMap.put(Flier.class, TerrainType.FLYABLE);
        vegetation = new NoVegetation(terrain.getPosition());
        entities = new Animal[3];
        this.position = terrain.getPosition();
        floor = terrain;
    }


    public int canPutAnimal(Animal a) {
        return terrainMap.entrySet().stream()
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
            a.setCurrentCell(this);
            return true;
        }
        return false;
    }


    @Override
    public void render(Graphics graphics) {
        floor.render(graphics);
        vegetation.render(graphics);
        Arrays.stream(entities).forEach(e -> {
            if (e != null) e.render(graphics);
        });
        if (player != null) player.render(graphics);
    }


    public void remove(WorldThing thing) {
        int found = exists(thing);
        if(found > -1) {
            switch (found) {
                case 0:
                case 1:
                case 2: this.entities[found] = null; break;
                case 3: this.player = null; break;
                case 4: this.floor = null; break;
                case 5: this.vegetation = null; break;
            }
        }
    }
}
