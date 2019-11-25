package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    public void RecibirDatos(String mesgIn) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if(mesgIn.contains("topics:>")){
                            //AQUI LISTA TOPICS
                        }
                        if(mesgIn.contains("Broadcast")){
                            System.out.println("texto");
                            //AQUI BROADCASR
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
        try {
            buffSalida.writeUTF("<" + nombre + "> " + msg);
            buffSalida.flush();
            System.out.println(msg);
            RecibirDatos(msg);
        } catch (Exception e) {
        }
        ;
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