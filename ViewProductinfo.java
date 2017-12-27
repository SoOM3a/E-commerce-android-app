package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProductinfo extends AppCompatActivity {

    private  Product  Myproduct;
    private  DBhelper Db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_productinfo);
        fillparamter();

        FloatingActionButton Plus  = (FloatingActionButton) findViewById(R.id.fib_ProductV__plus) ;
        FloatingActionButton Minus  = (FloatingActionButton) findViewById(R.id.fib_ProductV__minus) ;
        Button  btn_add_toshopchart = (Button) findViewById(R.id.btn_ProductV__Addshcart);

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt_cnt = (TextView)findViewById(R.id.txt_ProductV__cnt);
                int cnt = Integer.parseInt(txt_cnt.getText().toString());
                  int uplimite  = Integer.parseInt(Myproduct.getCount());
                if(cnt<uplimite)
                     cnt+=1 ;

                txt_cnt.setText(String.valueOf(cnt));


            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt_cnt = (TextView)findViewById(R.id.txt_ProductV__cnt);
                int cnt = Integer.parseInt(txt_cnt.getText().toString());

                 if(cnt>0)
                    cnt -= 1;

                txt_cnt.setText(String.valueOf(cnt));
            }
        });

        btn_add_toshopchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   Bundle B = getIntent().getExtras();
                    String Email = B.getString("email");
                    TextView cnt = (TextView) findViewById(R.id.txt_ProductV__cnt);
                    Db = new DBhelper(getApplicationContext());
                    boolean ch = Db.InsertIntoShopcart(Email, Myproduct.getId(), cnt.getText().toString());
                    int NCt = (Integer.parseInt(Myproduct.getCount()) - Integer.parseInt(cnt.getText().toString())) ;
                    Db.UpdateProudctitem(Myproduct ,String.valueOf(NCt));
                    if (ch) {
                        Toast.makeText(getApplicationContext(), "Add in ShopCart", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });





    }

    void fillparamter()
    {
        String PID  =  ( getIntent().getExtras() ).getString("PID");
        Db = new DBhelper(this) ;
        this.Myproduct =  Db.getProductinfo(PID) ;

        ((TextView)findViewById(R.id.txt_ProductV_Pname)).setText("Product Name : "+Myproduct.getPname());
        ((TextView)findViewById(R.id.txt_productV_Peices)).setText(" Avaliable : " +Myproduct.getCount());
        ((TextView)findViewById(R.id.txt_productV_Prcie)).setText("Price :"+Myproduct.getPrice()+"$");
        ((TextView)findViewById(R.id.txt_ProductV_Description)).setText("Description \n" +Myproduct.getDescription());


        Bitmap bitmap = BitmapFactory.decodeByteArray(Myproduct.getImg(), 0, Myproduct.getImg().length);
        ((ImageView) findViewById(R.id.img_ProductV_productimg)).setImageBitmap(bitmap);

        if(Myproduct.getCount().equals("0"))
            ((TextView)findViewById(R.id.txt_productV_Peices)).setText(" Avaliable : NA");


    }
}
