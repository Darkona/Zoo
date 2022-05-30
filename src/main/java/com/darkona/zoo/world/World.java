package com.darkona.zoo.world;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.darkona.zoo.common.Position;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.ai.MovementAi;
import com.darkona.zoo.entity.ai.interfaces.Renderable;
import com.darkona.zoo.entity.ai.interfaces.Updatable;
import com.darkona.zoo.entity.animal.Animal;
import com.darkona.zoo.entity.animal.Fox;
import com.darkona.zoo.entity.vegetation.Bush;
import com.darkona.zoo.entity.vegetation.Vegetation;
import com.darkona.zoo.simulation.Simulation;
import com.darkona.zoo.world.terrain.Dirt;
import com.darkona.zoo.world.terrain.Grass;
import com.darkona.zoo.world.terrain.Lava;
import com.darkona.zoo.world.terrain.Terrain;
import com.darkona.zoo.world.terrain.Water;
import lombok.Data;
import org.pmw.tinylog.Logger;

@Data
public class World implements Updatable, Renderable {

    private List<Updatable> updatables;
    private List<Updatable> addedToWorld;
    private List<Updatable> updatedTings;

    private WorldCreator worldCreator;

    private WorldCell[][] field;
    private List<WorldCell> worldCells;
    private Size size;

    public World(Size size) {
        this.worldCreator = new WorldCreator(this);
        this.size = size;
        this.field = new WorldCell[size.width][size.height];
        this.updatables = new ArrayList<>();
        this.addedToWorld = new Stack<>();
        this.worldCells = new ArrayList<>();
        for (int x = 0; x < field.length; x++) {
            WorldCell[] cells = field[x];
            for (int y = 0; y < cells.length; y++) {
                int gauss = (int) Math.floor(Simulation.RANDOM.nextGaussian() * 10);
                Position pos = new Position(x, y);
                Terrain t;
                switch (Math.abs(gauss)) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        t = new Dirt(this, pos);
                        break;


                    case 4:
                    case 5:
                        t = new Lava(this, pos);
                        break;
                    case 6:
                    case 7:

                        t = new Water(this, pos);
                        break;
                    default:
                        t = new Grass(this, pos);
                        break;

                }
                cells[y] = new WorldCell(this, t);
                worldCells.add(cells[y]);
            }
        }
        worldCells.forEach(cell -> cell.fillNeighbors(getField()));
        Logger.info("World created with" + worldCells.size() + " cells.");
        this.setAnimal(new Fox(this, new Position(0,0)));
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
        if (a.getCurrentCell() != null && a.getCurrentCell().hasAnimal(a))
            if (getCellAt(pos).setAnimal(a)) getCellAt(pos).removeAnimal(a);
            else a.position = oldPos;
    }

    public void moveThing(WorldThing thing, Position oldCoords) {
        if (thing instanceof Animal) moveAnimal((Animal) thing, oldCoords);
        if (thing instanceof Player) movePlayer((Player) thing, oldCoords);
    }

    public void removePlayerFrom(Player p, Position pos) {
        if (getCellAt(pos) == null) return;
        if (getCellAt(pos).hasPlayer(p)) getCellAt(pos).setPlayer(null);
    }

    public void setAnimal(Animal a) {
        if (getCellAt(a.position) != null && getCellAt(a.position).setAnimal(a)){
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
            addedToWorld.add(thing);
        }
    }

    @Override
    public void render(Graphics graphics) {
        worldCells.forEach(cell -> cell.getTerrainRenderer().render(graphics, cell.getFloor(), cell.getFloor().getU(), cell.getFloor().getV(),
                cell.getFloor().getImageSize()));
        worldCells.forEach(cell -> cell.getTerrainRenderer().render(graphics, cell.getVegetation(), cell.getVegetation().getU(), cell.getVegetation().getV(),
                cell.getVegetation().getImageSize()));
        worldCells.forEach(cell -> cell.render(graphics));
        worldCells.forEach(cell -> cell.getPlayerRenderer().render(graphics, cell.getPlayer()));

    }

    @Override
    public void update() {

        updatables.addAll(addedToWorld);
        addedToWorld = new ArrayList<>();
        worldCells.forEach(WorldCell::update);
    }

}
