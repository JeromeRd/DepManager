package com.dm.tools;

import com.dm.common.ProjectsReferential;
import com.dm.datamodel.Project;
import com.dm.dmenum.Properties;
import com.dm.manager.PropertiesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jrichard on 04/07/2017.
 */
public class ProjectUtilities {

    private final static String[] IGNORED_SUB_PROJECTS = new String[] {".git", "var", "bin ", ".gitignore"};

    public static List<String> getSubProjects(String strProjectPath) {
        List<String> result = new ArrayList<>();
        if (strProjectPath != null) {
            File project = new File(strProjectPath);
            if (project.exists() && project.isDirectory()) {
                String[] fileList = project.list();
                List<String> ignoredFiles = Arrays.asList(IGNORED_SUB_PROJECTS);
                for (String fileName : fileList) {
                    if (!ignoredFiles.contains(fileName)) {
                        result.add(fileName);
                    }
                }
            }
        }
        return result;
    }

    public static Project.ProjectType getProjectType(String strProjectPath) {
        Project.ProjectType result = Project.ProjectType.UNKNOWN;
        if (strProjectPath != null) {
            File project = new File(strProjectPath);
            List<String> ignoredFiles = Arrays.asList(IGNORED_SUB_PROJECTS);
            if (project.exists() && project.isDirectory()) {
                for (File file : project.listFiles()) {
                    if (ProjectsReferential.GRADLE_FILE.equals(file.getName())) {
                        result = Project.ProjectType.GRADLE;
                    } else if (ProjectsReferential.MAVEN_FILE.equals(file.getName())) {
                        result = Project.ProjectType.MAVEN;
                    } else {
                        if (file.isDirectory() && !ignoredFiles.contains(file.getName())) {
                            result = getProjectType(strProjectPath + "\\" + file.getName());
                        }
                    }

                    if (!result.equals(Project.ProjectType.UNKNOWN)) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static String niceName(String name) {
        String scriptPrefix = PropertiesManager.getProperty(Properties.SCRIPT_PREFIX.getKey());
        name = name.replace(scriptPrefix, "");

        String scriptExtension = PropertiesManager.getProperty(Properties.SCRIPT_EXTENSION.getKey());
        name = name.replace(scriptExtension, "");

        return name;
    }
}
