package com.esiea.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static com.esiea.game.ECellState.*;

public class NavalGridComponent extends JPanel
{
    private static final int SPACE = 40;
    private final Cell[][] cells = new Cell[10][10];

    private ECellState[][] gameGrid;
    private GameGUI gameGUI;
    private int cellWidth;
    private int cellHeight;

    public NavalGridComponent(ECellState[][] gameGrid, GameGUI gameGUI)
    {
        this.gameGrid = gameGrid;
        this.gameGUI = gameGUI;

        setMinimumSize(new Dimension(500,500) );
        setMaximumSize(new Dimension(500,500) );
        setPreferredSize(new Dimension(500,500) );
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mousePressed(e);
                int mX = e.getX();
                int mY = e.getY();
                Cell cell = getCell(mX, mY);

                if (gameGUI.getClientModel().isAdmin() & !gameGUI.getClientModel().isGameLaunched())
                {
                    cell.adminClicked();
                }
                else if (gameGUI.getClientModel().isAdmin() & gameGUI.getClientModel().isGameLaunched())
                {
                    JOptionPane.showMessageDialog(gameGUI.getFrame(), "Action impossible car la partie est déjà lancée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else if (!gameGUI.getClientModel().isAdmin() & gameGUI.getClientModel().isGameLaunched())
                {
                    cell.clicked();
                }
                else if (!gameGUI.getClientModel().isAdmin() & !gameGUI.getClientModel().isGameLaunched())
                {
                    JOptionPane.showMessageDialog(gameGUI.getFrame(), "Action impossible car la partie n'est pas encore lancée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                repaint();
            }
        });

        initCells();
    }

    public void initCells()
    {
        for (int i = 0; i < cells.length; i++)
        {
            for (int j = 0; j < cells[0].length; j++)
            {
                cells[i][j] = new Cell(i, j, gameGrid[i][j], gameGUI.getClientModel().isAdmin());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth() - 2 * SPACE;
        int h = getHeight() - 2 * SPACE;
        int cellw = Math.round(w / 10.0f);
        int cellh = Math.round(h / 10.f);

        Cell.setCellh(cellh);
        Cell.setCellw(cellw);

        cellWidth = cellw;
        cellHeight = cellh;

        // draw columns
        for(int col = 0 ; col <= 10; col ++)
        {
            g.drawLine(col * cellw + SPACE, SPACE,col * cellw + SPACE, 10 * cellh + SPACE);
        }

        // draw rows
        for(int row = 0 ; row <= 10; row ++)
        {
            g.drawLine(SPACE,row * cellh + SPACE, w + SPACE, row * cellh + SPACE);
        }

        g2d.translate(SPACE, SPACE);

        for(int col = 0 ; col < 10; col ++)
        {
            for(int row = 0 ; row < 10; row ++)
            {
                cells[col][row].draw(g2d);
                g2d.translate(0,cellh);
            }

            g2d.translate(cellw, -10*cellh);
        }
    }

    public Cell getCell(int mouseX, int mouseY)
    {
        int col = (mouseX - SPACE) / cellWidth;
        int row = (mouseY - SPACE) / cellHeight;

        return cells[col][row];
    }

    public Cell getCellTest(int col, int row)
    {
        return cells[col][row];
    }

    public void setNCgameGrid()
    {
        for (int col = 0; col < 10; col++)
        {
            for (int row = 0; row < 10; row++)
            {
                gameGUI.getClientModel().setState(col, row, cells[col][row].getState());
            }
        }
    }
}
