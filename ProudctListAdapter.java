package com.example.abdel.ecommerceproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abdel on 12/7/2017.
 */

public class ProudctListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Product> ProductList;

    public ProudctListAdapter(Context context, int layout, ArrayList<Product> ProductList) {
        this.context = context;
        this.layout = layout;
        this.ProductList = ProductList;
    }

    @Override
    public int getCount() {
        return ProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return ProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView txtName,  txtPid;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txt_item_Name);
            holder.txtPid = (TextView) row.findViewById(R.id.txt_item_PID);
            holder.imageView = (ImageView) row.findViewById(R.id.img_item_Product);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Product P = ProductList.get(i);

        holder.txtName.setText(P.getPname());
       holder.txtPid.setText(P.getId());

        byte[] Productimg = P.getImg();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Productimg, 0, Productimg.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;

    }
}
