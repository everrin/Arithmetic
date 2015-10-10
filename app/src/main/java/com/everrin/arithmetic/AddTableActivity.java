package com.everrin.arithmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AddTableActivity extends AppCompatActivity {

    private int number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

        initTextviewArray();
        displayTable(number);
    }

    private int[] TextViewId = {R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
    private TextView[] displayTextView = new TextView[9];

    private void initTextviewArray()
    {
        for(int i = 0; i < TextViewId.length; i++)
        {
            displayTextView[i] = (TextView)findViewById(TextViewId[i]);
        }
    }

    private  void displayTable(int n)
    {
        for(int i = 1; i <= TextViewId.length; i++)
        {
            displayTextView[i-1].setText(""+ n  + " + " + i + " = " + (n + i));
        }
    }

    private void nextTable()
    {
        number++;
        if(number >= 10)
        {
            number = 1;
        }

        displayTable(number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_table_next) {
            nextTable();
            return true;
        }
        if(id == R.id.action_show_selector) {
            Intent i = new Intent(this, NumberSelectActivity.class);
            i.putExtra(NumberSelectActivity.INIT_VALUE_NAME, number);
            startActivityForResult(i, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            number = data.getIntExtra(NumberSelectActivity.NUMBER_NAME, 1);
            displayTable(number);
        }
    }
}
