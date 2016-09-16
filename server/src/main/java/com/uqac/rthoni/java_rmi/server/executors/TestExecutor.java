package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;

/**
 * Created by robin on 9/16/16.
 */
public class TestExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public String run(Command command) throws Exception {
        return "Test. succeeded";
    }
}
