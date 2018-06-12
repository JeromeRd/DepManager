package com.dm.statemachine;

import com.dm.datamodel.Project;

/**
 * Created by jrichard on 12/07/2017.
 */
public class ProjectStateMachine {

    public static void initialize() {
        FilledState.initialize();
        NotFilledState.initialize();
        ValidState.initialize();
        InErrorState.initialize();
    }

    public static void process(Project project, Project.ProjectState next) {
        if (project.getState() != null) {
            State actualState = getState(project.getState());
            if (actualState != null) {
                actualState.off(project);
            }
        }
        State nextState = getState(next);
        if (nextState != null) {
            nextState.on(project);
        }
    }

    private static State getState(Project.ProjectState projectState) {
        switch (projectState) {
            case FILLED:
                return FilledState.getInstance();
            case NOT_FILLED:
                return NotFilledState.getInstance();
            case VALID:
                return ValidState.getInstance();
            case IN_ERROR:
                return InErrorState.getInstance();
            default:
                return null;
        }
    }
}
