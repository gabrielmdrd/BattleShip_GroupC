package com.esiea.server;

import java.util.List;

public class ServerModel
{
    List<String> clientIds;
    int[][] grid;
    List<String> logs;

    private boolean isAdminOnline;

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

    public List<String> getLogs()
    {
        return logs;
    }

    public void setLogs(List<String> logs)
    {
        this.logs = logs;
    }

    public boolean isAdminOnline()
    {
        return isAdminOnline;
    }

    public void setAdminOnline(boolean adminOnline)
    {
        isAdminOnline = adminOnline;
    }
}
