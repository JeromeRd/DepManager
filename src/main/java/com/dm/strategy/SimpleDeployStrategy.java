package com.dm.strategy;

import com.dm.action.ActionCommand;
import com.dm.datamodel.Profile;
import com.dm.datamodel.Project;
import com.dm.listener.ActionListenerRegistry;
import com.dm.runtime.Commands;
import com.dm.statemachine.ProjectStateMachine;
import com.dm.tools.ProjectUtilities;
import com.dm.visitor.BuildExecutorVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 03/07/2017.
 */
public class SimpleDeployStrategy
        implements ProjectBuildStrategy {

    @Override
    public void fill(Project project, Profile profile) {
        List<String> subProjects = ProjectUtilities.getSubProjects(project.getPath());
        profile.setSubProject(project.getPath() + "\\" + subProjects.get(0));
        profile.setStrategy(ProjectStrategy.SIMPLE_STRATEGY);
        List<String> commandList = new ArrayList<>();
        commandList.add("cmd.exe");
        commandList.add("/C");
        commandList.add(Commands.GRADLEW + " " + Commands.BOOT);
        profile.setCommand(commandList.toArray(new String[commandList.size()]));
        profile.setProjectDeployStrategy(this);
        ProjectStateMachine.process(project, Project.ProjectState.FILLED);
        ActionListenerRegistry.fireActionListeners(ActionCommand.REFRESH_PROJECT_LIST, null);
    }

    @Override
    public void execute(Profile profile) {
        profile.execute(new BuildExecutorVisitor());
    }
}
