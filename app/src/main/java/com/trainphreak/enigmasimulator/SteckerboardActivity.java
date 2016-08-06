package com.trainphreak.enigmasimulator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SteckerboardActivity extends Activity
{

    private Button[] buttons = new Button[26];
    private String steckers;
    private char halfStecker = ' ';
    private SharedPreferences sharedPrefs;

    private String HALFSTECKER = "halfStecker";
    private int GREEN;
    private int BLUE;
    private int defaultButtonBackgroundId = android.R.drawable.btn_default;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steckerboard);
        sharedPrefs = this.getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);

        GREEN = getResources().getColor(R.color.green);
        BLUE = getResources().getColor(R.color.blue);

        buttons[0] = (Button) findViewById(R.id.buttonA);
        buttons[1] = (Button) findViewById(R.id.buttonB);
        buttons[2] = (Button) findViewById(R.id.buttonC);
        buttons[3] = (Button) findViewById(R.id.buttonD);
        buttons[4] = (Button) findViewById(R.id.buttonE);
        buttons[5] = (Button) findViewById(R.id.buttonF);
        buttons[6] = (Button) findViewById(R.id.buttonG);
        buttons[7] = (Button) findViewById(R.id.buttonH);
        buttons[8] = (Button) findViewById(R.id.buttonI);
        buttons[9] = (Button) findViewById(R.id.buttonJ);
        buttons[10] = (Button) findViewById(R.id.buttonK);
        buttons[11] = (Button) findViewById(R.id.buttonL);
        buttons[12] = (Button) findViewById(R.id.buttonM);
        buttons[13] = (Button) findViewById(R.id.buttonN);
        buttons[14] = (Button) findViewById(R.id.buttonO);
        buttons[15] = (Button) findViewById(R.id.buttonP);
        buttons[16] = (Button) findViewById(R.id.buttonQ);
        buttons[17] = (Button) findViewById(R.id.buttonR);
        buttons[18] = (Button) findViewById(R.id.buttonS);
        buttons[19] = (Button) findViewById(R.id.buttonT);
        buttons[20] = (Button) findViewById(R.id.buttonU);
        buttons[21] = (Button) findViewById(R.id.buttonV);
        buttons[22] = (Button) findViewById(R.id.buttonW);
        buttons[23] = (Button) findViewById(R.id.buttonX);
        buttons[24] = (Button) findViewById(R.id.buttonY);
        buttons[25] = (Button) findViewById(R.id.buttonZ);

        for (Button button : buttons) button.setBackgroundResource(defaultButtonBackgroundId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.steckerboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.about)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.about_message)).setTitle(getString(R.string.about_title));
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if(id == R.id.help)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.steckerboard_help_message));
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if(id == R.id.resetSteckers)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.reset);
            builder.setMessage(getString(R.string.steckerboard_reset_message));
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    steckers = getString(R.string.alphabet);
                    for (int i = 0; i < buttons.length; i++)
                    {
                        setButtonText(buttons[i], (char)(i+65));
                        buttons[i].setBackgroundResource(defaultButtonBackgroundId);
                    }
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(MainScreen.STECKERBOARD_SETTINGS, steckers);
                    editor.apply();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if(id == R.id.resetAll)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.reset_all_message));
            builder.setTitle(R.string.reset_title);
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(getString(R.string.reset_all), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    steckers = getString(R.string.alphabet);
                    for (int i = 0; i < buttons.length; i++)
                    {
                        setButtonText(buttons[i],(char)(i+65));
                        buttons[i].setBackgroundResource(defaultButtonBackgroundId);
                    }

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(MainScreen.LEFT_ROTOR, 1);
                    editor.putInt(MainScreen.MIDDLE_ROTOR, 2);
                    editor.putInt(MainScreen.RIGHT_ROTOR, 3);
                    editor.putInt(MainScreen.LEFT_ROTOR_POS, 1);
                    editor.putInt(MainScreen.MIDDLE_ROTOR_POS, 1);
                    editor.putInt(MainScreen.RIGHT_ROTOR_POS, 1);
                    editor.putInt(MainScreen.LEFT_ROTOR_RING, 1);
                    editor.putInt(MainScreen.MIDDLE_ROTOR_RING, 1);
                    editor.putInt(MainScreen.RIGHT_ROTOR_RING, 1);
                    editor.putInt(MainScreen.REFLECTOR_SETTING, 0);
                    editor.putString(MainScreen.STECKERBOARD_SETTINGS, getString(R.string.alphabet));
                    editor.putString(MainScreen.INPUT_BOX_TEXT, getString(R.string.blank));
                    editor.apply();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        steckers = sharedPrefs.getString(MainScreen.STECKERBOARD_SETTINGS, getString(R.string.alphabet));
        for (int i = 0; i < buttons.length; i++)
        {
            setButtonText(buttons[i], steckers.charAt(i));
            if ((char)(i+65) == halfStecker)
                buttons[i].setBackgroundColor(GREEN);
            else
                if (getButtonText(buttons[i]) != (char)(i+65))
                    buttons[i].setBackgroundColor(BLUE);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(MainScreen.STECKERBOARD_SETTINGS, steckers);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState( Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putChar(HALFSTECKER, halfStecker);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        halfStecker = savedInstanceState.getChar(HALFSTECKER);
    }

    public void buttonClick(View v)
    {
        Button button = (Button) v;
        int index = getButtonIndex(button);
        char letter = (char)(index + 65);

        if (halfStecker == ' ')
        {
            halfStecker = letter;
            button.setBackgroundColor(GREEN);
        }
        else
        {
            if (letter == halfStecker)
            {
                if (letter == steckers.charAt(index)) // if character is unsteckered
                    button.setBackgroundResource(defaultButtonBackgroundId);
                else
                    button.setBackgroundColor(BLUE);
            }
            else
            {
                // if the characters are steckered to each other, set them to themselves
                // otherwise, stecker them to each other (which will undo any steckers they are currently in)
                if (steckers.charAt(index) == halfStecker)
                {
                    removePair(letter, halfStecker);
                }
                else
                {
                    addPair(halfStecker, letter);
                }

                // since the steckers have changed, update the stecker string variable
                char[] temp = new char[26];
                for (int i = 0; i < buttons.length; i++)
                {
                    temp[i] = getButtonText(buttons[i]);
                }
                steckers = new String(temp);
            }

            halfStecker = ' ';
        }
    }

    private void addPair(char c1, char c2)
    {
        int i1 = (int)c1 - 64;
        int i2 = (int)c2 - 64;

        if (steckers.charAt(i1 - 1) != c1) // If the first character is steckered
        {
            // undo stecker
            removePair(c1, steckers.charAt(i1 - 1));
        }

        if (steckers.charAt(i2 - 1) != c2) // If the second character is steckered
        {
            // undo stecker
            removePair(c2, steckers.charAt(i2 - 1));
        }

        // Now that any existing steckers are undone, stecker the characters
        setButtonText(buttons[i1 - 1], c2);
        buttons[i1 - 1].setBackgroundColor(BLUE);
        setButtonText(buttons[i2 -1], c1);
        buttons[i2 - 1].setBackgroundColor(BLUE);
    }

    private void removePair(char c1, char c2)
    {
        int i1 = (int)c1 - 64;
        int i2 = (int)c2 - 64;

        setButtonText(buttons[i1-1],(char)(i1+64));
        buttons[i1 - 1].setBackgroundResource(defaultButtonBackgroundId);
        setButtonText(buttons[i2 -1], (char)(i2+64));
        buttons[i2 - 1].setBackgroundResource(defaultButtonBackgroundId);
    }

    private int getButtonIndex(Button b)
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (buttons[i] == b)
                return i;
        }
        return -1;
    }

    private char getButtonText(Button button)
    {
        return button.getText().charAt(3);
    }

    private void setButtonText(Button button, char c)
    {
        char letter = button.getText().charAt(0);
        button.setText(letter + " (" + c + ")");
    }
}
