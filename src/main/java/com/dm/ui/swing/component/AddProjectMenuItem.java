package com.dm.ui.swing.component;

import com.dm.action.ActionCommand;
import com.dm.action.AddProjectAction;
import com.dm.listener.ActionListenerRegistry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jrichard on 30/06/2017.
 */
public class AddProjectMenuItem extends JMenuItem {

    private JFrame mainFrame;

    public AddProjectMenuItem(JFrame frame) {
        super("Add project");
        this.mainFrame = frame;
        addActionListener(new AddProjectActionListener());
    }

    class AddProjectActionListener implements ActionListener {
        private AddProjectAction addProjectAction = new AddProjectAction(mainFrame);

        public void actionPerformed(ActionEvent e) {
            addProjectAction.execute();
            ActionListenerRegistry.fireActionListeners(ActionCommand.REFRESH_PROJECT_LIST, e);
        }
    }
}
