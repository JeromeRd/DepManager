package com.dm.ui.swing.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ProjectComponent extends JPanel {
    private JLabel label;
    private UUID projectId;

    public ProjectComponent(UUID projectId, String strLabel) {
        this.projectId = projectId;

        label = new JLabel(strLabel);
        Dimension dimension = new Dimension(300, 20);
        label.setPreferredSize(dimension);
        label.setMaximumSize(dimension);
        label.setMinimumSize(dimension);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new FlowLayout());

        dimension = new Dimension(300, 100);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setBorder(new LineBorder(Color.BLACK));

        ProjectPopupMenu projectPopupMenu = new ProjectPopupMenu(projectId);
        projectPopupMenu.createContent();
        addMouseListener(new ProjectComponentMouseAdapter(projectPopupMenu));

        add(label);
    }

    public ProjectComponent clone() {
        ProjectComponent clone = new ProjectComponent(projectId, label.getText());
        Border border = this.getBorder();
        Border clonedBorder = null;
        if (border instanceof LineBorder) {
            clonedBorder = new LineBorder(((LineBorder)border).getLineColor());
        } else if (border instanceof StrokeBorder) {
            clonedBorder = BorderFactory.createDashedBorder(null);
        }
        clone.setBorder(clonedBorder);
        return clone;
    }

    public UUID getProjectId() {
        return projectId;
    }

    class ProjectComponentMouseAdapter extends MouseAdapter {
        private ProjectPopupMenu projectPopupMenu;

        public ProjectComponentMouseAdapter(ProjectPopupMenu projectPopupMenu) {
            this.projectPopupMenu = projectPopupMenu;
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                projectPopupMenu.createContent();
                projectPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
