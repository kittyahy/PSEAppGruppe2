package com.pseandroid2.dailydataserver.onlineproject.requestParameters.deltaController;

import com.pseandroid2.dailydataserver.RequestParameter;

public class SaveDeltaParameter extends RequestParameter {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public SaveDeltaParameter(String token, String command) {
        super(token);
        this.command = command;
    }

    @Override
    public String toString() {
        return "SaveDeltaParameter{" +
                "command='" + command + '\'' +
                "} " + super.toString();
    }


}
