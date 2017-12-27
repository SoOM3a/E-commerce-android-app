package com.example.abdel.ecommerceproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by abdel on 11/29/2017.
 */


public class DBhelper extends SQLiteOpenHelper {

public  static  final  String DBname =  "ECommerceDB" ;
    Context con ;
    public  static  final int version = 1 ;

    public DBhelper(Context context ) {
        super(context,  DBname , null, version);
        con = context ;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("Create table if not Exists Users (id INTEGER primary key , user_name TEXT  , password TEXT , emails TEXT  ) ; ");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Products(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Pname VARCHAR , Catid VARCHAR , Count VARCHAR , " +
                "Price VARCHAR , Description TEXT , Image BLOB ) ; ");
        sqLiteDatabase.execSQL("Create table if not Exists Shopcart (id INTEGER primary key , emails TEXT , PID INTEGER , UBuycnt VARCHAR ) ;");
        sqLiteDatabase.execSQL("Create table if not Exists Forgetpassword (id INTEGER primary key , emails TEXT , answer1 TEXT , answer2 TEXT ," +
                " birthdate TEXT ) ;");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop Table if EXISTS Users ;");
        sqLiteDatabase.execSQL("Drop Table if EXISTS Products ;");
        sqLiteDatabase.execSQL("Drop Table if  EXISTS  Shopcart ;");
        sqLiteDatabase.execSQL("Drop Table if  EXISTS  Forgetpassword ;");

        onCreate(sqLiteDatabase);
    }

    //  =================== User Methods ============================
    public Boolean Update(String name , String email ,String Password ,String id  )
    {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues Values = new ContentValues();
        Values.put("user_name" ,name);
        Values.put("password" ,Password);
        Values.put("emails",email);

        long res = -1 ;
            res = db.update("Users", Values, "  id = ? ", new String[]{id});
            db.close();

        if (res == -1)
            return false;

        return true ;
    }
    public Boolean InsertIntoUsers(String name , String email ,String Password )
    {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues Values = new ContentValues();
        Values.put("user_name" ,name);
        Values.put("password" ,Password);
        Values.put("emails",email);
     long res =    db.insert("Users",null,Values);
        if (res == -1)
            return false;

        return true ;
    }

