package com.trainphreak.enigmasimulator;

public class Enigma
{
	Rotor rotor1, rotor2, rotor3;
	Reflector reflector;
	Steckerboard steckerboard;

    public Enigma()
    {
        rotor1 = new RotorI();
        rotor2 = new RotorII();
        rotor3 = new RotorIII();
        reflector = new ReflectorB();
        steckerboard = new Steckerboard();
    }

    private void rotateAndKnock()
    {
        boolean knockMiddle = false;
        boolean knockLeft = false;

        char[] rightNotch = rotor3.getNotch();
        char[] middleNotch = rotor2.getNotch();

        for (char c : rightNotch)
        {
            if (rotor3.getPosition() == c)
            {
                knockMiddle = true;
                break;
            }
        }
        for (char c : middleNotch)
        {
            if (rotor2.getPosition() == c)
            {
                knockLeft = true;
                break;
            }
        }

        rotor3.rotate();
        if (knockMiddle || knockLeft)
            rotor2.rotate();
        if (knockLeft)
            rotor1.rotate();
    }

    /**
     *
     * @param rotor 1-8, indicating Rotor wiring I-VIII
     */
    public void setLeftRotor(int rotor)
    {
        Character pos = null;
        Integer ring = null;

        if (rotor1 != null)
        {
            pos = rotor1.getPosition();
            ring = rotor1.getRing();
        }
        switch(rotor)
        {
            case 1:
                rotor1 = new RotorI();
                break;
            case 2:
                rotor1 = new RotorII();
                break;
            case 3:
                rotor1 = new RotorIII();
                break;
            case 4:
                rotor1 = new RotorIV();
                break;
            case 5:
                rotor1 = new RotorV();
                break;
            case 6:
                rotor1 = new RotorVI();
                break;
            case 7:
                rotor1 = new RotorVII();
                break;
            case 8:
                rotor1 = new RotorVIII();
                break;
        }

        if (pos != null && ring != null)
        {
            rotor1.setPosition(pos);
            rotor1.setRing(ring);
        }
    }

    /**
     *
     * @param rotor 1-8, indicating Rotor wiring I-VIII
     */
    public void setMiddleRotor(int rotor)
    {
        Character pos = null;
        Integer ring = null;

        if (rotor2 != null)
        {
            pos = rotor2.getPosition();
            ring = rotor2.getRing();
        }
        switch(rotor)
        {
            case 1:
                rotor2 = new RotorI();
                break;
            case 2:
                rotor2 = new RotorII();
                break;
            case 3:
                rotor2 = new RotorIII();
                break;
            case 4:
                rotor2 = new RotorIV();
                break;
            case 5:
                rotor2 = new RotorV();
                break;
            case 6:
                rotor2 = new RotorVI();
                break;
            case 7:
                rotor2 = new RotorVII();
                break;
            case 8:
                rotor2 = new RotorVIII();
                break;
        }

        if (pos != null && ring != null)
        {
            rotor2.setPosition(pos);
            rotor2.setRing(ring);
        }
    }

    /**
     *
     * @param rotor 1-8, indicating Rotor wiring I-VIII
     */
    public void setRightRotor(int rotor)
    {
        Character pos = null;
        Integer ring = null;

        if (rotor3 != null)
        {
            pos = rotor3.getPosition();
            ring = rotor3.getRing();
        }
        switch(rotor)
        {
            case 1:
                rotor3 = new RotorI();
                break;
            case 2:
                rotor3 = new RotorII();
                break;
            case 3:
                rotor3 = new RotorIII();
                break;
            case 4:
                rotor3 = new RotorIV();
                break;
            case 5:
                rotor3 = new RotorV();
                break;
            case 6:
                rotor3 = new RotorVI();
                break;
            case 7:
                rotor3 = new RotorVII();
                break;
            case 8:
                rotor3 = new RotorVIII();
                break;
        }

        if (pos != null && ring != null)
        {
            rotor3.setPosition(pos);
            rotor3.setRing(ring);
        }
    }

    /**
     *
     * @param pos zero-based alphabetic index (A=0)
     */
    public void setLeftRotorPos(int pos)
    {
        char position = (char)(pos + 65);
        if (rotor1 != null)
            rotor1.setPosition(position);
    }

