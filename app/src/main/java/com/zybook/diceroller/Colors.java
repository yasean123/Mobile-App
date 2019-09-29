package com.zybook.diceroller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Colors extends AppCompatActivity {


    public Spinner mySpinner;
    public SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    public LinearLayout seekBarGroup;
    private int seekR, seekG, seekB;
    TextView colorBox;
    private Object colorId;
    private int red,green,blue;
    public int colorSpinner = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable diceDrawable;
        getSupportActionBar().setTitle("Set Color");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        mySpinner = (Spinner) findViewById(R.id.spinner);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        seekBarGroup = (LinearLayout) findViewById(R.id.seekBarGroup);

        seekBarGroup.setVisibility(View.GONE);

        ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(Colors.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.colors));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdaptor);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onMenuChange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        colorBox = findViewById(R.id.colorBox);
        colorBox.setBackgroundColor(Color.parseColor("#ffffff"));

        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekR = progress;
                onColorChange();
            }
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekG = progress;
                onColorChange();
            }
        });

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekB = progress;
                onColorChange();
            }
        });
    }

    public void onColorChange() {
         red = (int) (seekR * 2.55);
         green = (int) (seekG * 2.55);
         blue = (int) (seekB * 2.55);
        colorBox.setBackgroundColor(Color.rgb(red, green, blue));

//        getApplicationContext().setTheme(Integer.parseInt("#D51100"));
//        diceDrawable = getResources().getDrawable(mDice[i].getImageId(),
//                getApplicationContext().setTheme(R.color.colorAccent));
    }

    public void onMenuChange(int position) {
        // Determine which menu option was chosen
        switch (position) {
            case 0:
                System.out.println("This should be red");
                colorBox.setBackgroundColor(Color.RED);
                colorSpinner = Color.RED;
                break;
            case 1:
                System.out.println("This should be green");
                colorBox.setBackgroundColor(Color.GREEN);
                colorSpinner = Color.GREEN;
                break;
            case 2:
                System.out.println("This should be blue");
                colorBox.setBackgroundColor(Color.BLUE);
                colorSpinner = Color.BLUE;
                break;
            case 3:
                System.out.println("This should be cyan");
                colorBox.setBackgroundColor(Color.CYAN);
                colorSpinner = Color.CYAN;
                break;
            case 4:
                System.out.println("This should be yellow");
                colorBox.setBackgroundColor(Color.YELLOW);
                colorSpinner = Color.YELLOW;
                break;
            case 5:
                System.out.println("This should be magenta");
                colorBox.setBackgroundColor(Color.MAGENTA);
                colorSpinner = Color.MAGENTA;
                break;
        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.predefined:
                if (checked) {
                    mySpinner.setVisibility(View.VISIBLE);
                    seekBarGroup.setVisibility(View.GONE);
                    System.out.println("Show predefined");
                }
                break;
            case R.id.custom:
                if (checked) {
                    mySpinner.setVisibility(View.GONE);
                    colorSpinner = 0;
                    seekBarGroup.setVisibility(View.VISIBLE);
                    System.out.println("Show Custom");
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int col = 0;

        if(colorSpinner == 0 ){
            col = Color.rgb(red, green, blue);
        } else {
            col = colorSpinner;
        }

        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = getIntent();
                intent.putExtra("color", col);
                Log.v("PROTO", "VALUE = " + col);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}




