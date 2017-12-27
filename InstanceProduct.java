package com.example.abdel.ecommerceproject;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class InstanceProduct extends AppCompatActivity
{

    final  int REQUEST_CODE_GALLERY = 999 ;
    DBhelper Db ;
     String Catid ;
    ImageView imv ; Bitmap bm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_product);
        Db = new DBhelper(this);
        imv = (ImageView) findViewById(R.id.imageView);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"onclick",Toast.LENGTH_SHORT).show();
               ActivityCompat.requestPermissions(InstanceProduct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY
                );
            }
        });

        Button btn_add = (Button) findViewById(R.id.btn_pro_addProduct) ;
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Bundle B = getIntent().getExtras();
                    Catid  =  B.getString("Catid") ;
                    String txt_Pname = ((EditText) findViewById(R.id.txt_pro_name)).getText().toString();
                    String txt_cnt = ((EditText) findViewById(R.id.txt_pro_Count)).getText().toString();
                    String txt_Descrip = ((EditText) findViewById(R.id.txt_pro_description)).getText().toString();
                    String txt_Price = ((EditText) findViewById(R.id.txt_pro_price)).getText().toString();

                Boolean ch = false ;
                if(Validate(txt_Pname ,txt_cnt ,txt_Descrip ,txt_Price , bm ))
                 ch = Db.InsertProduct(txt_Pname, Catid, txt_cnt, txt_Price, txt_Descrip, bm);

                else
                    Toast.makeText(getApplicationContext(),"Not Valid Information",Toast.LENGTH_SHORT).show();
                if(ch)
                    Toast.makeText(getApplicationContext(), "Succeful added", Toast.LENGTH_LONG).show();


                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri path = null ;
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
             path = data.getData();
        }

        try {
            InputStream inputStream = getContentResolver().openInputStream(path);

             bm = BitmapFactory.decodeStream(inputStream);
            imv.setImageBitmap(bm);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

Boolean Validate(String arg1 , String arg2 , String arg3 ,String arg4 ,Bitmap arg5)
{

    if(arg1.isEmpty() || arg2.isEmpty() ||arg3.isEmpty() ||arg4.isEmpty() || arg5 == null )
        return  false;

    return true;
}

}


