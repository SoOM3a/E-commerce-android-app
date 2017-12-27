package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class Categoeylist extends AppCompatActivity {
    GridView Gv_cat ;
    String username = "default" ,Email ,password ;
    Intent Shopc ;
    Intent EditP ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_list);


        fillparamter();
        Gv_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent CatInstacne = new Intent(getApplicationContext(), CategoryInstance.class);
                    CatInstacne.putExtra("Catname", ((TextView) view).getText().toString());
                    CatInstacne.putExtra("email" , Email);
                    startActivity(CatInstacne);

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catagory,menu);
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

            else if (id == R.id.mncat_edP) {
                Bundle b = getIntent().getExtras();
                EditP.putExtra("email", Email);
                startActivity(EditP);

            }
            else if (id == R.id.mncat_Shopcart) {
                   Shopc = new Intent(this, ShopcartActivity.class);
                    Shopc.putExtra("email", Email);
                    startActivity(Shopc);

                }

                else if ( id == R.id.mncat_Search)
                  {
                Intent Search = new Intent(getApplicationContext(),SearchActivity.class);
                      Search.putExtra("email",Email);
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


private  void fillparamter()
{

    EditP = new Intent(this, EditProfile.class);


    String []arr = getResources().getStringArray(R.array.Categories) ;
    Gv_cat = (GridView) findViewById(R.id.gv_cat) ;
    ArrayAdapter<String> gs_adp =
            new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);

    Gv_cat.setAdapter(gs_adp);
    Bundle arg = getIntent().getExtras();
    if(arg != null)
    {
        Email = arg.getString("email");
        DBhelper db = new DBhelper(getApplicationContext());
        String []args =  db.GetUserInfo(Email);
        username = args[0] ;
        password = args[1];

    }
}


}
