package com.uqac.rthoni.java_rmi.server.executors;

/**
 * Created by robin on 9/16/16.
 */
public class TestDbo {
    private String privateString = "default_value";

    private int privateInt = 24;

    public int publicInt = 42;

    private int aPrivateField = 0;

    public float publicFloat = 0.42f;

    public double publicDouble = 0.125f;

    public boolean publicBool = true;

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

    public void setPrivateInteger(Integer privateInt) {
        this.privateInt = privateInt;
    }

    public String getMyself(String str)
    {
        return str;
    }

    public String toString()
    {
        return String.format("privateString=%s, privateInt=%d, publicInt=%d, aPrivateField=%d, publicFloat=%f, publicDouble=%f, publicBool=%b",
                privateString, privateInt, publicInt, aPrivateField, publicFloat, publicDouble, publicBool);
    }
}
