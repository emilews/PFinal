package controllers;


import com.sun.tools.javac.Main;
import core.App;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    static Cliente cliente = null;
    @FXML
    Button MainBotonSalir;
    @FXML
    Button MainBotonEnviar;
    @FXML
    TextField MainEnviarMensaje;
    @FXML
    ListView MainChatArea;
    @FXML
    ListView MainListaTopics;
    @FXML
    Label MainCanalLabel;

    static ObservableList mensajes = FXCollections.observableArrayList();
    static ObservableList topics = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mensajes.add("¡Bienvenido!");
        topics.add("Sin canales.");
        MainListaTopics.setItems(topics);
        MainChatArea.setItems(mensajes);
        MainChatArea.refresh();
        topicsGetter();
        MainListaTopics.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    String selection = MainListaTopics.getSelectionModel()
                            .getSelectedItem().toString();
                    changeTarget(selection);
                }
            }
        });
    }
    @FXML
    public void enviarMensaje(){
        if(!MainEnviarMensaje.getText().trim().isEmpty()){
            switch (MainCanalLabel.getText().trim()){
                case "Broadcast":
                    cliente.EnviarDatos("enviar -m " + "'"
                            + MainEnviarMensaje.getText().trim() + "'");
                    newMessageIn("Yo: " + MainEnviarMensaje.getText().trim());
                    break;
                default:
                    cliente.EnviarDatos("enviar -m " + "'" + MainEnviarMensaje.getText().trim() +
                            "'" +" -t " + "'" + MainCanalLabel.getText().trim() + "' " );
                    newMessageIn("Yo -> " + MainCanalLabel.getText().trim() + ": " +
                            MainEnviarMensaje.getText().trim());
            }
            MainEnviarMensaje.clear();
        }
    }
    @FXML
    public void salirMenu(){
        cliente.EnviarDatos("exit");
        System.exit(0);
    }
    public static void newMessageIn(String msg){
        mensajes.add(msg);
    }
    public static void newPrivateMessageIn(String msg){
        String[] data = msg.split("]<");
        String who = data[1].split(">")[0];
        String result = who + " -> Yo: " + data[1].split(">")[1];
        mensajes.add(result);
    }
    public static void setClienteModel(String name, String ip, int port, String pswd, boolean isNew){
        cliente = new Cliente(name, ip, port, pswd);
        cliente.init(isNew);
    }
    private void changeTarget(String target){
        MainCanalLabel.setText(target);
    }
    public static void salir(){
        cliente.EnviarDatos("exit");
    }
    public static void askForTopics(){
        cliente.EnviarDatos("topic -l");
    }
    public static void setTopics(String t){
        String[] ts = t.split(":>");
        String[] s = ts[1].split(",");
        for(int i = 0; i < s.length; i++){
            if(cliente.nombre.equals(s[i])){
                continue;
            }
            if(!topics.contains(s[i])){
                topics.add(s[i]);
            }
        }
        if(topics.get(0).equals("Sin canales.")){
            topics.remove(0);
        }
    }
    public void topicsGetter(){
        Thread t = new Thread(){
          @Override
          public void run(){
              Timer timer = new Timer();
              timer.schedule(new TimerTask() {
                  @Override
                  public void run() {
                      MainController.askForTopics();
                  }
              }, 0, 5000);
          }
        };
        t.start();
    }
}
