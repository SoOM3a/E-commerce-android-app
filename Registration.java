package com.example.abdel.ecommerceproject;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Registration extends AppCompatActivity {

    DBhelper db ;
     List<String> Emails ;
   public  static  String birthdate = "1/9/2018";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if(db == null)
            db =new DBhelper(getApplicationContext());

        Emails = db.GetAllUser();

        Button btn_Reg = (Button) findViewById(R.id.btn_regs);
        Button btn_birthdate = (Button)findViewById(R.id.btn_setBirthdate);

        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration();

            }
        });

       btn_birthdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent bd = new Intent(getApplication(), com.example.abdel.ecommerceproject.Calendar.class);
               startActivity(bd);
           }
       });

    }


    private void Registration() {

        String  email = ( (EditText) findViewById(R.id.txt_email) ).getText().toString() ;
        String username = ( (EditText) findViewById(R.id.txt_username)).getText().toString() ;
        String Password = ( (EditText) findViewById(R.id.txt_password)).getText().toString() ;
        String Re_password = ( (EditText) findViewById(R.id.txt_repassword)).getText().toString() ;
        String Answer1 =( (EditText) findViewById(R.id.txt_Answer1)).getText().toString() ;
        String Answer2 =( (EditText) findViewById(R.id.txt_Answer2)).getText().toString() ;

        if(email.isEmpty() || username.isEmpty() || Password.isEmpty() ||Re_password.isEmpty() || Answer1.isEmpty() || Answer2.isEmpty())
            Toast.makeText(getApplicationContext(),"Invalid Information",Toast.LENGTH_LONG).show();

        else if (Password.length() <=4)
            Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_LONG).show();

        else if(! Password.equals((Re_password)))
            Toast.makeText(getApplicationContext() ,"Password didn't match ." , Toast.LENGTH_LONG).show();

        else if (! Checkmail(email))
        {
            Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();

        }


        else {
            //    Toast.makeText(getApplicationContext(), "====== In Registration Step ===== ", Toast.LENGTH_LONG).show();



            Boolean ch = db.InsertIntoUsers(username, email, Password);
                     ch =  db.InsertForgetPassItem(email,Answer1,Answer2,birthdate);

            if (ch) {
                Toast.makeText(getApplicationContext(), "Succefully Registration ", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "failed in Registration ", Toast.LENGTH_LONG).show();
                finish();
            }


        }

    }



    public  Boolean Checkmail(String emailValue )
    {
       // String [] args = db.GetUserInfo(emailValue);
        if(! android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() )   //|| args !=null
            return false;

        for(int i = 0 ; i< Emails.size() ;i++)
            if(Emails.get(i).matches(emailValue))
                return  false;

      return  true ;
    }

    @Override
    protected void onResume() {
        TextView txt_Birthdate = (TextView)findViewById(R.id.txt_date);
        txt_Birthdate.setText(birthdate);

        super.onResume();
    }
}
