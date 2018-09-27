package com.esiea.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class GameGUI implements ActionListener
{
    JFrame frame = new JFrame("Bataille Navale - Groupe C");
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel gamePanel = new JPanel();
    JPanel userPanel = new JPanel(new GridLayout(10, 1));
    GroupLayout groupLayout = new GroupLayout(gamePanel);

    JButton connectionButton = new JButton("Connexion");
    JButton deconnectionButton = new JButton("Déconnexion");
    JButton boatButton = new JButton("Placer Bateaux");
    JButton launchButton = new JButton("Lancer la partie");
    JButton fireButton = new JButton("Tirer une torpille");

    JTextField loginTextField = new JTextField(15);
    JPasswordField passwordTextField = new JPasswordField(15);
    JLabel loginLabel = new JLabel("Login :");
    JLabel passwordLabel = new JLabel("Mot de passe :");
    JLabel connectionLabel = new JLabel("Bienvenue sur le jeu de bataille navale du groupe C !");

    private String login = "";
    private String password = "";
    private String infoForServer = "";
    private boolean readyToQuit = true;
    private String whoIs = "";

    private NavalGridComponent navalGrid;

    public GameGUI()
    {
        //model = new ClientModel();
        navalGrid = new NavalGridComponent(null /*model.getGrid()*/);
        mainPanel.add(navalGrid, BorderLayout.CENTER);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        gamePanel.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(loginLabel, 0, GroupLayout.DEFAULT_SIZE, 45)
                    .addComponent(loginTextField, 0, GroupLayout.DEFAULT_SIZE, 200)
                    .addComponent(passwordLabel, 0, GroupLayout.DEFAULT_SIZE, 90)
                    .addComponent(passwordTextField, 0, GroupLayout.DEFAULT_SIZE, 200)
                    .addComponent(connectionButton, 0, GroupLayout.DEFAULT_SIZE, 110)
                    .addComponent(deconnectionButton,0, GroupLayout.DEFAULT_SIZE, 110))
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(connectionLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginLabel).addComponent(loginTextField).addComponent(passwordLabel).addComponent(passwordTextField).addComponent(connectionButton).addComponent(deconnectionButton))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(connectionLabel))
        );

        boatButton.setVisible(false);
        launchButton.setVisible(false);
        fireButton.setVisible(false);

        userPanel.add(boatButton);
        userPanel.add(launchButton);
        userPanel.add(fireButton);

        connectionButton.setActionCommand("connection");
        connectionButton.addActionListener(this);

        deconnectionButton.setActionCommand("disconnection");
        deconnectionButton.addActionListener(this);
        deconnectionButton.setEnabled(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (!readyToQuit)
                {
                    JOptionPane.showMessageDialog(frame, "Veuillez vous déconnecter avant de pouvoir quitter le jeu.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    infoForServer = "disconnection";
                    try
                    {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(userPanel, BorderLayout.EAST);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new GameGUI();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        String source = e.getActionCommand();

        switch (source)
        {
            case "connection":
                login = loginTextField.getText();
                password = passwordTextField.getText();
                infoForServer = login + " " + password;
            break;

            case "disconnection":
                if (whoIs.equals("admin"))
                {
                    infoForServer = "disconnectionad";
                }
                else
                {
                    infoForServer = "disconnection";
                }
            break;
        }
    }

    public void setInfoForGameGUI(String info)
    {
        switch (info)
        {
            case "adminco":
                JOptionPane.showMessageDialog(frame, "Impossible de connecté ce compte administrateur car il y en a déjà un de connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
            break;

            case "disconnected":
                this.connectionLabel.setText("Vous êtes maintenant déconnecté.");
                this.connectionLabel.setForeground(Color.RED);
                this.deconnectionButton.setEnabled(false);
                this.connectionButton.setEnabled(true);
                this.boatButton.setVisible(false);
                this.launchButton.setVisible(false);
                this.fireButton.setVisible(false);
                readyToQuit = true;
                this.frame.setTitle("Bataille Navale - Groupe C");
                this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                whoIs = "";
            break;
        }
    }

    public String getInfoForServer()
    {
        return infoForServer;
    }

    public void clearInfoForServer()
    {
        infoForServer = "";
    }

    public void setConnected(String user, int id)
    {
        readyToQuit = false;
        if (user.equals("admin"))
        {
            this.connectionLabel.setText("Vous êtes connecté en temps que : " + user);
            this.connectionLabel.setForeground(Color.RED);
            this.boatButton.setVisible(true);
            this.launchButton.setVisible(true);
            this.connectionButton.setEnabled(false);
            this.deconnectionButton.setEnabled(true);
            this.frame.setTitle("Bataille Navale - Groupe C " + "[" + user + "]");
        }
        if (user.equals("user"))
        {
            this.connectionLabel.setText("Vous êtes connecté en temps que : " + user + " " + id);
            this.connectionLabel.setForeground(Color.GREEN);
            this.fireButton.setVisible(true);
            this.connectionButton.setEnabled(false);
            this.deconnectionButton.setEnabled(true);
            this.frame.setTitle("Bataille Navale - Groupe C " + "[" + user + id + "]");
        }
        whoIs = user;
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
