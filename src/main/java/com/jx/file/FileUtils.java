package com.jx.file;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for file operations.
 * 
 * @author jx
 */
public class FileUtils {

    /**
     * Gets the project directory path.
     * 
     * @return The canonical path of the project directory
     */
    public static String getProjectDir() {
        File directory = new File("");
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(courseFile);
        return courseFile;
    }
}