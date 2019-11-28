package com.dicoding.picodiploma.datasiswa;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SiswaList extends ArrayAdapter<DataSiswaReq> {
    private Activity context;
    private List<DataSiswaReq> siswaList;

    public SiswaList (Activity context, List<DataSiswaReq> siswaList){
        super(context, R.layout.list_layout,siswaList);
        this.context = context;
        this.siswaList = siswaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvName);
            TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.tvEmail);
            TextView textViewNoHP = (TextView) listViewItem.findViewById(R.id.tvNoHP);

            DataSiswaReq dataSiswaReq = siswaList.get(position);

            textViewName.setText(dataSiswaReq.getSiswaNama());
            textViewEmail.setText(dataSiswaReq.getSiswaEmail());
            textViewNoHP.setText(dataSiswaReq.getSiswaNoHP());

        return listViewItem;
    }
}
