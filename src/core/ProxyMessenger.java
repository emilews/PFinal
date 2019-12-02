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
    public static synchronized void usersIn(String msg){
        MainController.setUsers(msg);
    }
    public static synchronized void miosIn(String msg){
        MainController.setMios(msg);
    }
    public static synchronized void cantRemoveIn(String msg){
        MainController.cantRemoveTopic(msg);
    }

    public static synchronized void cantCreateIn(String msg){
        MainController.cantCreate(msg);
    }
    public static synchronized void newPrivateMessageIn(String msg){
        MainController.newPrivateMessageIn(msg);
    }
}
