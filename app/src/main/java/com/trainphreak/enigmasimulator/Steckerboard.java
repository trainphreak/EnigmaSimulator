package com.trainphreak.enigmasimulator;

public class Steckerboard
{
    private char[] plugs = new char[26];

    public Steckerboard()
    {
        for (int i = 0; i < plugs.length; i++)
        {
            plugs[i] = (char)(i + 65);
        }
    }

    public void setSteckers(String steckers)
    {
        plugs = steckers.toCharArray();
    }

    public String getSteckers()
    {
        return new String(plugs);
    }

    public void addPair(char c1, char c2)
    {
        int i1 = (int)c1 - 65; // Zero-base representation of c1
        int i2 = (int)c2 - 65;
        
        if (plugs[i1] != c1)
            removePair(i1, plugs[i1]);
        if (plugs[i2] != c2)
            removePair(i2, plugs[i2]);

        plugs[i1] = c2;
        plugs[i2] = c1;
    }

    public void removePair(int i1, char c2)
    {
        int i2 = (int)c2 - 65;
        char c1 = (char)(i1 + 65);

        plugs[i1] = c1;
        plugs[i2] = c2;
    }

    public char stecker(char _c)
    {
        int i = (int)_c - 65;
        return plugs[i];
    }
}