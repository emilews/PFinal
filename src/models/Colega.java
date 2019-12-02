package models;

import core.ProxyMessenger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Colega {
    public Socket cliente;
    public DataOutputStream buffSalida;
    public DataInputStream buffEntrada;
    public DataInputStream teclado;
    public String nombre;
    public String ip;
    public int puerto;
    public String pass;

    public void RecibirDatos() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String mesgIn = buffEntrada.readUTF();
                        System.out.println(mesgIn);
                        if(mesgIn.contains("Topics:>")){
                            ProxyMessenger.topicsIn(mesgIn);
                        }
                        else if(mesgIn.contains("Users:>")){
                            ProxyMessenger.usersIn(mesgIn);
                        }
                        else if(mesgIn.contains("Mios:>")){
                            ProxyMessenger.miosIn(mesgIn);
                        }
                        else if(mesgIn.equals("No eres el administrador del topic, imposible remover")){
                            ProxyMessenger.cantRemoveIn(mesgIn);
                        }
                        else if(mesgIn.contains("El topic ya existe")){
                            ProxyMessenger.cantCreateIn(mesgIn);
                        }
                        else if(mesgIn.contains("["+nombre+"]")){
                            ProxyMessenger.newPrivateMessageIn(mesgIn);
                        }else{
                            ProxyMessenger.newMessageIn(mesgIn);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        hilo.start();
    }

    public void EnviarDatos(String msg) {
        if("topic -l".equals(msg)){
            try {
                buffSalida.writeUTF("topic -l");
                buffSalida.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                buffSalida.writeUTF(msg);
                buffSalida.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void EscribirDatos() {
        try {
            String line = "";
            while (!line.equals(".bye")) {
                line = teclado.readLine();
                buffSalida.writeUTF(line);
                buffSalida.flush();
            }
        } catch (Exception e) {
        }
    }
}