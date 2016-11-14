package com.example.ibrah.contacttelephone;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {


    ListView listRehber;
    Cursor numaralar;
    ArrayList<String> isimler = new ArrayList<String>();
    String[] projectıon = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolların Cağırılması
        listRehber = (ListView) findViewById(R.id.listNumber);


        //rehber baglatıusının numaralar degıskenıne atanması
        numaralar = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectıon, null, null, null);


        while (numaralar.moveToNext()) {
            //isimler degıskenıne rehber baglantısındakı kişi isimlerini cektik
            isimler.add(numaralar.getString(numaralar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

        }
        // Veriler adında bir Array ADapter olusturup içindekii verileri adaptere ekledk
        ArrayAdapter<String> veriler = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, isimler);

        listRehber.setAdapter(veriler);
        Collections.sort(isimler);

        listRehber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Kişi Ara");
                alert.setMessage("Aramayı gerçekleştirmek istiyormusunuz ? ");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //arama yapmayı kabul ederse
                        Toast.makeText(getApplicationContext(), "Arama yapılıyor ...", Toast.LENGTH_LONG).show();

                        Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("656565656565"));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);

                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Arama İptal ediliyor ...",Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();


            }
        });

    }

}
