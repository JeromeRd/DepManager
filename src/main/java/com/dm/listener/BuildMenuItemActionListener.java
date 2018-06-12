package com.dm.listener;

import com.dm.converter.ProfileConverter;
import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.datamodel.Project;
import com.dm.exception.ProfileNotFoundException;
import com.dm.exception.ProjectNotFoundException;
import com.dm.reader.ProfilesReader;
import com.dm.reader.ProjectsReader;
import com.dm.visitor.BuildExecutorVisitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Created by jrichard on 03/07/2017.
 */
public class BuildMenuItemActionListener implements ActionListener {
    private UUID projectId;

    public BuildMenuItemActionListener(UUID projectId) {
        this.projectId = projectId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = null;
        try {
            project = ProjectsReader.find(projectId);
            if (project != null) {
                ProfileProxy profileProxy = ProfilesReader.find(project.getProfileId());
                Profile profile = project.createProfile();
                ProfileConverter.convertToProfile(profile, profileProxy);
                profile.execute(new BuildExecutorVisitor());
            }
        } catch (ProjectNotFoundException ex) {
            System.out.println("Project not found with id : " + projectId);
        } catch (ProfileNotFoundException ex) {
            System.out.println("Profile not found with id : " + project.getProfileId());
        }
    }
}
