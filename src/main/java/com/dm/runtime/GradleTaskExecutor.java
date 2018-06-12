package com.dm.runtime;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

/**
 * Created by jrichard on 11/07/2017.
 */
public class GradleTaskExecutor {

    public CustomResultHandler.Result buildProject(String gradleInstallationPath, String projectPath, String... tasks) {
        CustomResultHandler.Result execResult = null;
        File gradleInstallationDir = new File(gradleInstallationPath);
        File projectFile = new File(projectPath);

        if (gradleInstallationDir == null || projectFile == null) {
            return null;
        }

        GradleConnector connector = GradleConnector.newConnector();
        connector.useInstallation(gradleInstallationDir);
        connector.forProjectDirectory(projectFile);

        ProjectConnection connection = connector.connect();
        BuildLauncher build = connection.newBuild();
        build.forTasks(tasks);
        try {
            CustomResultHandler resultHandler = new CustomResultHandler();
            build.run(resultHandler);
            execResult = resultHandler.getExecutionResult();
        } catch (GradleConnectionException | IllegalStateException e) {
            System.out.println("gradleInstallationPath = [" + gradleInstallationPath + "], projectPath = [" + projectPath + "], tasks = [" + tasks + "]");
        }
        finally {
            connection.close();
        }
        return execResult;
    }
}
