package com.esiea.server;

import java.util.List;

public class ServerModel
{
    private List<String> clientIds;
    private boolean isAdminOnline;
    private boolean isGameLaunched;

    private int[][] grid =
    {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public List<String> getClientIds()
    {
        return clientIds;
    }

    public void setClientIds(List<String> clientIds)
    {
        this.clientIds = clientIds;
    }

    public int[][] getGrid()
    {
        return grid;
    }

    public void setGrid(int[][] grid)
    {
        this.grid = grid;
    }

    public boolean isAdminOnline()
    {
        return isAdminOnline;
    }

    public void setAdminOnline(boolean adminOnline)
    {
        isAdminOnline = adminOnline;
    }

    public boolean isGameLaunched()
    {
        return isGameLaunched;
    }

    public void setGameLaunched(boolean gameLaunched)
    {
        isGameLaunched = gameLaunched;
    }
}
