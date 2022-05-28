package com.darkona.zoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.pmw.tinylog.Logger;

@EqualsAndHashCode(callSuper = true)
@Data
public class Configuration extends Properties {

    private static final String path = "/config/configuration.properties";
    private static Configuration instance;

    public static Configuration getInstance(){
        if(instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private int rate = 5;
    private boolean printStats = true;
    private boolean enableLog = false;

    private Configuration(){

        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
            this.load(inputStream);
            System.out.print("Host name is: " + getProperty("rate"));
        } catch (Exception ex) {
            Logger.error("Couldn't load configuration. Cause: " + ex.getMessage());
        }
    }

}
