package com.umldesigner;

public interface ReceiverInterface {
    /**
     * this interface is used for sending data from any activity back to the main one, doing it like
     * this because it gets hard to scale up if there are 100 classes that need data sent back and
     * the arguments of the fields aren't looking good eiter
     */

    public boolean receiveData(Object sentData);
}
