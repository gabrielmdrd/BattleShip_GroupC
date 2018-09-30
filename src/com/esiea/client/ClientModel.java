package com.esiea.client;

import com.esiea.game.ECellState;

import static com.esiea.game.ECellState.EMPTY;

public class ClientModel
{
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
        this.gameGrid = gameGrid;
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
}
