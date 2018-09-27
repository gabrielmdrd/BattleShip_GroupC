package com.esiea.server;

import java.io.*;
import java.net.*;

public class Server
{
    private static int NB_MAX_CLIENTS = 100;

   /* ServerSocket serverSocket;

    public Server()
    {
        try
        {
            serverSocket = new ServerSocket(5056);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        serverModel = new ServerModel();
    }

    public void loop()
    {

        while (true)
        {

            try(Socket socket = serverSocket.accept())
            {
                System.out.println("A new client is connected : " + socket);
                System.out.println("Assigning new thread for this client");

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                // if max client connected reached
                if( serverModel.getClientIds().size() > Server.NB_MAX_CLIENTS)
                {
                    System.out.println("test");
                }
                else
                    {

                }
                ClientHandler thread = new ClientHandler(socket, dataInputStream, dataOutputStream, serverModel);
                thread.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }*/

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(5056);
        ServerModel serverModel = new ServerModel();

        while (true)
        {
            Socket socket = null;

            try
            {
                socket = serverSocket.accept();
                System.out.println("A new client is connected : " + socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Assigning new thread for this client");

                ClientHandler clientHandler = new ClientHandler(socket, dataInputStream, dataOutputStream, serverModel);
                clientHandler.start();
            }
            catch (Exception e)
            {
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread
{
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    String id;
    ServerModel model;

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, ServerModel model)
    {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

        this.id = socket.getInetAddress().toString();
        this.model = model;
    }

    @Override
    public void run()
    {
        String toReturn;

        while (true)
        {
            try
            {
                String received = dataInputStream.readUTF();

                if(received.equals("disconnection"))
                {
                    System.out.println("Client " + this.socket + " a demandé une déconnexion...");
                    System.out.println("Fermeture de la connexion.");
                    this.socket.close();
                    System.out.println("Connexion terminée");
                    break;
                }
                else if (received.equals("disconnectionad"))
                {
                    System.out.println("Client " + this.socket + " a demandé une déconnexion...");
                    System.out.println("Fermeture de la connexion.");
                    this.socket.close();
                    model.setAdminOnline(false);
                    System.out.println("Connexion terminée");
                    break;
                }

                if (received.contains("user"))
                {
                    char clientNumber = received.charAt(4);
                    toReturn = "user " + Character.toString(clientNumber);
                    dataOutputStream.writeUTF(toReturn);
                }

                switch (received)
                {
                    case "admin admin":
                    {
                        if (model.isAdminOnline())
                        {
                            toReturn = "adminco";
                            dataOutputStream.writeUTF(toReturn);
                        }
                        else
                        {
                            model.setAdminOnline(true);

                            toReturn = "admin";
                            dataOutputStream.writeUTF(toReturn);
                        }
                        break;
                    }

                    default:
                        dataOutputStream.writeUTF("Entrée invalide");
                    break;
                }
            }
            catch (SocketException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            this.dataInputStream.close();
            this.dataOutputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
