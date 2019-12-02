package controllers;


import core.App;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Cliente;

import java.io.*;
import java.net.URL;
import java.util.*;

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
    @FXML
    ListView MainListaUsers;
    @FXML
    ListView MainListaMio;
    @FXML
    TextField MainNuevoTopicNombre;
    @FXML
    Button MainAgregarNuevoTopic;

    static ObservableList mensajes = FXCollections.observableArrayList();
    static ObservableList topics = FXCollections.observableArrayList();
    static ObservableList users = FXCollections.observableArrayList();
    static ObservableList mios = FXCollections.observableArrayList();
    File file = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mensajes.add("¡Bienvenido!");
        topics.add("Sin canales.");
        users.add("Sin usuarios.");
        mios.add("No estás en ningún canal.");
        MainListaTopics.setItems(topics);
        MainChatArea.setItems(mensajes);
        MainListaUsers.setItems(users);
        MainListaMio.setItems(mios);
        MainChatArea.refresh();
        topicsGetter();
        usersGetter();
        miosGetter();
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
        MainListaMio.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    String selection = MainListaMio.getSelectionModel()
                            .getSelectedItem().toString();
                    changeTarget(selection);
                }
            }
        });
        MainListaUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    String selection = MainListaUsers.getSelectionModel()
                            .getSelectedItem().toString();
                    changeTarget(selection);
                }
            }
        });
        // Create ContextMenu
        ContextMenu topicsContextMenu = new ContextMenu();
        ContextMenu mioContextMenu = new ContextMenu();
        ContextMenu usersContextMenu = new ContextMenu();


        MenuItem salirDeCanal = new MenuItem("Salir de canal");
        salirDeCanal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancelar suscripción de canal");
                alert.setHeaderText("Estás a punto de cancelar la suscripción a este canal.");
                alert.setContentText("¿Quieres continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    cliente.EnviarDatos("unsubscribe -t '"+MainListaTopics.getSelectionModel()
                            .getSelectedItem().toString().trim()+"'");
                } else {
                    //Nothing
                }
            }
        });
        MenuItem eliminarCanal = new MenuItem("Eliminar canal");
        eliminarCanal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar canal");
                alert.setHeaderText("Estás a punto de eliminar este canal.");
                alert.setContentText("¿Quieres continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    cliente.EnviarDatos("remove -t '"+MainListaMio.getSelectionModel()
                            .getSelectedItem().toString().trim()+"'");
                } else {
                    //Nothing
                }
            }
        });
        MenuItem entrarACanal = new MenuItem("Suscribirse al canal");
        entrarACanal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suscripción a canal");
                alert.setHeaderText("Estás a punto de suscribirte a este canal.");
                alert.setContentText("¿Quieres continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    cliente.EnviarDatos("subscribe -t '"+MainListaTopics.getSelectionModel()
                    .getSelectedItem().toString().trim()+"'");
                } else {
                    //Nothing
                }
            }
        });
        MenuItem enviarArchivo = new MenuItem("Enviar archivo");
        enviarArchivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enviar archivo");
                file = fileChooser.showOpenDialog(App.getStage());
                if( file != null ){
                    FileInputStream fs = null;
                    try {
                        fs = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(fs != null){
                        byte[] data = null;
                        try {
                            data = fs.readAllBytes();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        cliente.EnviarDatos("file -t '"+MainListaUsers.getSelectionModel()
                                .getSelectedItem()
                                .toString()
                                .trim()+"' -n '"+file.getName()+"' -b '"+Arrays.toString(data)+"'");
                    }
                }
            }
        });
        // Add MenuItem to ContextMenu
        topicsContextMenu.getItems().addAll(entrarACanal);
        mioContextMenu.getItems().addAll(salirDeCanal, eliminarCanal);
        usersContextMenu.getItems().addAll(enviarArchivo);
        MainListaMio.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                mioContextMenu.show(MainListaMio, event.getScreenX(), event.getScreenY());
            }
        });
        MainListaTopics.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                topicsContextMenu.show(MainListaTopics, event.getScreenX(), event.getScreenY());
            }
        });
        MainListaUsers.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                usersContextMenu.show(MainListaUsers, event.getScreenX(), event.getScreenY());
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
    @FXML
    public void newTopicWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Crear canal");
        alert.setHeaderText("Estás a punto de crear este canal.");
        alert.setContentText("¿Quieres continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            cliente.EnviarDatos("crear -t '"+MainNuevoTopicNombre.getText().trim()+"'");
            MainNuevoTopicNombre.clear();
        } else {
            //Nothing
        }
    }

    public static void cantCreate(String msg){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ocurrió un error al intentar crear el topic");
                alert.setContentText(msg);

                alert.showAndWait();
            }
        });
    }

    public static void cantRemoveTopic(String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ocurrió un error al intentar eliminar el topic");
                alert.setContentText(msg);

                alert.showAndWait();
            }
        });
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
    public static void askForUsers(){
        cliente.EnviarDatos("user -l");
    }
    public static void askForMios(){
        cliente.EnviarDatos("mio -l");
    }
    public static void setTopics(String t){
        String[] ts = t.split(":>");
        String[] s = ts[1].split(",");
        List<String> n = new ArrayList<>();
        for(int i = 0; i < s.length; i++){
            if(cliente.nombre.equals(s[i])){
                continue;
            }
            boolean rep = false;
            for(int j = 0; j < topics.size(); j++){
                if(topics.get(j).equals(s[i])){
                    rep = true;
                }
            }
            if(!rep){
                n.add(s[i]);
            }
        }
        topics.addAll(n);
        if(topics.size() > 1 && topics.get(0).equals("Sin canales.")){
            topics.remove(0);
        }
    }
    public static void setUsers(String t){
        String[] ts = t.split(":>");
        String[] s = ts[1].split(",");
        List<String> n = new ArrayList<>();
        for(int i = 0; i < s.length; i++){
            if(cliente.nombre.equals(s[i])){
                continue;
            }
            boolean rep = false;
            for(int j = 0; j < users.size(); j++){
                if(users.get(j).equals(s[i])){
                    rep = true;
                }
            }
            if(!rep){
                n.add(s[i]);
            }
        }
        users.addAll(n);
        if(users.size() > 1 && users.get(0).equals("Sin usuarios.")){
            users.remove(0);
        }
    }
    public static void setMios(String t){
        String[] ts = t.split(":>");
        String[] s = ts[1].split(",");
        List<String> n = new ArrayList<>();
        for(int i = 0; i < s.length; i++){
            if(cliente.nombre.equals(s[i])){
                continue;
            }
            boolean rep = false;
            for(int j = 0; j < mios.size(); j++){
                if(mios.get(j).equals(s[i])){
                    rep = true;
                }
            }
            if(!rep){
                n.add(s[i]);
            }
        }
        mios.addAll(n);
        if(mios.size() > 1 && mios.get(0).equals("No estás en ningún canal.")){
            mios.remove(0);
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
    public void usersGetter(){
        Thread t = new Thread(){
            @Override
            public void run(){
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        MainController.askForUsers();
                    }
                }, 100, 5000);
            }
        };
        t.start();
    }
    public void miosGetter(){
        Thread t = new Thread(){
            @Override
            public void run(){
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        MainController.askForMios();
                    }
                }, 200, 5000);
            }
        };
        t.start();
    }
}
