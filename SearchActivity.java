package com.example.abdel.ecommerceproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private  static  final int SPEECH_REQ = 998 ;
    DBhelper db ; String Pname  , Email;
    ArrayList<Product> SearchedPrduct ;
    ProudctListAdapter adp ;
     GridView Mygrid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton Btn_Searchpnametxt  = (ImageButton)findViewById(R.id.btn_search_byPname) ;
        ImageButton Btn_Searchrecord  = (ImageButton)findViewById(R.id.btn_search_bysound) ;
        Mygrid = (GridView)findViewById(R.id.Gv_search_gv);
        final EditText txt_Pname = (EditText)findViewById(R.id.txt_serach_Pname);
         db = new DBhelper(this);
        Btn_Searchpnametxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! (txt_Pname.getText().toString().isEmpty()) )
                {
                    Pname = txt_Pname.getText().toString() ;
                    FillandSearch(Pname);
                }
                    else
                Toast.makeText(getApplicationContext() ,"Please Enter Product name" ,Toast.LENGTH_LONG).show();

            }

        });
        Btn_Searchrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);


                try {
                    startActivityForResult(intent, SPEECH_REQ);
                }
                catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            a.toString(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        Mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle B  = getIntent().getExtras();
                 Email =  B.getString("email");
                String PID = ((TextView) view.findViewById(R.id.txt_item_PID)).getText().toString();
                Intent ViewP = new Intent(getApplicationContext() , ViewProductinfo.class);
                ViewP.putExtra("PID", PID);
                ViewP.putExtra("email",Email);
                startActivity(ViewP);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQ && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Pname = results.get(0);
            if(! Pname.isEmpty())
            FillandSearch(Pname);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void FillandSearch ( String Pname)
    {
        SearchedPrduct = db.GetAllProductByname(Pname) ;
        adp = new ProudctListAdapter(getApplicationContext(),R.layout.productitem,SearchedPrduct);
        Mygrid.setAdapter(adp);
    }
}