    public String getPass(String EMail)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] arg = {EMail};
        Cursor res = db.rawQuery("select password from Users where emails like ? " ,arg);
        res.moveToFirst();
        db.close();
       return res.getString(res.getColumnIndex("password"));
    }


    public String [] GetUserInfo(String EMail)

    {

        SQLiteDatabase db = this.getReadableDatabase();
        String [] arg = {EMail};
        Cursor res = db.rawQuery("select user_name , password , id  from Users where emails like ? " ,arg);
        res.moveToFirst();
        db.close();
        if(res == null)
            return  null ;
        String [] QResult =
             {res.getString(res.getColumnIndex("user_name")) ,res.getString(res.getColumnIndex("password"))
                     ,res.getString(res.getColumnIndex("id"))};
        return QResult ;
    }

    public List<String> GetAllUser()

    {

        SQLiteDatabase db = this.getReadableDatabase();
        String [] arg = {};
       List<String> QResult =new LinkedList<String >();

        Cursor res = db.rawQuery("select emails from Users " ,arg);
        res.moveToFirst();
        db.close();
        if(res == null)
            return  null ;
        while(res.moveToNext()) {
             QResult.add(res.getString(res.getColumnIndex("emails")));
        }
        return QResult ;
    }

    // =================================Product==========================================
   public Boolean InsertProduct(String Pname , String Catid , String Count ,String Price , String DScrip ,Bitmap img )
   {
      // " Pname TEXT , Catid INTEGER , Count INTEGER , " +
       //        "Price real , Description TEXT , Image BLOB

       SQLiteDatabase db = this.getWritableDatabase() ;
       ContentValues Values = new ContentValues();
       Values.put("Pname" ,Pname);
       Values.put("Catid" ,Catid);
       Values.put("Count",Count);
       Values.put("Price",Price);
       Values.put("Description",DScrip);
       Values.put("Image",imageViewToByte(img));
       long res =    db.insert("Products",null,Values);
       if (res == -1)
           return false;

       return true ;
   }

    public static byte[] imageViewToByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Boolean UpdateProudctitem(Product Mypro , String NCount )
    {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues Values = new ContentValues();
        Values.put("Pname" ,Mypro.getPname());
        Values.put("Catid" ,Mypro.getCatid());
        Values.put("Count",NCount);
        Values.put("Price",Mypro.getPrice());
        Values.put("Description",Mypro.getDescription());
        Values.put("Image",Mypro.getImg());
        long res = -1 ;
        try {
            res = db.update("Products", Values, "  id = ? ", new String[]{Mypro.getId()});
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(con,e.toString(),Toast.LENGTH_LONG).show();
        }
        if (res == -1)
            return false;

        return true ;
    }

  public ArrayList<Product> RetriveByCatid(String CID)
  {
      ArrayList<Product> Mylist = new ArrayList<>();
      SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
      Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Products where Catid = ? " , new String[]{CID});

     cursor.moveToFirst();
      sqLiteDatabase.close();
      if(cursor == null )return  null ;
      while (! cursor.isAfterLast()) {
          /*
          Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Pname VARCHAR , Catid VARCHAR , Count VARCHAR , " +
                "Price VARCHAR , Description TEXT , Image BLOB
           */
          String id = cursor.getString(0);
          String Pname = cursor.getString(1);
          String Ctid = cursor.getString(2);
          String Count = cursor.getString(3) ;
          String Price = cursor.getString(4) ;
          String Description = cursor.getString(5);
          byte[] img = cursor.getBlob(6) ;
          Mylist.add(new Product(id,Pname,Ctid,Count,Price,Description,img));
          cursor.moveToNext() ;

      }
      return  Mylist;
  }


  public Product getProductinfo(String PID)
  {

      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery("SELECT * FROM Products where Id = ? " , new String[]{PID});
      cursor.moveToFirst();
      db.close();
      if(cursor == null)
          return  null ;

      String id = cursor.getString(0);
      String Pname = cursor.getString(1);
      String Categoryid = cursor.getString(2);
      String Count = cursor.getString(3) ;
      String Price = cursor.getString(4) ;
      String Description = cursor.getString(5);
      byte[] img = cursor.getBlob(6) ;
      return  new Product(id,Pname,Categoryid,Count,Price,Description,img);
  }


    public ArrayList<Product> GetAllProductByname(String Name)
    {
          ArrayList<Product> Mylist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products where Pname like ? " , new String[]{Name});
        cursor.moveToFirst();
        db.close();
        if(cursor == null)
            return  null ;

        while (! cursor.isAfterLast()) {

          //Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
             //   " Pname VARCHAR , Catid VARCHAR , Count VARCHAR , " +
           //     "Price VARCHAR , Description TEXT , Image BLOB

            String id = cursor.getString(0);
            String Pname = cursor.getString(1);
            String Ctid = cursor.getString(2);
            String Count = cursor.getString(3);
            String Price = cursor.getString(4);
            String Description = cursor.getString(5);
            byte[] img = cursor.getBlob(6);
            Mylist.add(new Product(id, Pname, Ctid, Count, Price, Description, img));
            cursor.moveToNext();
        }
        return  Mylist;
    }

  // ==================================== shoping Cart ========================
  public MyShopcart GetMyshopCart(String EMail)

  {
      ArrayList<Product> RetListProd  = new ArrayList<Product>();
      ArrayList<Integer> RetBucnt = new ArrayList<Integer>();
      ArrayList<String> TransIDList = new ArrayList<>();
      MyShopcart Mshop = new MyShopcart();


      SQLiteDatabase db = this.getReadableDatabase();
      String [] arg = {EMail};
      Cursor cursor = db.rawQuery("select * from Shopcart where emails = ? " ,arg);
      cursor.moveToFirst();
      if (cursor == null)
          return null;
      Product tempProduct   ;
      while (! cursor.isAfterLast()) {
          // (0) id INTEGER primary key , (1) emails TEXT , (2) PID INTEGER ,(3) UBuycnt VARCHAR
          String TransID = cursor.getString(0);
          String PID = cursor.getString(2);
         tempProduct = getProductinfo(PID);
          String UBuycnt = cursor.getString(3);

          TransIDList.add(TransID);
          RetListProd.add(tempProduct);
          RetBucnt.add(Integer.parseInt(UBuycnt));

          cursor.moveToNext();
      }

      Mshop.setEmail(EMail);
      Mshop.setMyProducts(RetListProd);
      Mshop.setMyProudctsCnt(RetBucnt);
      Mshop.setListTransId(TransIDList);
      return Mshop;
  }

    public Boolean InsertIntoShopcart(String Email , String PID , String Count)
    {
        // (0) id INTEGER primary key , (1) emails TEXT , (2) PID INTEGER ,(3) UBuycnt VARCHAR
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues Values = new ContentValues();
        Values.put("emails" ,Email);
        Values.put("PID" ,PID);
        Values.put("UBuycnt",Count);
        long res = db.insert("Shopcart",null,Values);
        if (res == -1)
            return false;

        return true ;
    }


    public Boolean DeleteItem(String TransID )
    {
        SQLiteDatabase db = this.getWritableDatabase() ;
        long res =  db.delete("Shopcart"," id = ? ",new String[]{TransID}) ;
        db.close();
        if(res == -1 )
            return  false;

        else
            return  true ;
    }

    public Boolean UpdateItem(String TransID ,String Email ,String PID,  String Ncnt  )
    {
        SQLiteDatabase db = this.getWritableDatabase() ;

        ContentValues Values = new ContentValues();
        Values.put("emails" ,Email);
        Values.put("PID" ,PID);
        Values.put("UBuycnt",Ncnt);

        long res = -1 ;

            res = db.update("Shopcart", Values, "  id = ? ", new String[]{TransID});
            db.close();
        if (res == -1)
            return false;

        return true ;
    }

    // =============================== ForgetPassword ====================

   public  Boolean InsertForgetPassItem(String email  , String Answer1 , String Answer2 , String birthdate ) {

        //emails TEXT , answer1 TEXT , answer2 TEXT
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put("emails", email);
        Values.put("answer1", Answer1);
        Values.put("answer2", Answer2);
       Values.put("birthdate", birthdate);

        long res = -1;
        res = db.insert("Forgetpassword", null, Values);
        db.close();
        if (res == -1)
            return false;

        return true;
    }

  public  String [] GetAnswer(String mail)
  {
      SQLiteDatabase db = this.getReadableDatabase();
      String [] arg = {mail};
      Cursor res = db.rawQuery("select answer1 , answer2 , birthdate from Forgetpassword where emails = ? " ,arg);
      res.moveToFirst();
      db.close();
      if(res == null)
          return  null ;
      String [] QResult =
              {res.getString(res.getColumnIndex("answer1")) ,res.getString(res.getColumnIndex("answer2")) ,res.getString(res.getColumnIndex("birthdate")) };
      return QResult ;
  }
}

