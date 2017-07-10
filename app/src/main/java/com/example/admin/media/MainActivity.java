package com.example.admin.media;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mainList;




    public static String[] listContent = { "Artist: Lebo Sekgobela  \n Title: lion of judah \n   ", "Artist: SINACH \n \n  Title:WAY MAKER)", "Artist: SINACH  \n \n Title: I KNOW WHO I AM", "Artist: Hezekiah Walker \n \n Title: Every Praise\"",

            "Artist: Tye Tribbett   \n \n Title: What Can I Do", " Artist: Tye Tribbett  \n \n Title: I Love You Forever ", "Artist: Christian. K \n \n  Title: I Win ", "Artist: CSO \n \n  Title: I'm Excellent",
    "Artist: Okmalumkoolkat \n \n Title: Gqi","Artist: SANDS \n \n Title: TIGI "};

    public static  int POSITION = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);




        mainList = (ListView) findViewById(R.id.lvList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_list_item_1, listContent);

        mainList.setAdapter(adapter);


        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override


            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {



                POSITION =position;
                Intent i = new Intent(MainActivity.this,PlaySingle.class);
                i.putExtra("POSITION",POSITION);
                startActivity(i);




            }

        });




    }




}
