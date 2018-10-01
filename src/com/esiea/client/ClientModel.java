package com.esiea.client;

import com.esiea.game.ECellState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.esiea.game.ECellState.EMPTY;
import static com.esiea.game.ECellState.SHIP_HIDDEN;

public class ClientModel
{
    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();

    private boolean isGameLaunched;
    private boolean isAdmin;

    private ECellState[][] gameGrid =
            {
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
                    {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY}
            };

    public ECellState[][] getGameGrid()
    {
        return gameGrid;
    }

    public void updateGameGrid(ECellState[][] newGameGrid)
    {
        this.gameGrid = newGameGrid;
    }

    public boolean isGameLaunched()
    {
        return isGameLaunched;
    }

    public void setGameLaunched(boolean gameLaunched)
    {
        isGameLaunched = gameLaunched;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }

    public void setState(int col, int row, ECellState state)
    {
        gameGrid[col][row] = state;
    }

    public ECellState getState(int col, int row)
    {
        return gameGrid[col][row];
    }

    public String getStateToString()
    {
        int[][] stateValue =
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

        for (int col = 0; col < 10; col++)
        {
            for (int row = 0; row < 10; row++)
            {
                stateValue[col][row] = gameGrid[col][row].getValue();
            }
        }

        return gson.toJson(stateValue);
    }
}
