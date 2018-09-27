package com.esiea.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavalGridComponent extends JPanel
{
    private final int[][] grid;
    private static final int SPACE = 40;

    public NavalGridComponent(int[][] grid)
    {
        this.grid = grid;

        setMinimumSize(new Dimension(500,500) );
        setMaximumSize(new Dimension(500,500) );
        setPreferredSize(new Dimension(500,500) );
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mousePressed(e);
                int mouseX = e.getX();
                int mouseY = e.getY();

                System.out.println(mouseX + " " + mouseY);
            }
        });
    }

    public int[][] getGrid()
    {
        return grid;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int w = getWidth() - 2 * SPACE;
        int h = getHeight() - 2 * SPACE;
        int cellw = w / 10;
        int cellh = h / 10;

        // draw columns
        for(int col = 0 ; col <= 10; col ++)
        {
            g.drawLine(col * cellw + SPACE, SPACE,col * cellw + SPACE, 10 * cellh + SPACE);
        }

        // draw raws
        for(int row = 0 ; row <= 10; row ++)
        {
            g.drawLine(SPACE,row * cellh + SPACE, w + SPACE, row * cellh + SPACE);
        }

        // draw cells
        final int[][] mygrid={
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,0,-1,-1,-1,-1,0,1,2,-1},
                {-1,1,-1,-1,-1,-1,0,1,2,-1},
                {-1,2,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1},
                {-1,-1,-1,-1,-1,-1,0,1,2,-1}
        };

        for(int col = 0 ; col < 10; col ++)
        {
            for(int row = 0 ; row < 10; row ++)
            {
                switch(mygrid[col][row])
                {
                    case -1:
                    {

                        break;
                    }

                    case 0:
                    {
                        break;
                    }

                    case 1:
                    {
                        g.setColor(Color.RED);
                        g.fillRect(col * cellw + SPACE + 14, row * cellh + SPACE + 14, cellw - 28, cellh - 28);
                        break;
                    }

                    case 2:
                    {
                        g.setColor(Color.RED);
                        g.fillRect(col * cellw + SPACE + 4, row * cellh + SPACE + 4, cellw - 8, cellh - 8);
                        break;
                    }
                }
            }
        }
    }
}
