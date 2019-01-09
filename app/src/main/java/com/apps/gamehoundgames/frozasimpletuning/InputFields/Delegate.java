package com.apps.gamehoundgames.frozasimpletuning.InputFields;

//Source: https://www.journaldev.com/1739/observer-design-pattern-in-java
public interface Delegate {

    //methods to register and unregister observers
    public void register(EventDelegate obj);
    public void unregister(EventDelegate obj);

    //method to notify observers of change
    public void callback();

}//interface