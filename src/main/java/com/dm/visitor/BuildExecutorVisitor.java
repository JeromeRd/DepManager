package com.dm.visitor;

import com.dm.action.ActionCommand;
import com.dm.common.EnvironmentVariables;
import com.dm.datamodel.GradleProfile;
import com.dm.datamodel.MavenProfile;
import com.dm.datamodel.Project;
import com.dm.exception.ProjectNotFoundException;
import com.dm.listener.ActionListenerRegistry;
import com.dm.manager.EnvironmentVariableManager;
import com.dm.manager.ProjectManager;
import com.dm.runtime.CustomResultHandler;
import com.dm.runtime.GradleTaskExecutor;
import com.dm.runtime.MavenTaskExecutor;
import com.dm.statemachine.ProjectStateMachine;
import com.dm.ui.swing.UILoader;
import org.apache.maven.shared.invoker.InvocationResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jrichard on 12/07/2017.
 */
public class BuildExecutorVisitor {
    private String mavenHome;
    private String gradleHome;

    public BuildExecutorVisitor() {
        mavenHome = EnvironmentVariableManager.get(EnvironmentVariables.MAVEN_HOME);
        gradleHome = EnvironmentVariableManager.get(EnvironmentVariables.GRADLE_HOME);
    }

    public void execute(GradleProfile profile) {
        if (gradleHome == null) {
            System.out.println("Gradle task cannot be processed. Please set your "+EnvironmentVariables.GRADLE_HOME+" environment variable");
            return;
        }

        GradleTaskExecutor gradleTaskExecutor = new GradleTaskExecutor();
        if (profile.getSubProject() != null) {
            if (profile.getCommand() != null) {
                //UILoader.getInstance().setMessage("Build " + profile.getSubProject());
                //UILoader.getInstance().showLoader();
                try {
                    Project project = ProjectManager.findByProfile(profile.getId());

                    UILoader.getInstance().startLoading(project);

                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.submit(() -> {
                    CustomResultHandler.Result result = gradleTaskExecutor.buildProject(gradleHome, profile.getSubProject(), profile.getCommand());
                    //UILoader.getInstance().hideLoader();
                    UILoader.getInstance().endLoading(project);
                        if (result.isSuccess()) {
                            ProjectStateMachine.process(project, Project.ProjectState.VALID);
                        } else {
                            ProjectStateMachine.process(project, Project.ProjectState.IN_ERROR);
                        }
                        ActionListenerRegistry.fireActionListeners(ActionCommand.REFRESH_PROJECT_LIST, null);
                    });
                } catch (ProjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void execute(MavenProfile profile) {
        if (mavenHome == null) {
            System.out.println("Maven task cannot be processed. Please set your "+EnvironmentVariables.MAVEN_HOME+" environment variable");
            return;
        }

        MavenTaskExecutor mavenTaskExecutor = new MavenTaskExecutor();
        if (profile.getSubProject() != null) {
            if (profile.getCommand() != null) {
                //UILoader.getInstance().setMessage("Build " + profile.getSubProject());
                //UILoader.getInstance().showLoader();
                try {
                    Project project = ProjectManager.findByProfile(profile.getId());

                    UILoader.getInstance().startLoading(project);

                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.submit(() -> {
                        InvocationResult result = mavenTaskExecutor.buildProject(mavenHome, project.getPath(), profile.getCommand());
                        //UILoader.getInstance().hideLoader();
                        UILoader.getInstance().endLoading(project);
                        if (result.getExitCode() == 0) {
                            ProjectStateMachine.process(project, Project.ProjectState.VALID);
                        } else {
                            ProjectStateMachine.process(project, Project.ProjectState.IN_ERROR);
                            String msg = "Invocation Exception";
                            if (result.getExecutionException() != null) {
                                msg = result.getExecutionException().getMessage();
                            }
                            System.out.println(msg);
                        }
                        ActionListenerRegistry.fireActionListeners(ActionCommand.REFRESH_PROJECT_LIST, null);
                    });
                } catch (ProjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
