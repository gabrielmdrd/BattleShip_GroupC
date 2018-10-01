package com.esiea.client;

import com.esiea.game.ECellState;
import com.esiea.game.GameGUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.*;

public class Client
{
    private GameGUI gameGUI;
    private String username;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();

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

                        if (received.startsWith("launched::"))
                        {
                            int[][] gridToUpload;
                            String[] split = received.split("::");
                            String grid = split[1];
                            gridToUpload = gson.fromJson(grid, int[][].class);
                            gameGUI.setGameLaunched(gridToUpload);
                        }

                        switch (received)
                        {
                            case "connected":
                                gameGUI.setConnected(username);
                                if (!gameGUI.getClientModel().isAdmin())
                                {
                                    getGame();
                                }
                            break;

                            case "ad_connected":
                                gameGUI.adminAlreadyConnected();
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
            if (gameGUI.getClientModel().isAdmin())
            {
                dataOutputStream.writeUTF("deconnection::admin");
            }
            else
            {
                dataOutputStream.writeUTF("deconnection::");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        gameGUI.setDisconnected();
    }

    public void launchGame(String state)
    {
        try
        {
            dataOutputStream.writeUTF("launch::" + state);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void getGame()
    {
        try
        {
            dataOutputStream.writeUTF("game::");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
