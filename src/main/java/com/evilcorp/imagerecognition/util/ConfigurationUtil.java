package com.evilcorp.imagerecognition.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigurationUtil {

    private String defaultConfigPath;
    private Properties configuration = new Properties();


    public ConfigurationUtil(String configPath) throws IllegalArgumentException {
        if (configPath == null){
            throw new IllegalArgumentException("configPath cannot be NULL");
        }
        this.defaultConfigPath = configPath;
    }

    public String readConfig(String key){
        try {
            return getConfiguration().getProperty(key);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Properties getConfiguration() throws IOException{
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    private void loadConfiguration() {
        try (InputStream in = ConfigurationUtil.class.getResourceAsStream(defaultConfigPath)) {
            configuration.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
