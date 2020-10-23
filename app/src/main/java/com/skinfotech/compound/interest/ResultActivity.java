package com.skinfotech.compound.interest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView gyr,gmnth,gday,cyr,cmnth,cday,princi;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int number1 = getIntent().getIntExtra("tyr",0);
        int number2 = getIntent().getIntExtra("tmnth",0);
        int number3 = getIntent().getIntExtra("tday",0);
        int number4 = getIntent().getIntExtra("pyr",0);
        int number5 = getIntent().getIntExtra("pmnth",0);
        int number6 = getIntent().getIntExtra("pday",0);
        float number7 = getIntent().getFloatExtra("principle",0);
        float number8 = getIntent().getFloatExtra("rate",0);

        gyr = findViewById(R.id.gyr);
        gmnth = findViewById(R.id.gmnth);
        gday = findViewById(R.id.gday);
        cyr = findViewById(R.id.cyear);
        cmnth = findViewById(R.id.cmnth);
        cday = findViewById(R.id.cday);
        princi = findViewById(R.id.princi);
        button  = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        gyr.setText(""+number1+"-"+number2+"-"+number3);
        gmnth.setText(""+number4+"-"+number5+"-"+number6);
        if (number6 < number3) {
            number6 = number6 + 30;
            number5 = number5 - 1;
        }
        if (number5 < number2) {
            number5 = number5 + 12;
            number4 = number4 - 1;
        }
        int yrdiff = number4 - number1;
        float mnthdiff = number5 - number2;
        float daydiff = number6 - number3;
        float inter;
        float sum = number7;
        for (int i = 1; i <= yrdiff; i++) {
            inter = sum * 12 * number8 / 100;
            sum = sum + inter;
        }

        float totalmnth = mnthdiff + daydiff / 30;
        float mnthinterest = totalmnth * number8 * sum / 100;
        float totalinterest = mnthinterest + sum - number7;
        float totalsum = totalinterest + number7;

        gday.setText("" + yrdiff + " yr -" +
                (int)mnthdiff + " month -" + (int)daydiff+" day");
        cyr.setText(""+number7);
        cmnth.setText(""+number8+"% per month");
        cday.setText("" + totalinterest);
        princi.setText("" + totalsum);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            //search icon
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}