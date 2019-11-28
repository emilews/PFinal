package core;

import com.sun.tools.javac.Main;
import controllers.MainController;

public class ProxyMessenger {
    public static synchronized void newMessageIn(String msg){
        MainController.newMessageIn(msg);
    }
    public static synchronized void topicsIn(String msg){
        MainController.setTopics(msg);
    }
    public static synchronized void newPrivateMessageIn(String msg){
        MainController.newPrivateMessageIn(msg);
    }
}
