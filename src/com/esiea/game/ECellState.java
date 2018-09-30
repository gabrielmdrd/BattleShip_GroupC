package com.esiea.game;

public enum ECellState
{
    EMPTY(0),
    MISSED(1),
    SHIP_HIDDEN(2),
    SHIP_HIT(3),
    SHIP_SUNK(4);

    private final int value;

    ECellState(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
