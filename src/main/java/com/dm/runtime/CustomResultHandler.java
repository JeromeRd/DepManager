package com.dm.runtime;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.ResultHandler;

/**
 * Created by jrichard on 13/07/2017.
 */
public class CustomResultHandler implements ResultHandler {

    public enum Result {
        SUCCESS, FAIL;

        public boolean isSuccess() {
            return this.equals(CustomResultHandler.Result.SUCCESS);
        }
    }

    private Result executionResult;

    public Result getExecutionResult() {
        return executionResult;
    }

    @Override
    public void onComplete(Object o) {
        executionResult = Result.SUCCESS;
        System.out.println("Operation successfully finished");
    }

    @Override
    public void onFailure(GradleConnectionException e) {
        executionResult = Result.FAIL;
        System.out.println("Operation failed: "+ e.getMessage());
    }
}
