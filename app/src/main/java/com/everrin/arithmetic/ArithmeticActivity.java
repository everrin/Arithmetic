package com.everrin.arithmetic;

import android.content.Intent;
import android.graphics.Color;
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

    //private int a = 0;
    //private int b = 0;
    SimpleFormula mFormula = new SimpleFormula(SimpleFormula.OPERATOR.ADD);
    private int mCorrectCount = 0;
    private int mIncorrectCount = 0;

    private TextView mdisplayTextView;
    private TextView mResultTextView;
    private TextView mStatusTextView;
    private TextView mScoreTextView;

    final public static int RANDOM_NUMBER = 10;
    private int mSelectedMenu = RANDOM_NUMBER;

    private int mOperatorIndex = 0;
    private SimpleFormula.OPERATOR[] mOp = {SimpleFormula.OPERATOR.ADD, SimpleFormula.OPERATOR.SUB, SimpleFormula.OPERATOR.RANDOM};

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
        mResultTextView.setTextColor(Color.BLUE);
        getWindow().setBackgroundDrawableResource(R.drawable.hmbb_bob);

        reset();
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
            mBtns[i].setTextColor(Color.rgb(0x0b2, 0x3a, 0x0ee));
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
                    mFormula.setInputResult(c);
                    checkResult();
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

        sp.setSpan(new ForegroundColorSpan(Color.WHITE), start1, start1 + correctCountString.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.RED), start2, start2 + wrongCountString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mStatusTextView.setText(sp);

        int c = mCorrectCount + mIncorrectCount ;
        float score = c == 0 ? 0 : mCorrectCount * 100 / c;

        mScoreTextView.setText("Score : " + score);
    }

    private void checkResult()
    {
        boolean r = mFormula.checkResult();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            int num = data.getIntExtra(NumberSelectActivity.NUMBER_NAME, 1);
            mSelectedMenu = num;
        }
        generateNewItem();
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
           case R.id.action_choose_op:
               mOperatorIndex++;
               if(mOperatorIndex == mOp.length)
                   mOperatorIndex = 0;
               mFormula.setOperator(mOp[mOperatorIndex]);
               item.setTitle(mOp[mOperatorIndex].toString());
               generateNewItem();
               break;
           case R.id.action_show_add_table:
               Intent i = new Intent(this, AddTableActivity.class);
               this.startActivity(i);
               break;
           case R.id.action_pick_num:
               Intent x = new Intent(this, NumberSelectActivity.class);
               x.putExtra(NumberSelectActivity.INIT_VALUE_NAME, mSelectedMenu);
               x.putExtra(NumberSelectActivity.DISPLAY_STYLE_NAME, NumberSelectActivity.STYLE_WITH_RANDOM);
               startActivityForResult(x, 0);
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
        mFormula.setOperator(mOp[mOperatorIndex]);
        mCorrectCount = 0;
        mIncorrectCount = 0;

        generateNewItem();
        displayOverallStatus();
        mResultTextView.setText("");
    }

    private void generateNewItem()
    {
        if(mOp[mOperatorIndex] == SimpleFormula.OPERATOR.RANDOM)
        {
            Random r = new Random();            ;
            mFormula.setOperator(mOp[r.nextInt(2)]);
        }
        if(mSelectedMenu == RANDOM_NUMBER)
        {
            mFormula.generateNewItem(false, 0);
        }else {
            mFormula.generateNewItem(true, mSelectedMenu);
        }

        mdisplayTextView.setText(mFormula.toString());
    }
}
