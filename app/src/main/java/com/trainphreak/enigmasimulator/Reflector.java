package com.trainphreak.enigmasimulator;

public abstract class Reflector
{
    String wiring;
	
	public char reflect(char _c)
	{
		int i = wiring.indexOf(_c);
		
		if (i % 2 == 0)
			return wiring.charAt(i+1);
		else // i % 2 == 1
			return wiring.charAt(i-1);
	}
}
