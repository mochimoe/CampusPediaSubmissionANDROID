package com.dicoding.picodiploma.datasiswa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextNama,editTextEmail,editTextHP;
    Button buttonSave;
    Button buttonMove;

    DatabaseReference databaseSiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseSiswa = FirebaseDatabase.getInstance().getReference("siswa");

        editTextNama =(EditText) findViewById(R.id.et_Nama);
        editTextEmail = (EditText) findViewById(R.id.et_Email);
        editTextHP = (EditText) findViewById(R.id.et_NomorHP);

        buttonSave = (Button) findViewById(R.id.btn_save);
        buttonMove = (Button) findViewById(R.id.btn_move);
        buttonMove.setOnClickListener(this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_move:
                Intent moveIntent = new Intent(MainActivity.this, ListSiswa.class);
                startActivity(moveIntent);
                break;
        }
    }



    private void submitData(){
        String name = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String noHP = editTextHP.getText().toString().trim();

        if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(noHP)){
            //membuat id unik dari setiap siswa yang dibuat
            String id = databaseSiswa.push().getKey();
            //membuat data siswa
            DataSiswaReq siswaReq = new DataSiswaReq(id, name, email, noHP);
            //store ke firebase
            databaseSiswa.child(id).setValue(siswaReq);
            Toast.makeText(this, "Siswa ditambahkan", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Anda harus memasukkan data",Toast.LENGTH_LONG).show();
        }
    }
}
