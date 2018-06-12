package com.dm.manager;

import com.dm.datamodel.Project;
import com.dm.exception.ProjectNotFoundException;
import com.dm.reader.ProjectsReader;
import com.dm.tools.FileUtilities;
import com.dm.tools.ProjectUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ProjectManager {
    public static boolean isProjectAlreadyAdded(String projectName) {
        for (Project project : ProjectsReader.read()) {
            if (project.getName() != null && project.getName().equals(projectName)) {
                return true;
            }
        }
        return false;
    }

    public static Project findByProfile(UUID profileId) throws ProjectNotFoundException {
        for (Project project : ProjectsReader.read()) {
            if (project.getProfileId() != null && project.getProfileId().equals(profileId)) {
                return project;
            }
        }
        throw new ProjectNotFoundException();
    }

    public static List<Project> findProjectsInWorkspace(String workspace) {
        List<Project> result =new ArrayList<>();
        for (String projectName : FileUtilities.getFiles(workspace)) {
            Project project = new Project(ProjectUtilities.niceName(projectName));
            result.add(project);
        }
        return result;
    }
}
