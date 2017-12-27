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

public class EditItemInShopcart extends AppCompatActivity {

    DBhelper db ; Product Myproduct ;
    String Email , PID , TransID  , Buycnt ;
    TextView txt_cnt ;
    Bundle B ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_in_shopcart);

        fillparamter();

        FloatingActionButton fib_shocarted_minus = (FloatingActionButton) findViewById(R.id.fib_checkout_minus);
        FloatingActionButton fib_shocarted_Plus = (FloatingActionButton) findViewById(R.id.fib_checkout_plus);
        Button btn_Save = (Button) findViewById(R.id.btn_checkout_save);
        txt_cnt = (TextView)findViewById(R.id.txt_checkout_Buycnt);

        fib_shocarted_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = Integer.parseInt(txt_cnt.getText().toString());
                int uplimite  = Integer.parseInt(Myproduct.getCount());
                if(cnt<uplimite+Integer.parseInt(Buycnt))
                    cnt+=1 ;

                txt_cnt.setText(String.valueOf(cnt));

            }
        });

        fib_shocarted_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int cnt = Integer.parseInt(txt_cnt.getText().toString());
                if(cnt>0)
                    cnt-=1 ;

                txt_cnt.setText(String.valueOf(cnt));
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ncnt ( New Product Count )  = ProductCount + ( Oldcnt - Newcnt)

                    int NCnt = Integer.parseInt(Myproduct.getCount())+
                                  (Integer.parseInt(Buycnt)-Integer.parseInt(txt_cnt.getText().toString()) ) ;

                db.UpdateProudctitem(Myproduct, String.valueOf(NCnt));

                if(txt_cnt.getText().toString().equals("0"))
                        db.DeleteItem(TransID);

                else
                    db.UpdateItem(TransID, Email, PID, txt_cnt.getText().toString());


                Intent Nshopcart = new Intent(getApplicationContext(), ShopcartActivity.class);
                     Nshopcart.putExtra("email", Email);
                   startActivity(Nshopcart);
                      finish();




            }
        });


    }


    private void fillparamter()
    {

        B = getIntent().getExtras();

          PID  =  B.getString("PID");
        db = new DBhelper(this) ;
        this.Myproduct =  db.getProductinfo(PID) ;

        ((TextView)findViewById(R.id.txt_checkout_Pname)).setText("Product Name : "+Myproduct.getPname());
        ((TextView)findViewById(R.id.txt_checkout_cnt)).setText(" Avaliable : " +Myproduct.getCount());
        ((TextView)findViewById(R.id.txt_checkout_price)).setText("Price :"+Myproduct.getPrice()+"$");
        ((TextView)findViewById(R.id.txt_checkout_ProDes)).setText("Description \n" +Myproduct.getDescription());


        Bitmap bitmap = BitmapFactory.decodeByteArray(Myproduct.getImg(), 0, Myproduct.getImg().length);
        ((ImageView) findViewById(R.id.img_checkout_Pimg)).setImageBitmap(bitmap);

        if(Myproduct.getCount().equals("0"))
            ((TextView)findViewById(R.id.txt_checkout_cnt)).setText(" Avaliable : NA");

        TextView txt_cnt = (TextView) findViewById(R.id.txt_checkout_Buycnt) ;

        Buycnt = B.getString("Buycnt") ;
        txt_cnt.setText(Buycnt);

        Email = B.getString("email") ;
        TransID = B.getString("TransID") ;

    }
}
