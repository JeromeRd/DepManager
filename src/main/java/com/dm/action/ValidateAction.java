package com.dm.action;

import com.dm.datamodel.Project;
import com.dm.manager.ProjectManager;
import com.dm.writer.ProjectsWriter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 01/08/2017.
 */
class ValidateAction implements Action {
    private JComboBox<String> projectList;
    private JDialog jDialog;

    public ValidateAction(JDialog jDialog, JComboBox<String> projectList) {
        this.jDialog = jDialog;
        this.projectList = projectList;
    }

    @Override
    public Object execute() {
        List<Project> selectedProjects = new ArrayList<>();

        for (Object projectName : projectList.getSelectedObjects()) {
            if (ProjectManager.isProjectAlreadyAdded((String) projectName)) {
                //TODO notif that project is already added
            } else {
                Project project = new Project((String) projectName);
                project.setState(Project.ProjectState.NOT_FILLED);
                selectedProjects.add(project);
            }
        }
        ProjectsWriter.write(selectedProjects, false);

        jDialog.setVisible(false);
        return true;
    }
}