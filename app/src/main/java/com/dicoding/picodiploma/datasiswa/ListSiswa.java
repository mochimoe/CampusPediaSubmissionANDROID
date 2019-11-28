package com.dicoding.picodiploma.datasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ListSiswa extends AppCompatActivity {

    DatabaseReference databaseSiswa;

    ListView listViewSiswa;
    List<DataSiswaReq> listSiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_siswa);

        databaseSiswa = FirebaseDatabase.getInstance().getReference("siswa");

        listViewSiswa = (ListView) findViewById(R.id.listViewSiswa);
        listSiswa = new ArrayList<>();

        listViewSiswa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataSiswaReq dataSiswaReq = listSiswa.get(i);
                showUpdate(dataSiswaReq.getSiswaId(),dataSiswaReq.getSiswaNama());
                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSiswa.clear();
                for(DataSnapshot siswaSnapshot: dataSnapshot.getChildren()){
                    DataSiswaReq dataSiswaReq = siswaSnapshot.getValue(DataSiswaReq.class);

                    listSiswa.add(dataSiswaReq);
                }
                SiswaList adapter = new SiswaList(ListSiswa.this, listSiswa);
                listViewSiswa.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdate (final String siswaId, String siswaNama){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        //   final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewUpdate);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.up_Nama);
        final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.up_Email);
        final EditText editTextNoHP = (EditText) dialogView.findViewById(R.id.up_NomorHP);

        final Button buttonUp = (Button) dialogView.findViewById(R.id.btn_update);
        final Button buttonDel = (Button) dialogView.findViewById(R.id.btn_delete);

        dialogBuilder.setTitle("Mengupdate Siswa " +siswaNama);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String noHP = editTextNoHP.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Nama tidak boleh kosong");
                } else if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email tidak boleh kosong");
                } else if (TextUtils.isEmpty(noHP)){
                    editTextNoHP.setError("Nomor HP tidak boleh kosong");
                } else{
                    updateSiswa(siswaId,name,email,noHP);

                    alertDialog.dismiss();
                }


            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSiswa(siswaId);
            }
        });


    }

    private void deleteSiswa(String siswaId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("siswa").child(siswaId);

        databaseReference.removeValue();
        Toast.makeText(this,"Data terhapus",Toast.LENGTH_LONG).show();
    }

    private boolean updateSiswa(String id, String nama, String email, String noHP){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("siswa").child(id);

        DataSiswaReq dataSiswaReq = new DataSiswaReq(id,nama,email,noHP);
        databaseReference.setValue(dataSiswaReq);

        Toast.makeText(this,"Siswa Terupdate",Toast.LENGTH_LONG).show();

        return true;
    }
}
