package com.darkona.zoo.world;

import com.darkona.zoo.animal.Animal;
import com.darkona.zoo.common.Coordinates;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.interfaces.Renderable;
import com.darkona.zoo.interfaces.Updatable;
import com.darkona.zoo.simulation.Player;
import com.darkona.zoo.terrain.Terrain;
import com.darkona.zoo.vegetation.Vegetation;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class World implements Updatable, Renderable{

    private List<Updatable> updatables;
    private List<Renderable> renderables;
    private List<Updatable> updatedTings;

    private WorldCell[][] field;
    private Size size;

    public World(Size size){
        this.size = size;
        this.field = new WorldCell[size.width][size.height];
        updatables = new ArrayList<>();
        renderables = new ArrayList<>();
        for (int i = 0 ; i < field.length; i++) {
            WorldCell[] cells = field[i];
            for (int j = 0; j < cells.length; j++) {
                cells[j] = new WorldCell(new Coordinates(i, j));
                renderables.add(cells[j].getFloor());
            }
        }
    }

    public void moveThing(WorldThing thing, Coordinates oldCoords){

        int exists = field[thing.position.x][thing.position.y].exists(thing);
        if( exists > -1){
            switch (exists){
                case 0:
                case 1:
                case 2: setAnimal((Animal)thing);break;
                case 3: setPlayer((Player)thing);break;
                case 4: setTerrain((Terrain) thing);break;
                case 5: setVegetation((Vegetation) thing);break;

            }
            removeThingFrom(oldCoords, thing);
        }

    }

    public void removeThingFrom(Coordinates c, WorldThing thing){
        try{
        field[c.x][c.y].remove(thing);}
        catch (Exception e){}
    }

    public boolean setAnimal(Animal a){
        return field[a.position.x][a.position.y].setAnimal(a);

    }

    public void setTerrain(Terrain t){
        field[t.position.x][t.position.y].setFloor(t);
        addToWorld(t);
    }
    public void setVegetation(Vegetation v){
        field[v.position.x][v.position.x].setVegetation(v);

    }

    public void setPlayer(Player player) {
        field[player.position.x][player.position.y].setPlayer(player);
    }

    public void addToWorld(WorldThing thing){
        if(thing instanceof Renderable){
            renderables.add((Renderable) thing);
        }
        if(thing != null){
            updatables.add(thing);
        }
    }

    @Override
    public void render(Graphics graphics) {
        renderables.forEach(renderable -> renderable.render(graphics));
    }

    @Override
    public Image getSprite(Graphics graphics) {
        return null;
    }


    @Override
    public void update() {
        for(Updatable updatable : updatables){
            updatable.update();
        }
    }

}
