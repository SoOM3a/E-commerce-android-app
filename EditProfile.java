package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class EditProfile extends AppCompatActivity {

    private DBhelper DB ;
   private String  email , username , password , id ;
   private List<String> Emails ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        DB = new DBhelper(this);
        Emails = DB.GetAllUser() ;

      fillparamter();
        Button btn_save= (Button) findViewById(R.id.btn_svchange);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(check()) {
                   String  email = ( (EditText) findViewById(R.id.txt_ed_Email) ).getText().toString() ;
                   String username = ( (EditText) findViewById(R.id.txt_ed_username) ).getText().toString() ;
                   String N_password = ( (EditText) findViewById(R.id.txt_ed_NPassword)).getText().toString() ;

                   Boolean ch = DB.Update(username, email, N_password ,id);
                   if (ch) {
                       Toast.makeText(getApplicationContext(), "Update Succesfully .", Toast.LENGTH_SHORT).show();
                       finish();
                   }

                   else
                       Toast.makeText(getApplicationContext(), "Can't Update .", Toast.LENGTH_SHORT).show();

               }
            }
        });
    }
    private boolean check()
    {
        String  email = ( (EditText) findViewById(R.id.txt_ed_Email) ).getText().toString() ;
        String username = ( (EditText) findViewById(R.id.txt_ed_username) ).getText().toString() ;
        String Password = ( (EditText) findViewById(R.id.txt_ed_password)).getText().toString() ;
        String N_password = ( (EditText) findViewById(R.id.txt_ed_NPassword)).getText().toString() ;
        String Re_password = ( (EditText) findViewById(R.id.txt_ed_rePassword)).getText().toString() ;

        if(email.isEmpty() || username.isEmpty() || Password.isEmpty() ||Re_password.isEmpty()||N_password.isEmpty() )
            Toast.makeText(getApplicationContext(),"Invalid Information",Toast.LENGTH_LONG).show();

        else if (N_password.length() <=4)
            Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_LONG).show();


        else if (! this.password.matches(Password))
            Toast.makeText(getApplicationContext(),"Current Password is wrong",Toast.LENGTH_LONG).show();

        else if(! N_password.equals((Re_password)))
            Toast.makeText(getApplicationContext() ,"Password didn't match ." , Toast.LENGTH_LONG).show();

        else if (N_password.matches(Password))
            Toast.makeText(getApplicationContext() ,"New Password is same OldPassword  ." , Toast.LENGTH_LONG).show();

        else if (! Checkmail(email))
        {
            Toast.makeText(getApplicationContext(), "Email is not valid", Toast.LENGTH_SHORT).show();

        }
        else
            return  true;

return  false;
    }

    private  void fillparamter(){
        Bundle B = getIntent().getExtras() ;
        email = B.getString("email") ;
        String []arg = DB.GetUserInfo(email);
        username = arg[0] ;
        password =arg[1];
         id = arg[2];
        EditText Username = (EditText) findViewById(R.id.txt_ed_username);
        EditText Email = (EditText) findViewById(R.id.txt_ed_Email);
        TextView Id = (TextView) findViewById(R.id.txt_id);
        Username.setText(username);
        Email.setText(email);
        Id.setText(password);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.catagory , menu);
        menu.findItem(R.id.mncat_edP).setTitle(username);

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        Intent InsProduct = new Intent(this, InstanceProduct.class);

        if (id == R.id.mncat_electronics) {
            InsProduct.putExtra("Catid","1");
            startActivity(InsProduct);
        }

        else if (id == R.id.mncat_cars) {
            InsProduct.putExtra("Catid","2");
            startActivity(InsProduct);
        }

        else if (id == R.id.mncat_phones) {
            InsProduct.putExtra("Catid","3");
            startActivity(InsProduct);
        }

        else if (id == R.id.mncat_baby) {
            InsProduct.putExtra("Catid","4");
            startActivity(InsProduct);
        }


        else if (id == R.id.mncat_supermarket) {
            InsProduct.putExtra("Catid","5");
            startActivity(InsProduct);
        }
        else if (id == R.id.mncat_other) {
            InsProduct.putExtra("Catid","6");
            startActivity(InsProduct);
        }

        else if(id == R.id.mncat_edP)
        {
            Toast.makeText(this , "You ALready in Edit Profile " ,Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.mncat_Shopcart) {
            Toast.makeText(this, "choose Shop Cart ", Toast.LENGTH_LONG).show();

           Intent Shopc = new Intent(this, ShopcartActivity.class);
            Bundle b = getIntent().getExtras();
            Shopc.putExtra("email", b.getString("email"));
            startActivity(Shopc);

        }
        else if ( id == R.id.mncat_Search)
        {
            Intent Search = new Intent(getApplicationContext(),SearchActivity.class);
            Search.putExtra("email",email);
            startActivity(Search);
        }
        else if (id == R.id.mncat_exit) {
            MainActivity.editor.clear();
            MainActivity.editor.commit();
            Intent Login = new Intent(this, MainActivity.class);
            startActivity(Login);
            finish();
        } else
            return false;

        return  true;

    }


    public  Boolean Checkmail(String emailValue )
    {
        // String [] args = db.GetUserInfo(emailValue);
        if(! android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() )   //|| args !=null
            return false;

        for(int i = 0 ; i< Emails.size() ;i++)
            if(Emails.get(i).matches(emailValue) && !emailValue.matches(email) )
                return  false;

        return  true ;
    }


}
