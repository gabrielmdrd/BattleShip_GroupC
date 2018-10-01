package com.esiea.game;

import com.esiea.client.Client;
import com.esiea.client.ClientModel;

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
    JButton launchButton = new JButton("Lancer la partie");
    JButton fireButton = new JButton("Tirer une torpille");

    JTextField loginTextField = new JTextField(15);
    JPasswordField passwordTextField = new JPasswordField(15);
    JLabel loginLabel = new JLabel("Login :");
    JLabel passwordLabel = new JLabel("Mot de passe :");
    JLabel connectionLabel = new JLabel("Bienvenue sur le jeu de bataille navale du groupe C !");

    private String login;
    private String password;
    private boolean readyToQuit = true;

    private NavalGridComponent navalGrid;
    private Client client;
    private ClientModel model;

    public GameGUI()
    {
        model = new ClientModel();
        navalGrid = new NavalGridComponent(model.getGameGrid(), this);
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

        userPanel.add(launchButton);
        userPanel.add(fireButton);

        connectionButton.setActionCommand("connection");
        connectionButton.addActionListener(this);

        deconnectionButton.setActionCommand("disconnection");
        deconnectionButton.addActionListener(this);
        deconnectionButton.setEnabled(false);

        launchButton.setActionCommand("launch");
        launchButton.addActionListener(this);
        launchButton.setVisible(false);

        fireButton.setActionCommand("fire");
        fireButton.addActionListener(this);
        fireButton.setVisible(false);

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
                    client.disconnect();
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
    }

    public static void main(String[] args)
    {
        final GameGUI gui = new GameGUI();
        gui.initClient();

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                gui.getFrame().setVisible(true);
            }
        });
    }

    private void initClient()
    {
        client = new Client("localhost", 5056, this );
        client.loop();
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
                client.connect(login, password);
            break;

            case "disconnection":
                client.disconnect();
            break;

            case "launch":
                navalGrid.setNCgameGrid();
                client.launchGame(model.getStateToString());
            break;

            case "fire":

            break;

            default:
            break;
        }
    }

    public void setDisconnected()
    {
        System.exit(0);
    }

    public void setConnected(String username)
    {
        readyToQuit = false;
        if (username.equals("admin"))
        {
            model.setAdmin(true);
            this.connectionLabel.setText("Vous êtes connecté en temps que : " + username);
            this.connectionLabel.setForeground(Color.RED);
            this.launchButton.setVisible(true);
            this.connectionButton.setEnabled(false);
            this.deconnectionButton.setEnabled(true);
            this.frame.setTitle("Bataille Navale - Groupe C " + "[" + username + "]");
        }
        else
        {
            this.connectionLabel.setText("Vous êtes connecté en temps que : " + username );
            this.connectionLabel.setForeground(Color.GREEN);
            this.fireButton.setVisible(true);
            this.connectionButton.setEnabled(false);
            this.deconnectionButton.setEnabled(true);
            this.frame.setTitle("Bataille Navale - Groupe C " + "[" + username + "]");
        }
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        navalGrid.initCells();
    }

    public void adminAlreadyConnected()
    {
        JOptionPane.showMessageDialog(frame, "Impossible de connecté ce compte administrateur car il y en a déjà un de connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void setGameLaunched(int[][] gameGrid)
    {
        for (int col = 0; col < 10; col++)
        {
            for (int row = 0; row < 10; row++)
            {
                switch (gameGrid[col][row])
                {
                    case 0:
                        getClientModel().setState(col, row, ECellState.EMPTY);
                    break;

                    case 1:
                        getClientModel().setState(col, row, ECellState.MISSED);
                    break;

                    case 2:
                        getClientModel().setState(col, row, ECellState.SHIP_HIDDEN);
                    break;

                    case 3:
                        getClientModel().setState(col, row, ECellState.SHIP_HIT);
                    break;

                    case 4:
                        getClientModel().setState(col, row, ECellState.SHIP_SUNK);
                    break;
                }
            }
        }
        navalGrid.initCells();
        navalGrid.repaint();
        model.setGameLaunched(true);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public ClientModel getClientModel()
    {
        return model;
    }
}