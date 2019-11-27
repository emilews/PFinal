package core;

import controllers.MainController;

public class ProxyMessenger {
    public static synchronized void newMessageIn(String msg){
        MainController.newMessageIn(msg);
    }
}
