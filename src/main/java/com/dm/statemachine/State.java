package com.dm.statemachine;

import com.dm.datamodel.Project;

/**
 * Created by jrichard on 12/07/2017.
 */
public interface State {
    /**
     * This method is called when a project enter in the state
     * @param project
     */
    void on(Project project);

    /**
     * This method is called when a project leave the state
     * @param project
     */
    void off(Project project);
}
