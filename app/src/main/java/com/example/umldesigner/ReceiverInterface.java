package com.example.umldesigner;

public interface ReceiverInterface {
    /**
     * this interface is used for sending data from activity/fragment back to the main activity
     */

    public boolean receiveData(Object sentData);
}
