package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

/**
 * Created by robin on 9/16/16.
 */
public abstract class AbstractTest {

    public String runCommand(ServerApplication app, String str) throws Exception {
        Command command = Command.fromString(str);
        AbstractCommandExecutor executor = app.getExecutor(command);
        return executor.run(command, app);
    }

    public String runCommands(ServerApplication app, String ...str) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : str) {
            String res = runCommand(app, s);
            if (res != null) {
                stringBuilder.append(res);
            }
        }
        return stringBuilder.toString();
    }

    public ServerApplication getServer() throws Exception {
        ServerApplication app = new ServerApplication();
        app.loadExecutors();
        return app;
    }
}
