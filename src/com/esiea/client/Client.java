package com.esiea.client;

import com.esiea.game.ECellState;
import com.esiea.game.GameGUI;

import java.io.*;
import java.net.*;

public class Client
{
    private GameGUI gameGUI;
    private String username;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Client(String address, int port, GameGUI gameGUI)
    {
        this.gameGUI = gameGUI;

        try
        {
            socket = new Socket(address, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            // TODO: gerer l'erreur de connexion au serveur
            e.printStackTrace();
        }

    }

    public void loop()
    {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try
                {
                    while (true)
                    {
                        String received = dataInputStream.readUTF();

                        switch (received)
                        {
                            case "connected":
                                gameGUI.setConnected(username);
                            break;

                            case "ad_connected":
                                gameGUI.adminAlreadyConnected();
                            break;

                            case "launched":

                            break;
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void connect(String username, String password)
    {
        this.username = username;

        try
        {
            dataOutputStream.writeUTF("connection::" + username + "::" + password);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            dataOutputStream.writeUTF("deconnection::");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        gameGUI.setDisconnected();
    }

    public void launchGame(ECellState[][] gameGrid)
    {

        try
        {
            dataOutputStream.writeUTF("launch::");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
