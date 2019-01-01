package com.example.android.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printToLogs(View view) {
        // Find first menu item TextView and print the text to the logs
        TextView txt1 = (TextView) findViewById(R.id.menu_item_1);
        String str1 = txt1.getText().toString();
        Log.i(getApplicationContext(), str1);

        // Find second menu item TextView and print the text to the logs
        TextView txt2 = (TextView) findViewById(R.id.menu_item_2);
        String str2 = txt2.getText().toString();
        Log.i(getApplicationContext(), str2);

        // Find third menu item TextView and print the text to the logs
        TextView txt3 = (TextView) findViewById(R.id.menu_item_3);
        String str3 = txt3.getText().toString();
        Log.i(getApplicationContext(), str3);
    }
}