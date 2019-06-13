package com.evilcorp.imagerecognition;

import com.evilcorp.imagerecognition.util.ConfigurationUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import org.apache.log4j.Logger;

import sun.awt.OSInfo;

/**
 *
 * @author BaceK
 */
public class Constants {
    private static Logger log = Logger.getLogger(Constants.class);

    private static final String OS_TYPE = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

    public static String PATH_TO_NATIVE_LIB_WIN = getProperty("PATH_TO_NATIVE_LIB_WIN");
    public static String PATH_TO_ORIGIN_IMAGE = getProperty("PATH_TO_ORIGIN_IMAGE");
    public static String PATH_TO_RESULT_IMAGE = getProperty("PATH_TO_RESULT_IMAGE");
    
    
    /**
     *
     * @return
     */
    public static OSInfo.OSType getOperatingSystemType(){
        log.debug("OS TYPE: " + OS_TYPE);
        if ((OS_TYPE.contains("mac")) || (OS_TYPE.contains("darwin"))) {
            return OSInfo.OSType.MACOSX; }
        else if (OS_TYPE.contains("win")) {
            return OSInfo.OSType.WINDOWS; }
        else if (OS_TYPE.contains("nux")) {
            return OSInfo.OSType.LINUX; }
        else {
            return OSInfo.OSType.UNKNOWN;
        }
    }

    private static String getProperty(String key){
        ConfigurationUtil config = new ConfigurationUtil("/config.properties");
        return config.readConfig(key);
    }
}
