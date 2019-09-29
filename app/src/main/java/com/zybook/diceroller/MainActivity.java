package com.zybook.diceroller;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity extends AppCompatActivity
        implements RollLengthDialogFragment.OnRollLengthSelectedListener {
    public static final int MAX_DICE = 5;
    private static final int REQUEST_CODE = 999;


    private Menu mMenu;
    private CountDownTimer mTimer;
    private int mVisibleDice;
    private int mTimerLength = 2000;
    private int currentDice;
    private Dice[] mDice;
    private ImageView[] mDiceImageViews;
    private GestureDetectorCompat mDetector;
    public LinearLayout dice3Layout;
    public LinearLayout dice4Layout;
    public LinearLayout dice5Layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create an array of Dice
        mDice = new Dice[MAX_DICE];
        for (int i = 0; i < MAX_DICE; i++) {
            mDice[i] = new Dice(i + 1);
        }


        // Create an array of ImageViews
        mDiceImageViews = new ImageView[MAX_DICE];
        mDiceImageViews[0] = findViewById(R.id.dice1);
        mDiceImageViews[1] = findViewById(R.id.dice2);
        mDiceImageViews[2] = findViewById(R.id.dice3);
        mDiceImageViews[3] = findViewById(R.id.dice4);
        mDiceImageViews[4] = findViewById(R.id.dice5);
        dice3Layout = findViewById(R.id.dice3Layout);
//        dice4Layout = findViewById(R.id.dice4Layout);
//        dice5Layout = findViewById(R.id.dice5Layout);
        // All dice are initially visible
        mVisibleDice = MAX_DICE;
        // getDiceColor(R.color.colorDice);
        onUpdateDice(1);
        showDice();


        for (ImageView iv : mDiceImageViews)
            registerForContextMenu(mDiceImageViews[0]);

        mDetector = new GestureDetectorCompat(this, new DiceGestureListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

//    public boolean onLongPressOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.register_context_menu, menu);
//        mMenu = menu;
//        return super.onCreateOptionsMenu(menu);
//    }

    private void showDice() {
        // Display only the number of dice visible
        for (int i = 0; i < mVisibleDice; i++) {
            Drawable diceDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId(),
                        getApplicationContext().getTheme());
            } else {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId());
            }

            mDiceImageViews[i].setImageDrawable(diceDrawable);
            mDiceImageViews[i].setContentDescription(Integer.toString(mDice[i].getNumber()));


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            int c = data.getExtras().getInt("color");
            for (int i = 0; i < mVisibleDice; i++) {
                mDiceImageViews[i].setColorFilter(c);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_stop:
                mTimer.cancel();
                item.setVisible(false);
                return true;
            case R.id.action_roll:
                rollDice();
                return true;
            case R.id.action_one:
                changeDiceVisibility(1);
                onUpdateDice(1);
                showDice();
                return true;
            case R.id.action_two:
                changeDiceVisibility(2);
                onUpdateDice(1);
                showDice();
                return true;
            case R.id.action_three:
                changeDiceVisibility(3);
                onUpdateDice(1);
                showDice();
                return true;
            case R.id.action_four:
                changeDiceVisibility(4);
                onUpdateDice(2);
                showDice();
                return true;
            case R.id.action_five:
                changeDiceVisibility(5);
                onUpdateDice(3);
                showDice();
                return true;
            case R.id.action_about:
                changeToAbout();
                return true;
            // Action item added to app bar
            case R.id.action_roll_length:
                FragmentManager manager = getFragmentManager();
                RollLengthDialogFragment dialog = new RollLengthDialogFragment();
                dialog.show(manager, "rollLengthDialog");
                return true;
            case R.id.action_color:
                Intent intent = new Intent(MainActivity.this, Colors.class);
//                changeToColor();
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        for (int i = 0; i < mDiceImageViews.length; i++)
            if (mDiceImageViews[i] == ((ImageView) v))
                currentDice = i;
    }

    @Override

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_one:
                mDice[currentDice].addOne();
                showDice();
                return true;

            case R.id.subtract_one:
                mDice[currentDice].subtractOne();

                showDice();
                return true;
            case R.id.change_sides:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choose_from);
                final CharSequence[] configs = {"4-Sided", "6-Sided", "10-Sided"};
                builder.setItems(configs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        int currentDice = 0;
                        if (item == 0)                      // 3-Sided
                            mDice[currentDice].setSides(4);
                        else if (item == 1)
                            mDice[currentDice].setSides(6);   // 4-Sided
                        else
                            mDice[currentDice].setSides(10);   // 6-Sided
                        showDice();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                showDice();
                return true;
            case R.id.roll:
                rollDice();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    public void changeToAbout() {
        Intent activity2Intent = new Intent(getApplicationContext(), About.class);
        startActivity(activity2Intent);
    }

    private void changeDiceVisibility(int numVisible) {
        mVisibleDice = numVisible;

        // Make dice visible
        for (int i = 0; i < numVisible; i++) {
            mDiceImageViews[i].setVisibility(View.VISIBLE);
        }

        // Hide remaining dice
        for (int i = numVisible; i < MAX_DICE; i++) {
            mDiceImageViews[i].setVisibility(View.GONE);
        }

    }

    private void rollDice() {
        mMenu.findItem(R.id.action_stop).setVisible(true);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(mTimerLength, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < mVisibleDice; i++) {
                    System.out.println(mVisibleDice);
                    mDice[i].roll();
                }
                showDice();
            }

            public void onFinish() {
                mMenu.findItem(R.id.action_stop).setVisible(false);
            }
        }.start();
    }

    @Override
    public void onRollLengthClick(int which) {
        // Convert to milliseconds
        mTimerLength = 1000 * (which + 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class DiceGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            rollDice();
            return true;
        }
    }

    public void onUpdateDice(int num) {
//        switch (num) {
//            case 1:
//                dice3Layout.setVisibility(View.VISIBLE);
//                dice4Layout.setVisibility(View.GONE);
//                dice5Layout.setVisibility(View.GONE);
//                break;
//            case 2:
//                dice3Layout.setVisibility(View.GONE);
//                dice4Layout.setVisibility(View.VISIBLE);
//                dice5Layout.setVisibility(View.GONE);
//                break;
//            case 3:
//                dice3Layout.setVisibility(View.GONE);
//                dice4Layout.setVisibility(View.GONE);
//                dice5Layout.setVisibility(View.VISIBLE);
//                break;
//
//        }
    }

}