package com.example.garsonason;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class urunModelTemp extends ArrayAdapter<String> {


    public ArrayList<urunModel> model;
    public Activity context;

    public urunModelTemp(Activity context, ArrayList<urunModel> sepet) {
        super(context, R.layout.custom_listview_1, sepet.get(0).miktar);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.model = sepet;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_listview_1, null, true);
        TextView textview_UrunAdi = (TextView) rowView.findViewById(R.id.textview_UrunAdi);
        TextView textview_UrunMiktari = (TextView) rowView.findViewById(R.id.textview_UrunMiktari);
        TextView textview_UrunTopFiyati = (TextView) rowView.findViewById(R.id.textview_UrunTopFiyati);
        textview_UrunAdi.setText(this.model.get(position).urunAdi);
        textview_UrunMiktari.setText(model.get(position).miktar);
        textview_UrunTopFiyati.setText((model.get(position).miktar * model.get(position).fiyat));
        return rowView;

    }

}
