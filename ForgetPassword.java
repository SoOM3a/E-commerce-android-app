package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ForgetPassword extends AppCompatActivity {

    private  DBhelper db ;
     public  static  String Birthdate ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button btn_getP = (Button) findViewById(R.id.btn_sndmail);
        Button btn_setBirthdate = (Button)findViewById(R.id.btn_fr_setBirthdate);
        db = new DBhelper(this);

        btn_getP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String Email = ((TextView) findViewById(R.id.txt_fr_mail)).getText().toString();
                    String Answer1 = ((TextView) findViewById(R.id.txt_fr_quAnswer)).getText().toString();
                    String Answer2 = ((TextView) findViewById(R.id.txt_fr_quAnswer)).getText().toString();
                    TextView ShowPass = (TextView) findViewById(R.id.txt_fr_showpassword);
                    if (Validate(Email, Answer1, Answer2)) {
                        String[] args = db.GetAnswer(Email);
                        if (args != null) {
                            if (Answer1.equals(args[0]) && Answer2.equals(args[1])) {
                                String[] Userinfo = db.GetUserInfo(Email);
                                ShowPass.setText("Your Password is : " + Userinfo[1]);

                            } else {
                                ShowPass.setText("Your Answers is Wrong ");

                            }
                        }
                    }


            }
        });

        btn_setBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setBdate = new Intent(getApplicationContext(),Calendar.class) ;
               startActivity(setBdate);
            }
        });
    }
    public Boolean Checkmail(String emailValue) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() ;
    }

    private  Boolean Validate(String Email , String Ans1 , String Ans2)
    {
        if(Email.isEmpty() || Ans1.isEmpty() || Ans2.isEmpty() || !Checkmail(Email))
         return  false ;

        return true ;
    }

    @Override
    protected void onResume() {
        TextView txt_setB = (TextView)findViewById(R.id.txt_fr_birthdate);
        txt_setB.setText(Birthdate);
        super.onResume();
    }
}
