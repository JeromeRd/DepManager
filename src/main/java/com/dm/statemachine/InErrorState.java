package com.dm.statemachine;

import com.dm.datamodel.Project;
import com.dm.ui.swing.component.ProjectComponent;
import com.dm.ui.swing.component.ProjectComponentRegistry;
import com.dm.writer.ProjectsWriter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jrichard on 12/07/2017.
 */
public class InErrorState implements State {
    private static InErrorState instance;

    public static void initialize() {
        instance = new InErrorState();
    }

    public static State getInstance() {
        if(null == instance) {
            throw new IllegalStateException("InErrorState initialization not done");
        }
        return instance;
    }

    @Override
    public void on(Project project) {
        project.setState(Project.ProjectState.IN_ERROR);
        ProjectsWriter.overwrite(project);
        ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);
        if (projectComponent != null) {
            projectComponent.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    @Override
    public void off(Project project) {

    }
}
