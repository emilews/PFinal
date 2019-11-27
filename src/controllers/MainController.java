package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Cliente;

public class MainController {
    static Cliente cliente = null;
    @FXML
    Button MainBotonSalir;
    @FXML
    Button MainBotonEnviar;
    @FXML
    TextField MainEnviarMensaje;
    @FXML
    static TextArea MainChatArea = new TextArea();
    @FXML
    ListView MainListaTopics = new ListView();
    @FXML
    Label MainCanalLabel;

    @FXML
    public void enviarMensaje(){
        if(!MainEnviarMensaje.getText().trim().isEmpty()){
            switch (MainCanalLabel.getText().trim()){
                case "Broadcast":
                    cliente.EnviarDatos("enviar -m " + "'"
                            + MainEnviarMensaje.getText().trim() + "'");
                    break;
                default:
                    cliente.EnviarDatos("enviar -m " + "'" + MainEnviarMensaje.getText().trim() +
                            "'" +" -t " + "'" + MainCanalLabel.getText().trim() + "' " );
            }

            MainEnviarMensaje.clear();
        }
    }
    public static void newMessageIn(String msg){
        MainChatArea.appendText(msg+"\n");
    }



    public static void setClienteModel(String name, String ip, int port, String pswd, boolean isNew){
        cliente = new Cliente(name, ip, port, pswd);
        cliente.init(isNew);
    }
}
