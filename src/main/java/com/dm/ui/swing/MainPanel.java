package com.dm.ui.swing;

import com.dm.action.ActionCommand;
import com.dm.datamodel.Project;
import com.dm.listener.ActionListenerRegistry;
import com.dm.reader.ProjectsReader;
import com.dm.statemachine.ProjectStateMachine;
import com.dm.ui.swing.component.ProjectComponent;
import com.dm.ui.swing.component.ProjectComponentRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by jrichard on 29/06/2017.
 */
public class MainPanel extends JFrame implements ActionListener {

    public MainPanel() {
        ActionListenerRegistry.add(this, ActionCommand.REFRESH_PROJECT_LIST.name());
        UILoader.getInstance().setFrame(this);
    }

    public void createContent() {
        //Create menu bar
        MenuBarCreator menuBarCreator = new MenuBarCreator();
        setJMenuBar(menuBarCreator.create(this));

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        setLayout(flowLayout);
    }

    public void refreshProjectContainers() {
        List<Project> projects = ProjectsReader.read();

        cleanProjects(this);

        for (Project project : projects) {
            ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);
            if (projectComponent == null) {
                projectComponent = new ProjectComponent(project.getUuid(), project.getName());
                ProjectComponentRegistry.register(projectComponent, project);
                ProjectStateMachine.process(project, Project.ProjectState.NOT_FILLED);
            }
            add(projectComponent);
        }

        revalidate();
        repaint();
    }

    private void cleanProjects(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof ProjectComponent) {
                remove(component);
            } else if (component instanceof Container) {
                cleanProjects((Container) component);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refreshProjectContainers();
    }
}
