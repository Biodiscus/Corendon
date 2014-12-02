package nl.itopia.corendon.utils;

import java.io.File;
import java.util.UUID;

/**
 * © 2014, Biodiscus.net robin
 */
public class FileUtil {
    public static String randomFileName() {
        return randomFileName(16);
    }

    public static String randomFileName(int length) {
        return UUID.randomUUID().toString();
    }

    public static String getFileExtension(String string) {
        return getFileExtension(new File(string));
    }

    // http://stackoverflow.com/a/21974043
    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");

        if(lastIndex == -1) return "";

        return name.substring(lastIndex + 1);
    }
}
