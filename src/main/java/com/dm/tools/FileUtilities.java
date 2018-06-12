package com.dm.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 30/06/2017.
 */

public class FileUtilities {
    public static List<String> getFiles(String path) {
        List<String> result = new ArrayList<>();
        if (path != null) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                File[] fileList = dir.listFiles();
                for (File file : fileList) {
                    if (file.isFile()) {
                        result.add(file.getName());
                    }
                }
            }
        }
        return result;
    }
}
