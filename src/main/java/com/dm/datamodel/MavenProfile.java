package com.dm.datamodel;

import com.dm.visitor.BuildExecutorVisitor;

/**
 * Created by jrichard on 12/07/2017.
 */
public class MavenProfile extends Profile {
    public MavenProfile() {
        super();
    }

    public void execute(BuildExecutorVisitor buildExecutorVisitor) {
        buildExecutorVisitor.execute(this);
    }
}
