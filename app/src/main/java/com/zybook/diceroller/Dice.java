package com.zybook.diceroller;

import java.util.Random;

public class Dice {

    public static int LARGEST_NUM = 6;
    public static int SMALLEST_NUM = 1;

    private int mNumber = SMALLEST_NUM;
    private int mImageId;
    private Random mRandomGenerator;
    private int largestNum;
    private int Sided;

    public Dice(int number) {
        setNumber(number);
        mRandomGenerator = new Random();
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        if (number >= SMALLEST_NUM && number <= LARGEST_NUM) {
            mNumber = number;
            switch (number) {
                case 1:
                    mImageId = R.drawable.dice_1;
                    break;
                case 2:
                    mImageId = R.drawable.dice_2;
                    break;
                case 3:
                    mImageId = R.drawable.dice_3;
                    break;
                case 4:
                    mImageId = R.drawable.dice_4;
                    break;
                case 5:
                    mImageId = R.drawable.dice_5;
                    break;
                case 6:
                    mImageId = R.drawable.dice_6;
                    break;
            }
        }

    }

    public int getImageId() {
        return mImageId;
    }

    public void addOne() {
        setNumber(mNumber + 1);
    }

    public void subtractOne()
    {
        setNumber(mNumber - 1);
    }

    public void roll()
    {
        setNumber(mRandomGenerator.nextInt(LARGEST_NUM) + 1);
    }

    public void changeSides(int number) {
        if(LARGEST_NUM == 4-Sided){
            switch(number) {
                case 1:
                    mImageId = R.drawable.dice4_1;
                    break;
                case 2:
                    mImageId = R.drawable.dice4_2;
                    break;
                case 3:
                    mImageId = R.drawable.dice4_3;
                    break;
                case 4:
                    mImageId = R.drawable.dice4_4;
                    break;

                }
            }
         }
    private void updateImage(int number) {
        if (largestNum == 6) {
            switch (number) {
                case 1:
                    mImageId = R.drawable.dice_1; break;
                case 2:
                    mImageId = R.drawable.dice_2; break;
                case 3:
                    mImageId = R.drawable.dice_3; break;
                case 4:
                    mImageId = R.drawable.dice_4; break;
                case 5:
                    mImageId = R.drawable.dice_5; break;
                case 6:
                    mImageId = R.drawable.dice_6; break;
            }
        } else if (largestNum == 10) {
            switch (number) {
                case 1:
                    mImageId = R.drawable.dice10_1; break;
                case 2:
                    mImageId = R.drawable.dice10_2; break;
                case 3:
                    mImageId = R.drawable.dice10_3; break;
                case 4:
                    mImageId = R.drawable.dice10_4; break;
                case 5:
                    mImageId = R.drawable.dice10_5; break;
                case 6:
                    mImageId = R.drawable.dice10_6; break;
                case 7:
                    mImageId = R.drawable.dice10_7; break;
                case 8:
                    mImageId = R.drawable.dice10_8; break;
                case 9:
                    mImageId = R.drawable.dice10_9; break;
                case 10:
                    mImageId = R.drawable.dice10_10; break;
            }
        } else { //  must be 4!
            switch (number) {
                case 1:
                    mImageId = R.drawable.dice4_1; break;
                case 2:
                    mImageId = R.drawable.dice4_2; break;
                case 3:
                    mImageId = R.drawable.dice4_3; break;
                case 4:
                    mImageId = R.drawable.dice4_4; break;
            }
            }
        }

    public void setSides(int i) {
    }
}
