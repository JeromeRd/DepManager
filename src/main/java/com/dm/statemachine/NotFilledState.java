package com.dm.statemachine;

import com.dm.datamodel.Project;
import com.dm.ui.swing.component.ProjectComponent;
import com.dm.ui.swing.component.ProjectComponentRegistry;
import com.dm.writer.ProjectsWriter;

import javax.swing.*;

/**
 * Created by jrichard on 12/07/2017.
 */
public class NotFilledState implements State {
    private static NotFilledState instance;

    public static void initialize() {
        instance = new NotFilledState();
    }

    public static State getInstance() {
        if(null == instance) {
            throw new IllegalStateException("NotFilledState initialization not done");
        }
        return instance;
    }

    @Override
    public void on(Project project) {
        project.setState(Project.ProjectState.NOT_FILLED);
        ProjectsWriter.overwrite(project);
        ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);
        if (projectComponent != null) {
            projectComponent.setBorder(BorderFactory.createDashedBorder(null));
        }
    }

    @Override
    public void off(Project project) {

    }
}
