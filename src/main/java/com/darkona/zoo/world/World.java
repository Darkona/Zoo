package com.darkona.zoo.world;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.ai.interfaces.Updatable;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.vegetation.Bush;
import com.darkona.zoo.entity.vegetation.Vegetation;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.terrain.Dirt;
import com.darkona.zoo.world.terrain.Grass;
import com.darkona.zoo.world.terrain.Lava;
import com.darkona.zoo.world.terrain.Terrain;
import com.darkona.zoo.world.terrain.Water;
import lombok.Data;

@Data
public class World implements Updatable, Renderable {

    private List<Updatable> updatables;
    private Stack<Updatable> addedToWorld;
    private List<Updatable> updatedTings;

    private WorldCell[][] field;
    private List<WorldCell> worldCells;
    private Size size;

    public World(Size size) {
        this.size = size;
        this.field = new WorldCell[size.width][size.height];
        this.updatables = new ArrayList<>();
        this.addedToWorld = new Stack<>();
        this.worldCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            WorldCell[] cells = field[i];
            for (int j = 0; j < cells.length; j++) {
                int gauss = (int) Math.floor(Simulation.RANDOM.nextGaussian() * 10);
                Position pos = new Position(i, j);
                switch (Math.abs(gauss)) {
                    case 0:
                    case 1:
                    case 2:
                        cells[j] = new WorldCell(this, new Dirt(this, pos));
                        break;

                    case 3:
                    case 4:
                    case 5:
                        cells[j] = new WorldCell(this, new Lava(this, pos));
                        break;
                    case 6:
                    case 7:
                    case 8:
                        cells[j] = new WorldCell(this, new Water(this, pos));
                    default:
                        cells[j] = new WorldCell(this, new Grass(this, pos));
                        if (Simulation.RANDOM.nextInt(10) == 6) {
                            cells[j].setVegetation(new Bush(this, pos));
                        }
                        break;
                }
                worldCells.add(cells[j]);
            }
        }
        for (WorldCell cell : worldCells) {
            cell.fillNeighbors(getField());
        }
    }

    public WorldCell getCellAt(Position pos) {
        try {
            return field[pos.x][pos.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public void movePlayer(Player p, Position oldPos) {
        if (getCellAt(p.position) != null && !getCellAt(p.position).hasPlayer(p)) {
            getCellAt(p.position).setPlayer(p);
            removePlayerFrom(p, oldPos);
        }
    }

    public void moveAnimal(Animal a, Position oldPos) {
        Position pos = a.position;
        if (getCellAt(pos) != null && !getCellAt(pos).hasAnimal(a)) {
            if (getCellAt(pos).setAnimal(a)) {
                getCellAt(pos).removeAnimal(a);
            }
            else {
                a.position = oldPos;
            }
        }
    }

    public void moveThing(WorldThing thing, Position oldCoords) {
        if (thing instanceof Animal) {
            moveAnimal((Animal) thing, oldCoords);
        }
        if (thing instanceof Player) {
            movePlayer((Player) thing, oldCoords);
        }
    }

    public void removePlayerFrom(Player p, Position pos) {
        if (getCellAt(pos) == null) {
            return;
        }
        if (getCellAt(pos).hasPlayer(p)) {
            getCellAt(pos).setPlayer(null);
        }
    }

    public void setAnimal(Animal a) {
        if (field[a.position.x][a.position.y].setAnimal(a)) {
            addToWorld(a);
        }
    }

    public void setTerrain(Terrain t) {
        field[t.position.x][t.position.y].setFloor(t);
        addToWorld(t);
    }

    public void setVegetation(Vegetation v) {
        field[v.position.x][v.position.x].setVegetation(v);
    }

    public void setPlayer(Player player) {
        field[player.position.x][player.position.y].setPlayer(player);
    }

    public void addToWorld(WorldThing thing) {
        if (thing != null) {
            addedToWorld.push(thing);
        }
    }

    @Override
    public void render(Graphics graphics) {
        worldCells.forEach(cell -> cell.render(graphics));
    }

    @Override
    public void update() {

        while (!addedToWorld.isEmpty()) {
            updatables.add(addedToWorld.pop());
        }

        updatables.forEach(updatable -> {
                    if (updatable instanceof Player) {
                        updatable.update();
                    }
                    else if (!Simulation.PAUSED) {
                        updatable.update();
                    }
                });

        worldCells.forEach(WorldCell::update);
    }

}
