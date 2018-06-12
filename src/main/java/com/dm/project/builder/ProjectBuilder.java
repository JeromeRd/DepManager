package com.dm.project.builder;

import com.dm.common.ProjectsReferential;
import com.dm.datamodel.MavenProfile;
import com.dm.datamodel.Profile;
import com.dm.datamodel.Project;
import com.dm.reader.PomReader;
import com.dm.strategy.ProjectStrategy;
import com.dm.strategy.SimpleDeployStrategy;
import com.dm.writer.ProfilesWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 06/03/2018.
 */
public class ProjectBuilder {
    private String name;
    private Project.ProjectState state;
    private String path;
    private Profile profile;

    /**
     * Create a project and recursively its sub projects
     * @return project list : the main project at first index followed by its sub projects
     * @param recursively
     */
    public List<Project> create(boolean recursively) {
        List<Project> projectList = new ArrayList<>();

        Project project = new Project(name);
        project.setState(state);
        project.setPath(path);
        if (profile == null) {
            profile = project.createProfile();
            profile.setProjectDeployStrategy(new SimpleDeployStrategy());
            profile.setCommand(null);
            profile.setStrategy(ProjectStrategy.SIMPLE_STRATEGY);
            profile.setSubProject(null);
            ProfilesWriter.overwrite(profile);
        }
        project.setProfileId(profile.getId());
        projectList.add(project);

        if (recursively) {
            //Find modules inside Maven project
            if (profile instanceof MavenProfile) {
                PomReader pomReader = new PomReader(project.getPath() + "\\" + ProjectsReferential.MAVEN_FILE);
                for (String moduleName : pomReader.getModules()) {
                    ProjectBuilder projectBuilder = new ProjectBuilder();
                    projectList.addAll(projectBuilder.setName(moduleName).setState(Project.ProjectState.NOT_FILLED)
                                               .setPath(path + "\\" + moduleName).create(recursively));
                }
            }
        }
        return projectList;
    }

    public ProjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder setState(Project.ProjectState state) {
        this.state = state;
        return this;
    }

    public ProjectBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public ProjectBuilder setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}


