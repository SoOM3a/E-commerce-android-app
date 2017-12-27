package com.example.abdel.ecommerceproject;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopcartActivity extends AppCompatActivity {

    String UEmail ;
    GridView MygridV ;
    MyShopcart Myshop ;
    ArrayList<Product> Mylist ;
    ProudctListAdapter adp = null;
    DBhelper DB ;
    Bundle B  ;
    private LocationManager Lm ;
    private LocationListener Ls ;
String Address  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcartactivity);

        Toast.makeText(getApplicationContext() , "Long Press On item to remove ." , Toast.LENGTH_SHORT).show();
        fillparamter();

    Lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Ls=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Address = "Latiude" +location.getLatitude() + "Longitude:" +location.getLongitude() ;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        MygridV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Boolean ch = DB.DeleteItem(Myshop.getListTransId().get(i));
                Product Myprod = Myshop.getMyProducts().get(i) ;
                DB.UpdateProudctitem(Myprod, String.valueOf(Integer.parseInt(Myprod.getCount()) + Myshop.getMyProudctsCnt().get(i)));
                Intent  Nshopcart = new Intent(getApplicationContext(), ShopcartActivity.class);
                Nshopcart.putExtra("email", UEmail);
                startActivity(Nshopcart);
                finish();

                return ch;
            }
        });

        MygridV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent EDitItem = new Intent(getApplicationContext(), EditItemInShopcart.class);
                    EDitItem.putExtra("email", UEmail);
                    EDitItem.putExtra("PID", String.valueOf(Myshop.getMyProducts().get(i).getId()));
                    EDitItem.putExtra("Buycnt", String.valueOf(Myshop.getMyProudctsCnt().get(i)));
                    EDitItem.putExtra("TransID", String.valueOf(Myshop.getListTransId().get(i)));
                    startActivity(EDitItem);
                   finish(); // // TODO: 12/8/2017  comment

            }
        });


    }


    private  void fillparamter()
    {

            B =  getIntent().getExtras();
            UEmail = B.getString("email");

            DB = new DBhelper(this);

            Myshop = DB.GetMyshopCart(UEmail);
            Mylist = new ArrayList<>();
            Mylist.clear();
            Mylist = Myshop.getMyProducts();
            MygridV = (GridView) findViewById(R.id.Gv_Myshopcart);
            adp = new ProudctListAdapter(this, R.layout.productitem, Mylist);
            MygridV.setAdapter(adp);

            TextView total = (TextView) findViewById(R.id.txt_mshop_total) ;
            total.setText("Total : "+String.valueOf(Myshop.getTotalPrice())+"$");


    }
}
