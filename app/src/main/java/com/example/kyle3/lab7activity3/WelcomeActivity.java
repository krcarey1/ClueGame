package com.example.kyle3.lab7activity3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button submit = (Button)findViewById(R.id.button);
        // get win history from ClueActivity
        Intent p = getIntent();
        String name = p.getStringExtra("name");
        String count = p.getStringExtra("count");
        String suspect = p.getStringExtra("suspect");
        String room = p.getStringExtra("room");
        String weapon = p.getStringExtra("weapon");

        if (suspect == null) {
            // do not show text
        } else {
            String history = "In the last game, "+ name +" won after "+ count +
                    " rounds by guessing "+ suspect +" in the "+ room +" with a "+ weapon;
            TextView historyText = (TextView) findViewById(R.id.textView3);
            historyText.setText(history); // put history in textView below submit
        }

        // listener for user name submit button
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.editText);
                String uName = input.getText().toString();
                Intent i = new Intent(getApplicationContext(), ClueActivity.class);
                i.putExtra("userName", uName);
                startActivity(i);
            }
        });
    }

    // exits app to home screen when back is pressed
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