    /**
     *
     * @param pos zero-based alphabetic index (A=0)
     */
    public void setMiddleRotorPos(int pos)
    {
        char position = (char)(pos + 65);
        if (rotor2 != null)
            rotor2.setPosition(position);
    }

    /**
     *
     * @param pos zero-based alphabetic index (A=0)
     */
    public void setRightRotorPos(int pos)
    {
        char position = (char)(pos + 65);
        if (rotor3 != null)
            rotor3.setPosition(position);
    }

    /**
     *
     * @param ring 0-25, indicating ring setting 1-26
     */
    public void setLeftRotorRing(int ring)
    {
        if (rotor1 != null)
        rotor1.setRing(ring + 1);
    }

    /**
     *
     * @param ring 0-25, indicating ring setting 1-26
     */
    public void setMiddleRotorRing(int ring)
    {
        if (rotor2 != null)
        rotor2.setRing(ring + 1);
    }

    /**
     *
     * @param ring 0-25, indicating ring setting 1-26
     */
    public void setRightRotorRing(int ring)
    {
        if (rotor3 != null)
        rotor3.setRing(ring + 1);
    }

    /**
     *
     * @param reflector 0 or 1, indicating B or C respectively
     */
    public void setReflector(int reflector)
    {
        if (this.reflector != null)
        {
            if (reflector == 0)
                this.reflector = new ReflectorB();
            else
                this.reflector = new ReflectorC();
        }
    }

    /**
     *
     * @param steckers String of 26 characters, with each letter taking the place of the letter it is swapped with
     */
    public void setSteckerboard(String steckers)
    {
        if (steckerboard != null)
        {
            steckerboard.setSteckers(steckers);
        }
    }

    public char getReflector()
    {
        if (reflector instanceof ReflectorB)
            return 'B';
        else
            return 'C';
    }

    public int getLeftRotor()
    {
        if (rotor1 instanceof RotorI)
            return  1;
        else if (rotor1 instanceof RotorII)
            return  2;
        else if (rotor1 instanceof RotorIII)
            return  3;
        else if (rotor1 instanceof RotorIV)
            return  4;
        else if (rotor1 instanceof RotorV)
            return  5;
        else if (rotor1 instanceof RotorVI)
            return  6;
        else if (rotor1 instanceof RotorVII)
            return  7;
        else
            return  8;
    }

    public int getLeftRotorPos()
    {
        return (int)rotor1.position - 65;
    }

    public int getLeftRotorRing()
    {
        return rotor1.ring - 1;
    }

    public int getMiddleRotor()
    {
        if (rotor2 instanceof RotorI)
            return  1;
        else if (rotor2 instanceof RotorII)
            return  2;
        else if (rotor2 instanceof RotorIII)
            return  3;
        else if (rotor2 instanceof RotorIV)
            return  4;
        else if (rotor2 instanceof RotorV)
            return  5;
        else if (rotor2 instanceof RotorVI)
            return  6;
        else if (rotor2 instanceof RotorVII)
            return  7;
        else
            return  8;
    }

    public int getMiddleRotorPos()
    {
        return (int)rotor2.position - 65;
    }

    public int getMiddleRotorRing()
    {
        return rotor2.ring - 1;
    }

    public int getRightRotor()
    {
        if (rotor3 instanceof RotorI)
            return  1;
        else if (rotor3 instanceof RotorII)
            return  2;
        else if (rotor3 instanceof RotorIII)
            return  3;
        else if (rotor3 instanceof RotorIV)
            return  4;
        else if (rotor3 instanceof RotorV)
            return  5;
        else if (rotor3 instanceof RotorVI)
            return  6;
        else if (rotor3 instanceof RotorVII)
            return  7;
        else
            return  8;
    }

    public int getRightRotorPos()
    {
        return (int)rotor3.position - 65;
    }

    public int getRightRotorRing()
    {
        return rotor3.ring - 1;
    }

    public String encipher(String input)
    {

        String output = "";
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            output += encipher(c);
            if (i % 5 == 4)
                output += " ";
        }
        return output;
    }

    private char encipher(char c)
    {
        rotateAndKnock();
        c = steckerboard.stecker(c);
        c = rotor3.encipher(c);
        c = rotor2.encipher(c);
        c = rotor1.encipher(c);
        c = reflector.reflect(c);
        c = rotor1.decipher(c);
        c = rotor2.decipher(c);
        c = rotor3.decipher(c);
        c = steckerboard.stecker(c);

        return c;
    }
}
