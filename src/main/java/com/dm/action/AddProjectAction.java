package com.dm.action;

import com.dm.common.EnvironmentVariables;
import com.dm.datamodel.MavenProfile;
import com.dm.datamodel.Profile;
import com.dm.datamodel.Project;
import com.dm.manager.EnvironmentVariableManager;
import com.dm.manager.FileManager;
import com.dm.manager.ProjectManager;
import com.dm.manager.PropertiesManager;
import com.dm.dmenum.Properties;
import com.dm.project.builder.ProjectBuilder;
import com.dm.reader.PomReader;
import com.dm.tools.ProjectUtilities;
import com.dm.writer.ProjectsWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 30/06/2017.
 */
public class AddProjectAction implements Action {

    private JFrame mainFrame;

    public AddProjectAction(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Object execute() {
        JFileChooser jFileChooser = createFileChooser();
        jFileChooser.showOpenDialog(mainFrame);

        List<Project> selectedProjects = new ArrayList<>();
        File projectFile = jFileChooser.getSelectedFile();
        if (ProjectManager.isProjectAlreadyAdded(projectFile.getName())) {
            //TODO notif that project is already added
        } else {
            ProjectBuilder projectBuilder = new ProjectBuilder();
            //TODO ask user to add recursively or not
            //False by default
            boolean recursively = false;
            List<Project> projects = projectBuilder.setName(projectFile.getName()).setState(Project.ProjectState.NOT_FILLED)
                    .setPath(projectFile.getPath()).create(recursively);
            selectedProjects.addAll(projects);
        }
        ProjectsWriter.write(selectedProjects, false);
        return true;
    }

    private JFileChooser createFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();

        //Git repository
        File gitDir = new File(EnvironmentVariableManager.get(EnvironmentVariables.GIT_DIRECTORY));
        if (gitDir.exists()) {
            jFileChooser.setCurrentDirectory(gitDir);
        } else {
            System.out.println("No environment variable "+EnvironmentVariables.GIT_DIRECTORY+" found");
        }
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);

        return jFileChooser;
    }

    private JDialog createProjectListDialog() {
        JDialog projectListDialog = new JDialog();
        projectListDialog.setModal(false);
        projectListDialog.setLocationRelativeTo(mainFrame);
        Dimension dimension = new Dimension(300, 100);
        projectListDialog.setPreferredSize(dimension);
        projectListDialog.setMaximumSize(dimension);
        projectListDialog.setMinimumSize(dimension);

        JComboBox projectList = new JComboBox();
        dimension = new Dimension(100, 30);
        projectList.setPreferredSize(dimension);
        projectList.setMaximumSize(dimension);
        projectList.setMinimumSize(dimension);

        List<File> projects = FileManager.getDirectories(PropertiesManager.getProperty(Properties.SCRIPT_WORKSPACE.getKey()));
        for (File file : projects) {
            String name = ProjectUtilities.niceName(file.getName());
            projectList.addItem(name);
        }
        projectListDialog.add(projectList);
        createMenu(projectListDialog, projectList);
        return projectListDialog;
    }

    private void createMenu(JDialog jDialog, JComboBox projectList) {
        ValidateMenuItem validateItem = new ValidateMenuItem(jDialog, projectList);

        JMenu menu = new JMenu("Menu");
        menu.add(validateItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        jDialog.setJMenuBar(menuBar);
    }

    class ValidateMenuItem extends JMenuItem {
        ValidateMenuItem(JDialog jDialog, JComboBox projectList) {
            super("Validate");
            addActionListener(new ValidateActionListener(jDialog, projectList));
        }
    }

    class ValidateActionListener implements ActionListener {
        private ValidateAction validateAction;

        public ValidateActionListener(JDialog jDialog, JComboBox<String> projectList) {
            this.validateAction = new ValidateAction(jDialog, projectList);
        }

        public void actionPerformed(ActionEvent e) {
            validateAction.execute();
        }
    }
}
