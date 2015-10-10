package com.everrin.arithmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NumberSelectActivity extends AppCompatActivity {

    public  final static String NUMBER_NAME = "NUMBER_NAME";
    public  final static String INIT_VALUE_NAME = "INIT_VALUE_NAME";
    public  final static String DISPLAY_STYLE_NAME = "DISPLAY_STYLE_NAME";

    final public static int STYLE_NORMAL = 1;
    final public static int STYLE_WITH_RANDOM = 2;

    private int mStyle = STYLE_NORMAL;

    private int mInitSelectedNumber;

    private final static int[] mButtonIDarray = {R.id.numButton1, R.id.numButton2, R.id.numButton3,
                                        R.id.numButton4, R.id.numButton5, R.id.numButton6,
                                        R.id.numButton7, R.id.numButton8, R.id.numButton9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_select);

        Intent i = this.getIntent();

        mInitSelectedNumber = i.getIntExtra(INIT_VALUE_NAME, 1);
        mStyle = i.getIntExtra(DISPLAY_STYLE_NAME, STYLE_NORMAL);
        initNumberPickupArray();
    }

    private void initNumberPickupArray()
    {
        for(int i = 0; i < mButtonIDarray.length;i++)
        {
            RadioButton btn = (RadioButton)findViewById(mButtonIDarray[i]);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton b = (RadioButton)v;
                    int a = Integer.parseInt(b.getText().subSequence(0, 1).toString());
                    Intent i = new Intent();
                    i.putExtra(NUMBER_NAME, a);
                    NumberSelectActivity.this.setResult(RESULT_OK, i);
                    finish();
                }
            });
            if(i == (mInitSelectedNumber - 1))
            {
                btn.setChecked(true);
            }
        }

        RadioButton btn = (RadioButton)findViewById(R.id.randomButton);
        if(mStyle == STYLE_WITH_RANDOM )
        {
            btn.setChecked(mInitSelectedNumber == 10);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton b = (RadioButton) v;
                    Intent i = new Intent();
                    i.putExtra(NUMBER_NAME, ArithmeticActivity.RANDOM_NUMBER);
                    NumberSelectActivity.this.setResult(RESULT_OK, i);
                    finish();
                }
            });
        }else
        {
            btn.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_number_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
