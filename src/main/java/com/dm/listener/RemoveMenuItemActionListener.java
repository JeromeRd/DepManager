package com.dm.listener;

import com.dm.action.ActionCommand;
import com.dm.datamodel.Project;
import com.dm.reader.ProjectsReader;
import com.dm.ui.swing.component.ProjectComponentRegistry;
import com.dm.writer.ProjectsWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class RemoveMenuItemActionListener implements ActionListener {
    private UUID projectId;

    public RemoveMenuItemActionListener(UUID projectId) {
        this.projectId = projectId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Project> projects = ProjectsReader.read();
        Iterator<Project> projectIterator = projects.iterator();
        while (projectIterator.hasNext()) {
            Project project = projectIterator.next();
            if (projectId.equals(project.getUuid())) {
                ProjectComponentRegistry.unregister(project);
                projectIterator.remove();
            }
        }
        ProjectsWriter.write(projects, true);
        ActionListenerRegistry.fireActionListeners(ActionCommand.REFRESH_PROJECT_LIST, e);
    }
}
