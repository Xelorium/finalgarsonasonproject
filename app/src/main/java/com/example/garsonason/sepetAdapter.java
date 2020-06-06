package com.example.garsonason;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class sepetAdapter extends ArrayAdapter<urunModel> {

    private static final String TAG = "sepetAdapter";

    private Context mContext;
    int mResource;


    public sepetAdapter(Context context, int resource, ArrayList<urunModel> objects) {
        super( context, resource, objects);
        mContext = context;
        mResource = resource;

        /**
         * Default constructor for the PersonListAdapter
         * @param context
         * @param resource
         * @param objects
         */


    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {

        String urunAd = getItem(position).getUrunAdi();
        int urunMiktar = getItem(position).getMiktar();
        int urunFiyat = getItem(position).getFiyat();
        String urunDurum = getItem(position).getDurum();
        String siparisId = getItem(position).getSiparisId();
        urunModel urunmodel = new urunModel(urunAd,urunMiktar,urunFiyat,urunDurum,siparisId);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView a = (TextView) convertView.findViewById(R.id.textview_UrunAdi);
        TextView b = (TextView) convertView.findViewById(R.id.textview_UrunMiktari);
        TextView c = (TextView) convertView.findViewById(R.id.textview_UrunTopFiyati);

        a.setText("Ürün Adı: "+urunAd);
        b.setText(" x"+String.valueOf(urunMiktar));
        c.setText(String.valueOf(urunFiyat*urunMiktar)+" TL");

        return convertView;


    }
}
