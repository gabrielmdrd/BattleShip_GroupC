package com.esiea.server;

import java.io.*;
import java.net.*;

public class Server
{
    private static int NB_MAX_CLIENTS = 100;

    private ServerModel serverModel;
    ServerSocket serverSocket;

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

            try
            {
                Socket socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);
                System.out.println("Assigning new thread for this client");

                //if max client connected reached
//              if( serverModel.getClientIds().size() > Server.NB_MAX_CLIENTS)
//              {
//                  System.out.println("test");
//              }
//              else
//              {
                    ClientHandler thread = new ClientHandler(socket, serverModel);
                    thread.start();
              //}
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("debut");

        new Thread(new Runnable()
        {
            public void run()
            {
                System.out.println("creation du server");
                new Server().loop();
            }
        }).start();

        System.out.println("fin.");
    }
}

class ClientHandler extends Thread
{
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    String id;
    ServerModel model;

    private String username;

    public ClientHandler(Socket socket, ServerModel model) throws IOException
    {
        this.socket = socket;

        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

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

                // connection
                if(received.startsWith("connection::"))
                {
                    String[] split = received.split("::");
                    username = split[1];
                    String password = split[2];
                    if(username.equals("admin") & password.equals("admin"))
                    {
                        if (model.isAdminOnline())
                        {
                            dataOutputStream.writeUTF("ad_connected");
                        }
                        else
                        {
                            model.setAdminOnline(true);
                            dataOutputStream.writeUTF("connected");
                        }
                    }
                    else
                    {
                        // send message to client that connection is OK
                        dataOutputStream.writeUTF("connected");
                    }
                }

                // disconnection
                else if(received.startsWith("deconnection::"))
                {
                    deconnection();
                    break;
                }
                // launch game
                else if(received.startsWith("launch::"))
                {
                    if(!username.equals("admin"))
                    {
                        System.out.println("erreur : not admin");
                    }
                    else
                    {
                        //model.launch();
                    }
                }

//                else if (received.equals("disconnectionad"))
//                {
//                    System.out.println("Client " + this.socket + " a demandé une déconnexion...");
//                    System.out.println("Fermeture de la connexion.");
//                    this.socket.close();
//                    model.setAdminOnline(false);
//                    System.out.println("Connexion terminée");
//                    break;
//                }
//
//                if (received.contains("user"))
//                {
//                    char clientNumber = received.charAt(4);
//                    toReturn = "user " + Character.toString(clientNumber);
//                    dataOutputStream.writeUTF(toReturn);
//                }
//
//                switch (received)
//                {
//                    case "admin admin":
//                    {
//                        if (model.isAdminOnline())
//                        {
//                            toReturn = "adminco";
//                            dataOutputStream.writeUTF(toReturn);
//                        }
//                        else
//                        {
//                            model.setAdminOnline(true);
//
//                            toReturn = "admin";
//                            dataOutputStream.writeUTF(toReturn);
//                        }
//                        break;
//                    }
//
//                    default:
//                        dataOutputStream.writeUTF("Entrée invalide");
//                        break;
//                }
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
    }

    private void deconnection()
    {
        System.out.println("Client " + this.socket + " a demandé une déconnexion...");
        System.out.println("Fermeture de la connexion en cours.");
        try
        {
            this.dataInputStream.close();
            this.dataOutputStream.close();
            this.socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Connexion terminée");
    }
}
