package com.dm.strategy;

import com.dm.datamodel.Profile;

/**
 * Created by jrichard on 04/07/2017.
 */
public class StrategyDispatcher {
    public static void dispatch(Profile profile, ProjectStrategy projectStrategy) {
        if (profile != null && projectStrategy != null) {
            switch (projectStrategy) {
                case SIMPLE_STRATEGY:
                    profile.setProjectDeployStrategy(new SimpleDeployStrategy());
                    break;
                default:
                    break;
            }
        }
    }
}
