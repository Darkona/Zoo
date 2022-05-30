package com.darkona.zoo.simulation;

import com.darkona.zoo.Configuration;
import lombok.Data;
import org.pmw.tinylog.Logger;

@Data
public class Loop implements Runnable{

    private final Simulation simulation;
    private final Configuration configuration = Configuration.getInstance();
    private boolean running;
    private final double updateRate = 1.0d/configuration.getRate();
    private final double renderRate = 1.0d/12.0d;
    private long nextStatTime;
    private int fps, ups;

    public Loop(Simulation simulation){
        this.simulation = simulation;
    }
    @Override
    public void run() {
        long now = System.currentTimeMillis();
        running = true;
        double upsAcc = 0, fpsAcc = 0;
        long currentTime, lastUpdate = now;
        nextStatTime = now + 1000;

        while(running){
            currentTime = System.currentTimeMillis();
            upsAcc += (currentTime - lastUpdate) / 1000d;
            fpsAcc += (currentTime - lastUpdate) / 1000d;
            lastUpdate = currentTime;

            if(upsAcc > updateRate){
                while(upsAcc > updateRate){
                    update();
                    upsAcc -= updateRate;
                }
            }
            if(fpsAcc > renderRate){
                render();
                fpsAcc = 0;
            }
            printStats();
        }
    }

    private void printStats() {
        if(configuration.isPrintStats() && System.currentTimeMillis() > nextStatTime) {
            Logger.info(String.format("FPS: %d, UPS: %d", fps, ups));
            fps = ups = 0;
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
