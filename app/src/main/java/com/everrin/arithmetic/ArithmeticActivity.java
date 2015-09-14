package com.everrin.arithmetic;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ArithmeticActivity extends AppCompatActivity {

    private int a = 0;
    private int b = 0;
    private int mCorrectCount = 0;
    private int mIncorrectCount = 0;

    private TextView mdisplayTextView;
    private TextView mResultTextView;
    private TextView mStatusTextView;
    private TextView mScoreTextView;

    private int mSelectedMenu = R.id.action_random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);

        initBottons();
        mdisplayTextView = (TextView)findViewById(R.id.display_textView);
        mdisplayTextView.setTextColor(Color.WHITE);
        mResultTextView = (TextView)findViewById(R.id.result_view);
        mStatusTextView = (TextView)findViewById(R.id.status_textView);
        mScoreTextView  = (TextView)findViewById(R.id.score_textView);

        generateNewItem();
        displayOverallStatus();
        mResultTextView.setTextColor(Color.BLUE);

        //getWindow().setBackgroundDrawableResource(R.drawable.hmbb);
        //getWindow().setBackgroundDrawableResource(R.drawable.pdx);
        getWindow().setBackgroundDrawableResource(R.drawable.building);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arithmetic, menu);
        return true;
    }
    final private Button[]  mBtns = new Button[20];

    final private int[]    mButtonIDs = {
        R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05,
        R.id.button06, R.id.button07, R.id.button08, R.id.button09, R.id.button10,
        R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
        R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20
    };

    private void initBottons()
    {
        for(int i = 0; i < mBtns.length; i++)
        {
            mBtns[i] = (Button)this.findViewById(mButtonIDs[i]);
            mBtns[i].setTextSize(25);
            mBtns[i].setTextColor(Color.WHITE);
            mBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Button btn = (Button)v;
                   String s = btn.getText().toString();
                    if(s.charAt(0) == ' ')
                    {
                        s = s.substring(1);
                    }
                   int c = Integer.parseInt(s);
                    checkResult(a, b, c);
                   generateNewItem();
                }
            });
        }
    }

    private void displayOverallStatus()
    {
        String  correctString = "Correct ";
        String  WrongString   =  " Incorrect ";
        String  correctCountString = String.format("%d", mCorrectCount);
        String  wrongCountString   = String.format("%d", mIncorrectCount);

        int start1 = correctString.length();
        int start2 = correctString.length() + correctCountString.length() + WrongString.length();

        String s = correctString + correctCountString + WrongString + wrongCountString;
        SpannableString sp = new SpannableString(s);

        sp.setSpan(new ForegroundColorSpan(Color.GREEN), start1, start1 + correctCountString.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.RED), start2, start2 + wrongCountString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mStatusTextView.setText(sp);

        int c = mCorrectCount + mIncorrectCount ;
        float score = c == 0 ? 0 : mCorrectCount * 100 / c;

        mScoreTextView.setText("Score : " + score);
    }

    private void checkResult(int _a, int _b, int _c)
    {
        boolean r = ((_a + _b) == _c ) ? true: false;

        if(r) {
            mResultTextView.setText("correct");
            mResultTextView.setTextColor(Color.BLUE);
            mCorrectCount++;
        }else {
            mResultTextView.setText("wrong");
            mResultTextView.setTextColor(Color.RED);
            mIncorrectCount++;
        }
        displayOverallStatus();
    }

    final private int[] mMenus = {R.id.action_random,
             R.id.action_1,
             R.id.action_2,
             R.id.action_3,
             R.id.action_4,
             R.id.action_5,
             R.id.action_6,
             R.id.action_7,
             R.id.action_8,
             R.id.action_9
};

    private int getNumberFromMenu(int menuID)
    {
        for(int i = 0; i < mMenus.length; i++)
        {
            if(mMenus[i] == menuID)
                return i;
        }

        return 0;
    }

    private int getMenuIdFromNumber(int i)
    {
        return mMenus[i];
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(mSelectedMenu).setChecked(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch(id)
       {
           case R.id.action_random:
           case R.id.action_1:
           case R.id.action_2:
           case R.id.action_3:
           case R.id.action_4:
           case R.id.action_5:
           case R.id.action_6:
           case R.id.action_7:
           case R.id.action_8:
           case R.id.action_9:
                mSelectedMenu = id;
               generateNewItem();
               break;

           case R.id.action_reset:
               reset();
               break;
           default:
               break;
       }

        return super.onOptionsItemSelected(item);
    }

    private void reset()
    {
        a = 0;
        b = 0;
        mCorrectCount = 0;
        mIncorrectCount = 0;

        generateNewItem();
        displayOverallStatus();
        mResultTextView.setText("");
    }

    private void generateNewItem()
    {
        Random r = new Random();
        if(mSelectedMenu == R.id.action_random)
        {
            a = r.nextInt(9) + 1;
        }else {
            a = getNumberFromMenu(mSelectedMenu);
        }
        b = r.nextInt(9) + 1;

        mdisplayTextView.setText(String.format("%d + %d = ?", a, b));
    }
}
