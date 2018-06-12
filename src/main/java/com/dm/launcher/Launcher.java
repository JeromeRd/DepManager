package com.dm.launcher;

import com.dm.manager.EnvironmentVariableManager;
import com.dm.manager.PropertiesManager;
import com.dm.reader.EnvironmentVariableReader;
import com.dm.reader.PropertiesReader;
import com.dm.statemachine.ProjectStateMachine;
import com.dm.ui.swing.MainPanel;

import java.awt.*;
import java.io.IOException;

/**
 * Created by jrichard on 28/06/2017.
 */
public class Launcher {
    public static void main(String[] args) {
        warmUp();

        MainPanel mainPanel = new MainPanel();
        Dimension dimension = new Dimension(800, 400);
        mainPanel.setPreferredSize(dimension);
        mainPanel.setMinimumSize(dimension);
        mainPanel.setMaximumSize(dimension);
        mainPanel.createContent();
        mainPanel.refreshProjectContainers();
        mainPanel.setVisible(true);
    }

    private static void warmUp() {
        //Load properties
        try {
            PropertiesManager.load(PropertiesReader.read());
        } catch (IOException e) {
            System.out.println("An error occurred during reading properties : " + e.getMessage());
        }

        //Load environment variables
        EnvironmentVariableManager.load(EnvironmentVariableReader.read());

        //Initialize the project state machine
        ProjectStateMachine.initialize();
    }
}
