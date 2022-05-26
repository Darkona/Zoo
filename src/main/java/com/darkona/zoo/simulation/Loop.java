package com.darkona.zoo.simulation;

import lombok.Data;

import static com.darkona.zoo.Main.PRINT_FPS;
import static com.darkona.zoo.Main.rate;

@Data
public class Loop implements Runnable{

    private final Simulation simulation;

    private boolean running;

    private final double updateRate = 1.0d/rate;
    private final double renderRate = 1.0d/120.0d;
    private long nextStatTime;
    private int fps, ups;

    public Loop(Simulation simulation){
        this.simulation = simulation;
    }
    @Override
    public void run() {
        long now = System.currentTimeMillis();
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = now;
        nextStatTime = now + 1000;

        while(running){
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if(accumulator > updateRate){
                while(accumulator > updateRate){
                    update();
                    accumulator -= updateRate;
                }
                render();
            }

            printStats();
        }

    }

    private void printStats() {
        if(PRINT_FPS && System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        ups++;
        simulation.update();
    }

    private void render() {
        fps++;
        simulation.render();
    }
}
