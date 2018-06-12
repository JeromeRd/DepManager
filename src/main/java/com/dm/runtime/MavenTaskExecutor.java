package com.dm.runtime;

import com.dm.common.ProjectsReferential;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;
import java.util.Arrays;

/**
 * Created by jrichard on 11/07/2017.
 */
public class MavenTaskExecutor {

    public InvocationResult buildProject(String mavenInstallationPath, String projectPath, String... phases) {
        File mavenInstallationDir = new File(mavenInstallationPath);
        File projectFile = new File(projectPath);

        if (mavenInstallationDir == null || projectFile == null) {
            return null;
        }

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File(projectPath + "/" + ProjectsReferential.MAVEN_FILE));
        request.setGoals(Arrays.asList(phases));

        Invoker invoker = new DefaultInvoker();
        InvocationResult execResult = null;
        try {
            execResult = invoker.execute(request);
        } catch (MavenInvocationException | IllegalStateException e) {
            System.out.println("mavenInstallationPath = [" + mavenInstallationPath + "], projectPath = [" + projectPath + "], tasks = [" + phases + "]");
        }
        return execResult;
    }
}
