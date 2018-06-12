package com.dm.ui.swing;

import com.dm.ui.swing.component.AddProjectMenuItem;

import javax.swing.*;

/**
 * Created by jrichard on 29/06/2017.
 */
public class MenuBarCreator {

    public JMenuBar create(JFrame frame) {
        AddProjectMenuItem addProject = new AddProjectMenuItem(frame);

        JMenu projectMenu = new JMenu("Project");
        projectMenu.add(addProject);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(projectMenu);

        return menuBar;
    }
}
