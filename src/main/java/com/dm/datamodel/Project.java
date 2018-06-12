package com.dm.datamodel;

import com.dm.tools.ProjectUtilities;

import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class Project {
    private UUID uuid;
    private String name;
    private UUID profileId;
    private String path;
    private ProjectState state;
    private String errorMessage;

    public enum ProjectState {
        //Filled with data (subProject, commands, ...)
        FILLED, NOT_FILLED,

        //Error appears during the build
        IN_ERROR,

        //Built successfully
        VALID
    }

    public enum ProjectType {
        MAVEN, GRADLE, UNKNOWN
    }

    public Project() {
    }

    public Project(String name) {
        this();
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public Project(UUID uuid, String name) {
        this();
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public ProjectState getState() {
        return state;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Profile createProfile() {
        switch (ProjectUtilities.getProjectType(path)) {
            case GRADLE:
                return new GradleProfile();
            case MAVEN:
                return new MavenProfile();
            case UNKNOWN:
            default :
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Project) {
            if (((Project) o).getUuid().equals(this.getUuid())) {
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
        result = 31 * result + uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + profileId.hashCode();
        return result;
    }
}
