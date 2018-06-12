package com.dm.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jrichard on 30/06/2017.
 */
public class FileManager {

    public static List<File> getDirectories(String path) {
        List<File> result = new ArrayList();
        File dir = new File(path);
        if (dir.exists()) {
            result = Arrays.asList(dir.listFiles());
        }
        return result;
    }
}
