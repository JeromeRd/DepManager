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
public class ValidState implements State {
    private static ValidState instance;

    public static void initialize() {
        instance = new ValidState();
    }

    public static State getInstance() {
        if(null == instance) {
            throw new IllegalStateException("ValidState initialization not done");
        }
        return instance;
    }

    @Override
    public void on(Project project) {
        project.setState(Project.ProjectState.VALID);
        ProjectsWriter.overwrite(project);
        ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);
        if (projectComponent != null) {
            projectComponent.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }
    }

    @Override
    public void off(Project project) {

    }
}
