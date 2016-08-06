package com.trainphreak.enigmasimulator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class MainScreen extends Activity
{
    private Enigma enigma;
    private static final Character[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    public static final String INPUT_BOX_TEXT = "INPUT_BOX_TEXT";
    public static final String LEFT_ROTOR = "LEFT_ROTOR";
    public static final String MIDDLE_ROTOR = "MIDDLE_ROTOR";
    public static final String RIGHT_ROTOR = "RIGHT_ROTOR";
    public static final String LEFT_ROTOR_POS = "LEFT_ROTOR_POS";
    public static final String MIDDLE_ROTOR_POS = "MIDDLE_ROTOR_POS";
    public static final String RIGHT_ROTOR_POS = "RIGHT_ROTOR_POS";
    public static final String LEFT_ROTOR_RING = "LEFT_ROTOR_RING";
    public static final String MIDDLE_ROTOR_RING = "MIDDLE_ROTOR_RING";
    public static final String RIGHT_ROTOR_RING = "RIGHT_ROTOR_RING";
    public static final String REFLECTOR_SETTING = "REFLECTOR_SETTING";
    public static final String STECKERBOARD_SETTINGS = "STECKERBOARD_SETTINGS";

    private WheelView leftRotor;
    private WheelView middleRotor;
    private WheelView rightRotor;
    private EditText inputBox;
    private TextView outputBox;

    private SharedPreferences sharedPrefs;

    // Wheel scrolled listener
    private OnWheelScrollListener scrolledListener = new OnWheelScrollListener()
    {
        public void onScrollingStarted(WheelView wheel)
        {
        }

        public void onScrollingFinished(WheelView wheel)
        {
            updateRotorPos(wheel);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
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
        else if (id == R.id.help)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.main_screen_help));
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if (id == R.id.clearInput)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.clear_input_title));
            builder.setMessage(getString(R.string.clear_input_message));
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.setPositiveButton(getString(R.string.clear), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    outputBox.setText(R.string.blank);
                    inputBox.setText(R.string.blank);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(INPUT_BOX_TEXT, getString(R.string.blank));
                    editor.apply();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (id == R.id.resetPositions)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.reset_positions_message));
            builder.setTitle(getString(R.string.reset_title));
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(getString(R.string.reset), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    leftRotor.setCurrentItem(0);
                    middleRotor.setCurrentItem(0);
                    rightRotor.setCurrentItem(0);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(LEFT_ROTOR_POS, 1);
                    editor.putInt(MIDDLE_ROTOR_POS, 1);
                    editor.putInt(RIGHT_ROTOR_POS, 1);
                    editor.apply();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }
        else if (id == R.id.resetAll)
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
                    inputBox.setText(R.string.blank);
                    outputBox.setText(R.string.blank);
                    leftRotor.setCurrentItem(0);
                    middleRotor.setCurrentItem(0);
                    rightRotor.setCurrentItem(0);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(LEFT_ROTOR, 1);
                    editor.putInt(MIDDLE_ROTOR, 2);
                    editor.putInt(RIGHT_ROTOR, 3);
                    editor.putInt(LEFT_ROTOR_POS, 1);
                    editor.putInt(MIDDLE_ROTOR_POS, 1);
                    editor.putInt(RIGHT_ROTOR_POS, 1);
                    editor.putInt(LEFT_ROTOR_RING, 1);
                    editor.putInt(MIDDLE_ROTOR_RING, 1);
                    editor.putInt(RIGHT_ROTOR_RING, 1);
                    editor.putInt(REFLECTOR_SETTING, 0);
                    editor.putString(STECKERBOARD_SETTINGS, getString(R.string.alphabet));
                    editor.putString(INPUT_BOX_TEXT, getString(R.string.blank));
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);



        Button rotorsButton = (Button) findViewById(R.id.rotorsButton);
        Button steckerboardButton = (Button) findViewById(R.id.steckerboardButton);
        Button runButton = (Button) findViewById(R.id.runButton);
        leftRotor = (WheelView) findViewById(R.id.rotor1Pos);
        middleRotor = (WheelView) findViewById(R.id.rotor2Pos);
        rightRotor = (WheelView) findViewById(R.id.rotor3Pos);
        inputBox = (EditText) findViewById(R.id.inputBox);
        outputBox = (TextView) findViewById(R.id.outputBox);

        initWheel(leftRotor);
        initWheel(middleRotor);
        initWheel(rightRotor);

        sharedPrefs = this.getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);

        rotorsButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent rotorIntent = new Intent(v.getContext(), RotorActivity.class);
                startActivity(rotorIntent);
            }
        });

        steckerboardButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent steckerboardIntent = new Intent(v.getContext(), SteckerboardActivity.class);
                startActivity(steckerboardIntent);
            }
        });

        runButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                {
                    outputBox.setText(R.string.blank);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(LEFT_ROTOR_POS, leftRotor.getCurrentItem() + 1);
                    editor.putInt(MIDDLE_ROTOR_POS, middleRotor.getCurrentItem() + 1);
                    editor.putInt(RIGHT_ROTOR_POS, rightRotor.getCurrentItem() + 1);
                    editor.putString(INPUT_BOX_TEXT, inputBox.getText().toString());
                    editor.apply();
                }

                boolean nonAlphaChars = false;
                char[] inputBoxChars = inputBox.getText().toString().toUpperCase().toCharArray();
                final char[] inputChars = new char[inputBoxChars.length];
                int i = 0;
                for (char c : inputBoxChars)
                {
                    if (Character.isLetter(c))
                    {
                        inputChars[i] = c;
                        i++;
                    }
                    else if (c != ' ')
                    {
                        nonAlphaChars = true;
                    }
                }
                for (; i < inputChars.length; i++)
                    inputChars[i] = ' ';
                if (nonAlphaChars)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.letters_only_title);
                    builder.setMessage(R.string.letters_only_message);
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.setPositiveButton(R.string.continue_text, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String inputString = new String(inputChars).trim();
                            outputBox.setText(enigma.encipher(inputString));
                            leftRotor.setCurrentItem(enigma.getLeftRotorPos());
                            middleRotor.setCurrentItem(enigma.getMiddleRotorPos());
                            rightRotor.setCurrentItem(enigma.getRightRotorPos());
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putInt(LEFT_ROTOR_POS, leftRotor.getCurrentItem() + 1);
                            editor.putInt(MIDDLE_ROTOR_POS, middleRotor.getCurrentItem() + 1);
                            editor.putInt(RIGHT_ROTOR_POS, rightRotor.getCurrentItem() + 1);
                            editor.apply();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    String inputString = new String(inputChars).trim();
                    outputBox.setText(enigma.encipher(inputString));
                    leftRotor.setCurrentItem(enigma.getLeftRotorPos());
                    middleRotor.setCurrentItem(enigma.getMiddleRotorPos());
                    rightRotor.setCurrentItem(enigma.getRightRotorPos());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(LEFT_ROTOR_POS, leftRotor.getCurrentItem() + 1);
                    editor.putInt(MIDDLE_ROTOR_POS, middleRotor.getCurrentItem() + 1);
                    editor.putInt(RIGHT_ROTOR_POS, rightRotor.getCurrentItem() + 1);
                    editor.apply();
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        enigma = new Enigma();
        enigma.setLeftRotor(sharedPrefs.getInt(LEFT_ROTOR, 1));
        enigma.setMiddleRotor(sharedPrefs.getInt(MIDDLE_ROTOR, 2));
        enigma.setRightRotor(sharedPrefs.getInt(RIGHT_ROTOR, 3));
        enigma.setLeftRotorPos(sharedPrefs.getInt(LEFT_ROTOR_POS, 1) - 1);
        leftRotor.setCurrentItem(enigma.getLeftRotorPos());
        enigma.setMiddleRotorPos(sharedPrefs.getInt(MIDDLE_ROTOR_POS, 1) - 1);
        middleRotor.setCurrentItem(enigma.getMiddleRotorPos());
        enigma.setRightRotorPos(sharedPrefs.getInt(RIGHT_ROTOR_POS, 1) - 1);
        rightRotor.setCurrentItem(enigma.getRightRotorPos());
        enigma.setLeftRotorRing(sharedPrefs.getInt(LEFT_ROTOR_RING, 1) - 1);
        enigma.setMiddleRotorRing(sharedPrefs.getInt(MIDDLE_ROTOR_RING, 1) - 1);
        enigma.setRightRotorRing(sharedPrefs.getInt(RIGHT_ROTOR_RING, 1) - 1);
        enigma.setReflector(sharedPrefs.getInt(REFLECTOR_SETTING, 0));
        enigma.setSteckerboard(sharedPrefs.getString(STECKERBOARD_SETTINGS, getString(R.string.alphabet)));
        inputBox.setText(sharedPrefs.getString(INPUT_BOX_TEXT, getString(R.string.blank)));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(LEFT_ROTOR_POS, leftRotor.getCurrentItem() + 1);
        editor.putInt(MIDDLE_ROTOR_POS, middleRotor.getCurrentItem() + 1);
        editor.putInt(RIGHT_ROTOR_POS, rightRotor.getCurrentItem() + 1);
        editor.putString(INPUT_BOX_TEXT, inputBox.getText().toString());
        editor.apply();
    }

    // Heavy lifting for setting up each wheel
    private void initWheel(WheelView wheel)
    {
        wheel.setViewAdapter(new ArrayWheelAdapter<Character>(this, alphabet));
        wheel.setCurrentItem(0);

        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
    }

    private void updateRotorPos(WheelView wheel)
    {
        switch (wheel.getId())
        {
            case R.id.rotor1Pos:
                enigma.setLeftRotorPos(leftRotor.getCurrentItem());
                break;
            case R.id.rotor2Pos:
                enigma.setMiddleRotorPos(middleRotor.getCurrentItem());
                break;
            case R.id.rotor3Pos:
                enigma.setRightRotorPos(rightRotor.getCurrentItem());
                break;
        }
    }
}
