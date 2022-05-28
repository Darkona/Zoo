package com.darkona.zoo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pmw.tinylog.Logger;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

@EqualsAndHashCode(callSuper = true)
@Data
public class Configuration extends Properties {

    private static Configuration instance;
    private int rate = 5;
    private boolean printStats = false;
    private boolean enableLog = false;

    public static Configuration getInstance() {
        return instance == null ? instance = new Configuration() : instance;
    }

    private Configuration() {
        final String path = "config/configuration.properties";
        try {
            this.load(ClassLoader.getSystemResourceAsStream(path));
            Logger.info("Configuration loaded:");
            this.propertyNames().asIterator().forEachRemaining(f -> Logger.debug(f.toString() + ">> " + getProperty(f.toString())));
           loadConfigs();
        } catch (Exception ex) {
            Logger.error("Couldn't load configuration. Cause: " + ex.getMessage());
        }
    }

    private void loadConfigs(){
        this.rate = Integer.parseInt(getProperty("rate"));
        this.printStats = Boolean.parseBoolean(getProperty("printStats"));
        this.enableLog = Boolean.parseBoolean(getProperty("enableLog"));
    }
}
