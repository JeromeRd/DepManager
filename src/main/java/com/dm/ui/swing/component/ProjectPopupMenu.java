package com.dm.ui.swing.component;

import com.dm.converter.ProfileConverter;
import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.datamodel.Project;
import com.dm.exception.ProfileNotFoundException;
import com.dm.exception.ProjectNotFoundException;
import com.dm.listener.DeployMenuItemActionListener;
import com.dm.listener.RemoveMenuItemActionListener;
import com.dm.reader.ProfilesReader;
import com.dm.reader.ProjectsReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ProjectPopupMenu extends JPopupMenu  {
    private UUID projectId;
    private List<JMenuItem> menuItems;
    private JMenuItem deployItem;

    private final int popupMenuWidth = 150;
    private final int itemHeight = 20;

    public ProjectPopupMenu(UUID projectId) {
        this.projectId = projectId;
        menuItems = new ArrayList<>();

        Dimension itemDimension = new Dimension(popupMenuWidth, itemHeight);

        /*JMenu strategyItem = new JMenu("Select strategy");
        strategyItem.setPreferredSize(itemDimension);
        strategyItem.setMaximumSize(itemDimension);
        strategyItem.setMinimumSize(itemDimension);
        menuItems.add(strategyItem);*/

        /*try {
            String workspace = PropertiesManager.getProperty(Properties.DM_WORKSPACE.getKey());
            String projectName = ProjectsReader.find(projectId).getName();
            if (ProjectUtilities.getSubProjects(workspace + "\\" + projectName).size() == 1) {
                JMenuItem simpleStrategyItem = new JMenuItem("Simple");
                simpleStrategyItem.addActionListener(new SelectStrategyMenuItemActionListener(projectId, ProjectStrategy.SIMPLE_STRATEGY));
                simpleStrategyItem.setPreferredSize(itemDimension);
                simpleStrategyItem.setMaximumSize(itemDimension);
                simpleStrategyItem.setMinimumSize(itemDimension);
                strategyItem.add(simpleStrategyItem);
            } else {
                JMenuItem complexStrategyItem = new JMenuItem("Complex");
                complexStrategyItem.addActionListener(new SelectStrategyMenuItemActionListener(projectId, ProjectStrategy.COMPLEX_STRATEGY));
                complexStrategyItem.setPreferredSize(itemDimension);
                complexStrategyItem.setMaximumSize(itemDimension);
                complexStrategyItem.setMinimumSize(itemDimension);
                strategyItem.add(complexStrategyItem);
            }
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }*/

        deployItem = new JMenuItem("Deploy");
        deployItem.addActionListener(new DeployMenuItemActionListener(projectId));
        deployItem.setPreferredSize(itemDimension);
        deployItem.setMaximumSize(itemDimension);
        deployItem.setMinimumSize(itemDimension);
        menuItems.add(deployItem);

        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(new RemoveMenuItemActionListener(projectId));
        removeItem.setPreferredSize(itemDimension);
        removeItem.setMaximumSize(itemDimension);
        removeItem.setMinimumSize(itemDimension);
        menuItems.add(removeItem);

        int borders = 2;
        int calculatedHeight = menuItems.size() * (itemHeight + borders);
        Dimension popupMenuDimension = new Dimension(popupMenuWidth, calculatedHeight);
        setPreferredSize(popupMenuDimension);
        setMaximumSize(popupMenuDimension);
        setMinimumSize(popupMenuDimension);
    }

    public void createContent() {
        removeAll();
        for (JMenuItem menuItem : menuItems) {
            add(menuItem);
        }
        deployItem.setEnabled(true);
    }
}
