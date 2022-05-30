package com.darkona.zoo;

import com.darkona.zoo.common.Size;
import com.darkona.zoo.simulation.Loop;
import com.darkona.zoo.simulation.Simulation;
import com.github.tobiasrm.ColoredConsoleWriter;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

public class Main {

    public static void main(String[] args) {
        Logger.debug("Initializing...");
        configureTinyLog();
        new Thread(new Loop(new Simulation(new Size( Configuration.getInstance().getWidth(),  Configuration.getInstance().getHeight())))).start();
    }

    private static void configureTinyLog() {
        Logger.debug("Configuring logger...");
        Configurator.currentConfig().removeAllWriters().addWriter(new ColoredConsoleWriter()).activate();
        Configurator.currentConfig().level(Level.INFO).activate();
        Configurator.currentConfig()
                .formatPattern("{{level}:|min-size=8} {date} - {class_name}.class>{method}>{line}|  |{message}")
                .activate();
    }
}
