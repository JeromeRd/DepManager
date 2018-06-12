package com.dm.datamodel;

import com.dm.strategy.ProjectBuildStrategy;
import com.dm.strategy.ProjectStrategy;
import com.dm.visitor.BuildExecutorVisitor;

import java.util.UUID;

/**
 * Created by jrichard on 03/07/2017.
 */
public abstract class Profile {
    private UUID id;
    private String[] command;
    private String subProject;
    private ProjectStrategy strategy;
    private ProjectBuildStrategy projectDeployStrategy;

    public Profile() {
        id = UUID.randomUUID();
    }

    public Profile(UUID uuid) {
        id = uuid;
    }

    public UUID getId() {
        return id;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        if (command != null) {
            this.command = command.clone();
        }
    }

    public String getSubProject() {
        return subProject;
    }

    public void setSubProject(String subProject) {
        this.subProject = subProject;
    }

    public ProjectStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ProjectStrategy strategy) {
        this.strategy = strategy;
    }

    public ProjectBuildStrategy getProjectDeployStrategy() {
        return projectDeployStrategy;
    }

    public void setProjectDeployStrategy(ProjectBuildStrategy projectDeployStrategy) {
        this.projectDeployStrategy = projectDeployStrategy;
    }

    public abstract void execute(BuildExecutorVisitor buildExecutorVisitor);

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Profile) {
            if (((Profile) o).getId().equals(this.getId())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + command.hashCode();
        result = 31 * result + subProject.hashCode();
        result = 31 * result + strategy.hashCode();
        result = 31 * result + projectDeployStrategy.hashCode();
        return result;
    }
}
