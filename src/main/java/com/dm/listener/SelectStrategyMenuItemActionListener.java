package com.dm.listener;

import com.dm.converter.ProfileConverter;
import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.datamodel.Project;
import com.dm.exception.ProfileNotFoundException;
import com.dm.exception.ProjectNotFoundException;
import com.dm.reader.ProfilesReader;
import com.dm.reader.ProjectsReader;
import com.dm.statemachine.ProjectStateMachine;
import com.dm.strategy.ProjectStrategy;
import com.dm.strategy.SimpleDeployStrategy;
import com.dm.writer.ProfilesWriter;
import com.dm.writer.ProjectsWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Created by jrichard on 03/07/2017.
 */
public class SelectStrategyMenuItemActionListener implements ActionListener {
    private UUID projectId;
    private ProjectStrategy projectStrategy;

    public SelectStrategyMenuItemActionListener(UUID projectId, ProjectStrategy projectStrategy) {
        this.projectId = projectId;
        this.projectStrategy = projectStrategy;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = null;
        try {
            project = ProjectsReader.find(projectId);
            if (project != null) {
                Profile profile = project.createProfile();
                if (project.getProfileId() != null) {
                    ProfileProxy profileProxy = ProfilesReader.find(project.getProfileId());
                    if (profileProxy != null) {
                        ProfileConverter.convertToProfile(profile, profileProxy);
                    }
                }

                if (profile == null) {
                    ProjectStateMachine.process(project, Project.ProjectState.IN_ERROR);
                    project.setErrorMessage("Unrecognized project type");
                    System.out.println("The project type is not recognized. Known project types " + Project.ProjectType.values());
                    return;
                }

                selectStrategy(project, profile);

                project.setProfileId(profile.getId());
                ProjectsWriter.overwrite(project);
            }
        } catch (ProjectNotFoundException ex) {
            System.out.println("Project not found with id : " + projectId);
        } catch (ProfileNotFoundException ex) {
            System.out.println("Profile not found with id : " + project.getProfileId());
        }
    }

    private void selectStrategy(Project project, Profile profile) {
        switch (projectStrategy) {
            case SIMPLE_STRATEGY:
                SimpleDeployStrategy simpleDeployStrategy = new SimpleDeployStrategy();
                simpleDeployStrategy.fill(project, profile);
                break;
            default:
                    break;
        }
        ProfilesWriter.overwrite(profile);
    }
}
