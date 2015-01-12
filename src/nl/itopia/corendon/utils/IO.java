package nl.itopia.corendon.utils;

import java.net.URL;

/**
 * © 2014, Biodiscus.net robin
 */
public class IO {
    
    public static URL get(String path) {
        return get(path, true);
    }

    public static URL get(String path, boolean useClassLoader) {
        if(useClassLoader) {
            return IO.class.getClassLoader().getResource(path);
        } else {
            return IO.class.getResource(path);
        }
    }
}
