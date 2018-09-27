package com.esiea.client;

import com.esiea.game.GameGUI;

import java.io.*;
import java.net.*;

public class Client
{
    public static void main(String[] args)
    {
        GameGUI gameGUI = new GameGUI();

        try
        {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true)
            {
                String toSend = gameGUI.getInfoForServer();
                dataOutputStream.writeUTF(toSend);

                if (toSend.equals("disconnection") | toSend.equals("disconnectionad"))
                {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    gameGUI.setInfoForGameGUI("disconnected");
                    gameGUI.clearInfoForServer();
                    break;
                }

                String received = dataInputStream.readUTF();

                if (received.contains("user"))
                {
                    String name = null;
                    int id = 0;
                    String[] result = received.split("\\s");
                    for (int i = 0; i < result.length; i++)
                    {
                        name = result[0];
                        id = Integer.parseInt(result[1]);
                    }
                    gameGUI.setConnected(name, id);
                }

                switch (received)
                {
                    case "admin":
                        gameGUI.setConnected("admin", 0);
                        gameGUI.clearInfoForServer();
                    break;

                    case "adminco":
                        gameGUI.setInfoForGameGUI(received);
                        gameGUI.clearInfoForServer();
                    break;
                }
            }

            dataInputStream.close();
            dataOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
