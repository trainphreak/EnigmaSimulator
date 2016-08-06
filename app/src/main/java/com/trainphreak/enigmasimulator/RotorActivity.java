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
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class RotorActivity extends Activity {

    private Spinner reflectorSpinner;
    private Spinner rotor1Spinner;
    private Spinner rotor2Spinner;
    private Spinner rotor3Spinner;
    private WheelView rotor1Ring;
    private WheelView rotor2Ring;
    private WheelView rotor3Ring;
    private SharedPreferences sharedPrefs;

    private AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if (parent.getId() != R.id.reflectorSpinner)
            {
                if (parent.getId() == R.id.rotor1Spinner)
                {
                    if (rotor1Spinner.getSelectedItemPosition() == rotor2Spinner.getSelectedItemPosition())
                    {
                        if (rotor1Spinner.getSelectedItemPosition() != 1 && rotor3Spinner.getSelectedItemPosition() != 1)
                            rotor2Spinner.setSelection(1);
                        else if (rotor1Spinner.getSelectedItemPosition() != 0 && rotor3Spinner.getSelectedItemPosition()!= 0)
                            rotor2Spinner.setSelection(0);
                        else
                            rotor2Spinner.setSelection(2);
                    }
                    else if (rotor1Spinner.getSelectedItemPosition() == rotor3Spinner.getSelectedItemPosition())
                    {
                        if (rotor1Spinner.getSelectedItemPosition() != 2 && rotor2Spinner.getSelectedItemPosition() != 2)
                            rotor3Spinner.setSelection(2);
                        else if (rotor1Spinner.getSelectedItemPosition() != 0 && rotor2Spinner.getSelectedItemPosition()!= 0)
                            rotor3Spinner.setSelection(0);
                        else
                            rotor3Spinner.setSelection(1);
                    }
                }
                else if (parent.getId() == R.id.rotor2Spinner)
                {
                    if (rotor2Spinner.getSelectedItemPosition() == rotor1Spinner.getSelectedItemPosition())
                    {
                        if (rotor2Spinner.getSelectedItemPosition() != 0 && rotor3Spinner.getSelectedItemPosition() != 0)
                            rotor1Spinner.setSelection(0);
                        else if (rotor2Spinner.getSelectedItemPosition() != 1 && rotor3Spinner.getSelectedItemPosition()!= 1)
                            rotor1Spinner.setSelection(1);
                        else
                            rotor1Spinner.setSelection(2);
                    }
                    else if (rotor2Spinner.getSelectedItemPosition() == rotor3Spinner.getSelectedItemPosition())
                    {
                        if (rotor1Spinner.getSelectedItemPosition() != 2 && rotor2Spinner.getSelectedItemPosition() != 2)
                            rotor3Spinner.setSelection(2);
                        else if (rotor1Spinner.getSelectedItemPosition() != 0 && rotor2Spinner.getSelectedItemPosition()!= 0)
                            rotor3Spinner.setSelection(0);
                        else
                            rotor3Spinner.setSelection(1);
                    }
                }
                else if (parent.getId() == R.id.rotor3Spinner)
                {
                    if (rotor3Spinner.getSelectedItemPosition() == rotor1Spinner.getSelectedItemPosition())
                    {
                        if (rotor2Spinner.getSelectedItemPosition() != 0 && rotor3Spinner.getSelectedItemPosition() != 0)
                            rotor1Spinner.setSelection(0);
                        else if (rotor2Spinner.getSelectedItemPosition() != 1 && rotor3Spinner.getSelectedItemPosition()!= 1)
                            rotor1Spinner.setSelection(1);
                        else
                            rotor1Spinner.setSelection(2);
                    }
                    else if (rotor3Spinner.getSelectedItemPosition() == rotor2Spinner.getSelectedItemPosition())
                    {
                        if (rotor1Spinner.getSelectedItemPosition() != 1 && rotor3Spinner.getSelectedItemPosition() != 1)
                            rotor2Spinner.setSelection(1);
                        else if (rotor1Spinner.getSelectedItemPosition() != 0 && rotor3Spinner.getSelectedItemPosition()!= 0)
                            rotor3Spinner.setSelection(0);
                        else
                            rotor3Spinner.setSelection(2);
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotor);

        reflectorSpinner = (Spinner) findViewById(R.id.reflectorSpinner);
        rotor1Spinner = (Spinner) findViewById(R.id.rotor1Spinner);
        rotor2Spinner = (Spinner) findViewById(R.id.rotor2Spinner);
        rotor3Spinner = (Spinner) findViewById(R.id.rotor3Spinner);
        rotor1Ring = (WheelView) findViewById(R.id.rotor1Ring);
        rotor2Ring = (WheelView) findViewById(R.id.rotor2Ring);
        rotor3Ring = (WheelView) findViewById(R.id.rotor3Ring);

        sharedPrefs = this.getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> reflectorSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.reflectors, android.R.layout.simple_spinner_item);
        reflectorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reflectorSpinner.setAdapter(reflectorSpinnerAdapter);

        ArrayAdapter<CharSequence> rotorSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.rotors, android.R.layout.simple_spinner_item);
        rotorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rotor1Spinner.setAdapter(rotorSpinnerAdapter);
        rotor1Spinner.setOnItemSelectedListener(selectedListener);
        rotor2Spinner.setAdapter(rotorSpinnerAdapter);
        rotor2Spinner.setOnItemSelectedListener(selectedListener);
        rotor3Spinner.setAdapter(rotorSpinnerAdapter);
        rotor3Spinner.setOnItemSelectedListener(selectedListener);

        initWheel(rotor1Ring);
        initWheel(rotor2Ring);
        initWheel(rotor3Ring);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        reflectorSpinner.setSelection(sharedPrefs.getInt(MainScreen.REFLECTOR_SETTING, 0));
        rotor1Spinner.setSelection(sharedPrefs.getInt(MainScreen.LEFT_ROTOR, 1) - 1);
        rotor2Spinner.setSelection(sharedPrefs.getInt(MainScreen.MIDDLE_ROTOR, 2) - 1);
        rotor3Spinner.setSelection(sharedPrefs.getInt(MainScreen.RIGHT_ROTOR, 3) - 1);
        rotor1Ring.setCurrentItem(sharedPrefs.getInt(MainScreen.LEFT_ROTOR_RING, 1) - 1);
        rotor2Ring.setCurrentItem(sharedPrefs.getInt(MainScreen.MIDDLE_ROTOR_RING, 1) - 1);
        rotor3Ring.setCurrentItem(sharedPrefs.getInt(MainScreen.RIGHT_ROTOR_RING, 1) - 1);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(MainScreen.REFLECTOR_SETTING, reflectorSpinner.getSelectedItemPosition());
        editor.putInt(MainScreen.LEFT_ROTOR, rotor1Spinner.getSelectedItemPosition() + 1);
        editor.putInt(MainScreen.MIDDLE_ROTOR, rotor2Spinner.getSelectedItemPosition() + 1);
        editor.putInt(MainScreen.RIGHT_ROTOR, rotor3Spinner.getSelectedItemPosition() + 1);
        editor.putInt(MainScreen.LEFT_ROTOR_RING, rotor1Ring.getCurrentItem() + 1);
        editor.putInt(MainScreen.MIDDLE_ROTOR_RING, rotor2Ring.getCurrentItem() + 1);
        editor.putInt(MainScreen.RIGHT_ROTOR_RING, rotor3Ring.getCurrentItem() + 1);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.rotor, menu);
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
            builder.setMessage(getString(R.string.rotor_screen_help_message));
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else if (id == R.id.resetRotors)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.reset);
            builder.setMessage(getString(R.string.reset_rotors_message));
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    reflectorSpinner.setSelection(0);
                    rotor1Spinner.setSelection(0);
                    rotor2Spinner.setSelection(1);
                    rotor3Spinner.setSelection(2);
                    rotor1Ring.setCurrentItem(0);
                    rotor2Ring.setCurrentItem(0);
                    rotor3Ring.setCurrentItem(0);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(MainScreen.REFLECTOR_SETTING, reflectorSpinner.getSelectedItemPosition());
                    editor.putInt(MainScreen.LEFT_ROTOR, 1);
                    editor.putInt(MainScreen.MIDDLE_ROTOR, 2);
                    editor.putInt(MainScreen.RIGHT_ROTOR, 3);
                    editor.putInt(MainScreen.LEFT_ROTOR_RING, 1);
                    editor.putInt(MainScreen.MIDDLE_ROTOR_RING, 1);
                    editor.putInt(MainScreen.RIGHT_ROTOR_RING, 1);
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
                    reflectorSpinner.setSelection(0);
                    rotor1Spinner.setSelection(0);
                    rotor2Spinner.setSelection(1);
                    rotor3Spinner.setSelection(2);
                    rotor1Ring.setCurrentItem(0);
                    rotor2Ring.setCurrentItem(0);
                    rotor3Ring.setCurrentItem(0);
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

    private void initWheel(WheelView wheel)
    {
        wheel.setViewAdapter(new NumericWheelAdapter(this, 1, 26));
        wheel.setCurrentItem(0);

        wheel.setCyclic(true);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
    }
}
