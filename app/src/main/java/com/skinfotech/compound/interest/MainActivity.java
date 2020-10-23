package com.skinfotech.compound.interest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     EditText tyr, tmnth, tday, pyr, pmnth, pday, principle, rate;
     Button calculate, reset;
     TextView answer,month,year,day;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        day = findViewById(R.id.day);

        Date currentTime  = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        String[]  splitDate = formattedDate.split(",");

        day.setText(splitDate[0]);
        month.setText(splitDate[1]);
        year.setText(splitDate[2]);

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);


        tyr = findViewById(R.id.tyr);
        tmnth = findViewById(R.id.tmnth);
        tday = findViewById(R.id.tday);
        pyr = findViewById(R.id.pyr);
        pmnth =findViewById(R.id.pmnth);
        pday = findViewById(R.id.pday);
        principle =findViewById(R.id.principle);
        rate = findViewById(R.id.rate);
        calculate =findViewById(R.id.calculate);
        reset = findViewById(R.id.reset);
        answer = findViewById(R.id.answer);

        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(tyr.getText())&& !TextUtils.isEmpty(tmnth.getText()) && !TextUtils.isEmpty(tday.getText()) && !TextUtils.isEmpty(pyr.getText()) && !TextUtils.isEmpty(pmnth.getText()) && !TextUtils.isEmpty(pday.getText()) && !TextUtils.isEmpty(principle.getText()) && !TextUtils.isEmpty(rate.getText())) {
                    int taken_month = Integer.parseInt(tmnth.getText().toString());
                    int taken_day = Integer.parseInt(tday.getText().toString());
                    int pay_month = Integer.parseInt(pmnth.getText().toString());
                    int pay_day = Integer.parseInt(pday.getText().toString());
                    int taken_year = Integer.parseInt(tyr.getText().toString());
                    int pay_year =Integer.parseInt(pyr.getText().toString());
                    if ( taken_year> pay_year ||taken_year<1900 || taken_year >2200 ||pay_year<1900 || pay_year >2200|| taken_month <1|| taken_month > 12 || taken_day <1 || taken_day > 32 || pay_month <1 || pay_month > 12  || pay_day <1||pay_day > 32) {
                        Toast.makeText(MainActivity.this, "Please fill valid date!!", Toast.LENGTH_SHORT).show();
                        answer.setText("INVALID DATE!!" );

                    } else {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        int ty = Integer.parseInt(tyr.getText().toString());
                        int tmnt = Integer.parseInt(tmnth.getText().toString());
                        int tda = Integer.parseInt(tday.getText().toString());
                        int py = Integer.parseInt(pyr.getText().toString());
                        int pmnt = Integer.parseInt(pmnth.getText().toString());
                        int pda = Integer.parseInt(pday.getText().toString());
                        float princi = Float.parseFloat(principle.getText().toString());
                        float ratt = Float.parseFloat(rate.getText().toString());
                        answer.setText("" );
                        intent.putExtra("tyr", ty);
                        intent.putExtra("tmnth", tmnt);
                        intent.putExtra("tday", tda);
                        intent.putExtra("pyr", py);
                        intent.putExtra("pmnth", pmnt);
                        intent.putExtra("pday", pda);
                        intent.putExtra("principle", princi);
                        intent.putExtra("rate", ratt);

                        startActivity(intent);

                    }}else {
                    Toast.makeText(MainActivity.this, "Please fill the required data!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tyr.setText(null);
                pyr.setText(null);
                tmnth.setText(null);
                tday.setText(null);
                pmnth.setText(null);
                pday.setText(null);
                principle.setText(null);
                rate.setText(null);
                answer.setText("Results Here");

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.splashlogo)
                    .setTitle("COMPOUND INTEREST APP")
                    .setMessage("Are you sure you want to close this app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_privacy) {
            Intent brointent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pandit-sk.blogspot.com/p/privacy-policy-for-compound-interest-app.html"));
            startActivity(brointent);

            return true;
        } else if (id==R.id.action_terms){
            Intent brointent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pandit-sk.blogspot.com/p/privacy-policy-for-compound-interest-app.html"));
            startActivity(brointent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.nav_rateme) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "This is the very best app to calculate COMPOUND INTEREST in easy way within a second in a single click. This is the one and only my best app on playstore.  Once you download this app and use, you also will really love this app. Follow the link for download this app.\n" + Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
            String shareSubject = "Sharing COMPOUND INTEREST App";
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            startActivity(Intent.createChooser(sharingIntent, "Share Using:"));

        } else if (id == R.id.nav_more) {
            Intent brointent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=SK+InfoTech&hl=en"));
            startActivity(brointent);
        } else if (id == R.id.nav_update) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        } else if (id == R.id.na_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("Email:"));
            String[] mail = {"pradipdiwana65@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, mail);
            intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack From COMPOUND INTEREST App:");
            intent.putExtra(Intent.EXTRA_TEXT, "Name: " + "\n" + "Address: " + "\n" + "Contact No.:");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Select Email"));

        } else if (id == R.id.nav_exit) {
            finish();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}