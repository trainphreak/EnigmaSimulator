package com.trainphreak.enigmasimulator;

public abstract class Rotor
{
	char position;
    int ring;
    char[] notch;
    String wiring;
	
	public Rotor()
	{
		position = 'A';
		ring = 1;
	}
	
	public void setPosition(char newPos)
	{
		position = newPos;
	}
	
	public char getPosition()
	{
		return position;
	}
	
	public void rotate()
	{
		int num = ((int)position) - 64; // Convert A to 1, B to 2, etc
		num++;
		if (num > 26)
			num -= 26; // Wrap if needed
		position = (char)(num + 64); // Convert back to ASCII/Unicode
	}
	
	public void setRing(int newRing)
	{
		ring = newRing;
	}
	
	public int getRing()
	{
		return ring;
	}
	
	public char[] getNotch() 
	{
		return notch;
	}
	
	public char encipher(char _c)
	{
		int i = ((int)_c - 64) + ((int)position - 64) - ring;
		if (i > 26)
			i -= 26;
		if (i < 1)
			i += 26;
        i = ((int)wiring.charAt(i-1) - 64) - ((int)position - 64) + ring;
        if (i > 26)
            i -= 26;
        if (i < 1)
            i += 26;
        char c = (char)(i + 64);
		//char c = (char)(((int)wiring.charAt(i - 1)/*zero-based index*/ - 64) - ((int)position - 64) + ring);
		
		return c;
	}
	
	public char decipher(char _c)
	{
		int i = ((int)_c - 64) + ((int)position - 64) - ring;
		if (i > 26)
			i -= 26;
		if (i < 1)
			i += 26;
		char c = (char)(i + 64);
		i = wiring.indexOf(Character.toString(c)) + 1;
        i = i - ((int)position - 64) + ring;
        if (i > 26)
            i -= 26;
        if (i < 1)
            i += 26;
		c = (char)(i + 64);
		
		return c;
	}
}
