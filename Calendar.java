package com.example.abdel.ecommerceproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final CalendarView c = (CalendarView)findViewById(R.id.calendarView);
       c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

               Registration.birthdate = String.valueOf(i) + "/" + String.valueOf(i1) + "/" +String.valueOf(i2);
               ForgetPassword.Birthdate = String.valueOf(i) + "/" + String.valueOf(i1) + "/" +String.valueOf(i2);
           }
       });
    }
}
