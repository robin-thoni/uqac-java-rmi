package com.uqac.rthoni.java_rmi.server;

/**
 * Created by robin on 9/16/16.
 */
public class TestDbo {
    private String privateString = "default_value";

    private int privateInt = 24;

    public int publicInt = 42;

    public String getPrivateString() {
        return privateString;
    }

    public void setPrivateString(String privateString) {
        this.privateString = privateString;
    }

    public int getPrivateInt() {
        return privateInt;
    }

    public void setPrivateInt(int privateInt) {
        this.privateInt = privateInt;
    }
}
