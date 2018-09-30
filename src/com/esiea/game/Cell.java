package com.esiea.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Cell implements ImageObserver {
    private static final int INSET = 10;

    private int row;
    private int col;
    private static int cellw;
    private static int cellh;
    private ECellState state;

    private Image ship;

    public Cell(int row, int col, ECellState state)
    {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public void draw(Graphics2D g2d)
    {
        switch (state)
        {
            case EMPTY:

            break;

            case MISSED:
                g2d.setColor(Color.CYAN);
                g2d.fillOval(INSET, INSET, cellw - 2 * INSET, cellh - 2 * INSET);
            break;

            case SHIP_HIDDEN:
                try
                {
                    ship = ImageIO.read(new File("ship.jpg"));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                g2d.drawImage(ship, INSET, INSET, cellw - 2 * INSET, cellh - 2 * INSET, this);
            break;

            case SHIP_HIT:
                g2d.setColor(Color.ORANGE);
                g2d.drawLine(INSET, cellh / 2, cellw - INSET, cellh / 2);
                g2d.drawLine(cellw / 2, INSET, cellw / 2, cellh - INSET);
                g2d.drawOval(INSET, INSET,cellw - 2 * INSET, cellh - 2 * INSET);
            break;

            case SHIP_SUNK:
                g2d.setColor(Color.RED);
                g2d.fillRect( INSET, INSET, cellw - 2 * INSET, cellh - 2 *INSET );
            break;

            default:

            break;
        }
    }

    private Point getSWPoint(int row, int col, int cellw, int cellh)
    {
        Point pt = new Point();
        pt.x = row * cellw;
        pt.y = (col + 1) * cellh;

        System.out.println("pt: " + pt);
        return pt;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public ECellState getState()
    {
        return state;
    }

    public void setState(ECellState state)
    {
        this.state = state;
    }

    public static void setCellw(int cellw)
    {
        Cell.cellw = cellw;
    }

    public static void setCellh(int cellh)
    {
        Cell.cellh = cellh;
    }

    public void clicked()
    {
        switch (state)
        {
            case EMPTY:
                state = ECellState.MISSED;
            break;

            case SHIP_HIDDEN:
                state = ECellState.SHIP_HIT;
            break;

            default:

            break;
        }
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        return false;
    }
}
