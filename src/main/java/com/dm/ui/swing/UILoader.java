package com.dm.ui.swing;

import com.dm.datamodel.Project;
import com.dm.ui.swing.component.ProjectComponent;
import com.dm.ui.swing.component.ProjectComponentRegistry;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jrichard on 24/07/2017.
 */
public class UILoader extends JPanel {

    private static UILoader instance = new UILoader();
    private JLabel imgLabel;
    private JLabel message;
    private JFrame frame;

    private ConcurrentHashMap<ProjectComponent, ProjectComponent> cache = new ConcurrentHashMap<>();

    private UILoader(){
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 50));

        Dimension dimension = new Dimension(50, 50);

        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("img/loader.gif"));
        imgLabel = new JLabel(img);
        imgLabel.setPreferredSize(dimension);
        imgLabel.setMaximumSize(dimension);
        imgLabel.setMinimumSize(dimension);

        message = new JLabel();
        message.setBorder(new LineBorder(Color.DARK_GRAY));
        message.setForeground(Color.DARK_GRAY);
        message.setPreferredSize(dimension);
        message.setMaximumSize(dimension);
        message.setMinimumSize(dimension);
        message.setHorizontalAlignment(SwingConstants.CENTER);

        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);

        this.add(imgLabel, BorderLayout.CENTER);
        this.add(message, BorderLayout.SOUTH);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public static UILoader getInstance() {
        return instance;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void showLoader() {
        if (frame != null) {
            JPanel glassPane = (JPanel) frame.getGlassPane();
            glassPane.setLayout(new BorderLayout());
            glassPane.add(this, BorderLayout.CENTER);
            frame.setGlassPane(glassPane);
            glassPane.setVisible(true);
        }
    }

    public void hideLoader() {
        if (frame != null) {
            JPanel glassPane = (JPanel) frame.getGlassPane();
            glassPane.removeAll();
            frame.setGlassPane(glassPane);
            glassPane.setVisible(false);
        }
    }

    public void startLoading(Project project) {
        ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);

        ProjectComponent clone = projectComponent.clone();
        cache.put(projectComponent, clone);

        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("img/loader.gif"));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(img);
        img.setImageObserver(iconLabel);

        JLabel label = new JLabel("Loading...");
        projectComponent.add(iconLabel);
        projectComponent.add(label);
    }

    public void endLoading(Project project) {
        ProjectComponent projectComponent = ProjectComponentRegistry.findByValue(project);
        projectComponent = cache.get(projectComponent);
        cache.remove(projectComponent);
    }
}
