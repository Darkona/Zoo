package com.darkona.zoo;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.simulation.Loop;
import com.darkona.zoo.simulation.Simulation;
import com.github.tobiasrm.ColoredConsoleWriter;
import org.pmw.tinylog.Configurator;

public class Main {


    private static final int SQUARE = 30;

    public static void main(String[] args) {
        new Thread(new Loop(new Simulation(new Size(SQUARE, SQUARE)))).start();
    }

    private static void configureTinyLog() {
        Configurator.currentConfig().removeAllWriters().addWriter(new ColoredConsoleWriter()).activate();
        Configurator.currentConfig()
                .formatPattern("{{level}:|min-size=8} {date} - {class_name}.class>{method}>{line}>{message}")
                .activate();
    }
}
