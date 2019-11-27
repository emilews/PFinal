package models;

import core.App;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente extends Colega{
    public Cliente(String nombre, String ip, int puerto, String ps) {

    this.puerto = puerto;
    this.nombre = nombre;
    this.ip = ip;
    this.pass = ps;
}

    public void init(boolean isNew) {
        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cliente = new Socket(ip, puerto);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        App.mainScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        buffSalida = new DataOutputStream(cliente.getOutputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        buffEntrada = new DataInputStream(cliente.getInputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if(isNew){
                            buffSalida.writeUTF("NewUser:>"+nombre + "," + pass);
                        }else{
                            buffSalida.writeUTF("LogIn:>"+nombre + "," + pass);
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String mesgIn = null;
                    try {
                        mesgIn = buffEntrada.readUTF();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (mesgIn.equals("Aceptado")) {
                        //AQUI EMPEZAMOS LOL



                        EscribirDatos();
                    } else {
                        RecibirDatos(mesgIn);
                        try {
                            cliente.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            });
            hilo.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}