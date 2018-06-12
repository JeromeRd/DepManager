package com.dm.strategy;

import com.dm.datamodel.Profile;
import com.dm.datamodel.Project;

/**
 * Created by jrichard on 03/07/2017.
 */
public interface ProjectBuildStrategy {
    void fill(Project project, Profile profile);
    void execute(Profile profile);
}
