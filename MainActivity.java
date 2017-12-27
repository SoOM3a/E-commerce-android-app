package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences ;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences sharedPreferences ;
    public static SharedPreferences.Editor editor ;
    List<String> Emails ;
    private DBhelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.btn_login);
        sharedPreferences = getSharedPreferences("Loginref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(db == null) {
            db = new DBhelper(getApplicationContext());

            Emails = db.GetAllUser();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        boolean rm = sharedPreferences.getBoolean("rm", false);
        if (rm == true) {
           ((EditText) findViewById(R.id.txt_email)).setText(sharedPreferences.getString("email", ""));
            ((EditText) findViewById(R.id.txt_password)).setText(sharedPreferences.getString("password", ""));
            login();

        }
    }

private  void login()
{
    final  CheckBox rm = (CheckBox) findViewById(R.id.chb_rm);
    String email = ((EditText) findViewById(R.id.txt_email)).getText().toString();
    String password = ((EditText) findViewById(R.id.txt_password)).getText().toString();

    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(getApplicationContext(), "Invalid Information", Toast.LENGTH_LONG).show();
    }
    else if (password.length() <= 4) {
        Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_LONG).show();
    }
    else if (!Checkmail(email)) {
        Toast.makeText(getApplicationContext(), "email is not Valid", Toast.LENGTH_LONG).show();
    }
    else {
        if (password.equals(db.getPass(email)))
        {

            if(rm.isChecked())
            {
                editor.putBoolean("rm",true);
                editor.putString("email",email);
                editor.putString("password",password);
                editor.commit();
            }
                Toast.makeText(getApplicationContext(), "Categoeylist Succesfully .", Toast.LENGTH_LONG).show();
            Intent CatagoryIntent = new Intent(getApplicationContext(), Categoeylist.class);
            CatagoryIntent.putExtra("email",email);
            startActivity(CatagoryIntent);
                 finish();

        }
        else
            Toast.makeText(getApplicationContext(), "Email or Password not valid .", Toast.LENGTH_LONG).show();

    }
}

    public Boolean Checkmail(String emailValue) {

        Boolean ch1 = false  ;
      Emails = db.GetAllUser();
        for(int i = 0 ; i< Emails.size() ;i++)
            if(Emails.get(i).matches(emailValue)) {
                 ch1 = true;
            }

        return  (android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()&&ch1) ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginmn, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int ItemSelect = item.getItemId();
        if (ItemSelect == R.id.mn_signup)
        {
            Intent i = new Intent(getApplicationContext(), Registration.class);
            startActivity(i);
        }
        else  if (ItemSelect == R.id.mn_forgetP)
        {
            Intent i = new Intent(getApplicationContext(), ForgetPassword.class);
            startActivity(i);
        }
    return  true ;
    }
}

