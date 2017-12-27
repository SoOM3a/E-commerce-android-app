package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryInstance extends AppCompatActivity {

     String Catname ; int Catid ;
    GridView MygridV ;
    ArrayList<Product> Mylist ;
    ProudctListAdapter adp = null;
    DBhelper DB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_instance);


        Fillparamter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent createP = new Intent(this,InstanceProduct.class);
       final  Intent ViewP = new Intent(this,ViewProductinfo.class);
       //final Intent CatInstance = new Intent(this, CategoryInstance.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createP.putExtra("Catid", String.valueOf(Catid));
                startActivity(createP) ;
                finish();

            }
        });

            DB = new DBhelper(this);
            MygridV = (GridView) findViewById(R.id.Gv_Cat);
            Mylist = new ArrayList<>(DB.RetriveByCatid(String.valueOf(Catid)));
            adp = new ProudctListAdapter(this, R.layout.productitem, Mylist);

            MygridV.setAdapter(adp);


        MygridV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle B  = getIntent().getExtras();
                    String Email =  B.getString("email");
                    String PID = ((TextView) view.findViewById(R.id.txt_item_PID)).getText().toString();
                    ViewP.putExtra("PID", PID);
                    ViewP.putExtra("email",Email);
                    startActivity(ViewP);
                    finish();

            }
        });


    }

    void Fillparamter()
    {
        Bundle B =  getIntent().getExtras();
        Catname = B.getString("Catname");
        if(Catname.equals("Electronics") )
        Catid =1 ;
        else if (Catname.equals("Cars"))
            Catid =2 ;
        else if (Catname.equals("Phones"))
            Catid=3 ;
        else if (Catname.equals("Baby And toys"))
            Catid =4;
        else if (Catname.equals("SuperMarket"))
            Catid = 5;
        else
            Catid =6 ;
        TextView catname = (TextView) findViewById(R.id.txt_catname);
        catname.setText(Catname);

    }

}
