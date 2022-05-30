package com.darkona.zoo.simulation;

import com.darkona.zoo.entity.Player;
import com.darkona.zoo.entity.animal.Fox;
import com.darkona.zoo.common.Position;
import com.darkona.zoo.control.Input;
import com.darkona.zoo.common.Size;
import com.darkona.zoo.control.PlayerController;
import com.darkona.zoo.render.Display;
import com.darkona.zoo.world.World;
import lombok.Data;
import lombok.extern.java.Log;
import org.pmw.tinylog.Logger;

import java.util.Random;


@Data
@Log
public class Simulation {

    public static final Random RANDOM = new Random();
    public static boolean PAUSED = false;
    private Display display;
    private World world;
    private Input input;
    private final int amountOfFoxes = 0;
    public static final int SCALE = 10;

    public Simulation(Size size) {
        input = new Input();
        world = new World(size);
        display = new Display(new Size(size.width * SCALE, size.height * SCALE), input);
        Logger.info(String.format("Started simulation with a world of %d by %d. Display will be %d by %d pixels.", size.width,
                size.height, display.getWidth(), display.getHeight()));
        Player player = new Player(world, new PlayerController(input), "Darkona");
        for (int i = 0; i < amountOfFoxes; i++) {
            Fox fox = new Fox(world, new Position(i, 0));
            if (world.setAnimal(fox)) world.addToWorld(fox);
        }
        /*for (int i = 0; i < amountOfFoxes * 2; i++) {
            Chicken chicken = new Chicken(world, new Coordinates(i, 2));
            if (world.setAnimal(chicken)) world.addToWorld(chicken);
        }*/
        world.setPlayer(player);
        world.addToWorld(player);
    }

    public void update() {
        world.update();
    }

    public void render() {
        display.render(this);
    }
}
