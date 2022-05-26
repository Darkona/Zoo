package com.darkona.zoo;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.simulation.Loop;
import com.darkona.zoo.simulation.Simulation;

public class Main {
    public static final boolean PRINT_FPS = false;
    public static final int rate = 4;

    public static void main(String[] args) {
        Size size = new Size(50,50);
        Simulation simulation = new Simulation(size);
        Thread mainThread = new Thread(new Loop(simulation));
        mainThread.start();
    }
}
