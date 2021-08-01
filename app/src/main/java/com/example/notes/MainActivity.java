package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> noteContent = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);
        int listSize = sharedPreferences.getInt("listSize", 0);


        for (int i = 0; i < listSize; i++) {
            noteContent.set(i, sharedPreferences.getString("content" + String.valueOf(i), ""));
        }

        if (listSize == 0) {

            noteContent.add("Please take your note !");
            listSize++;
            sharedPreferences.edit().putInt("listSize", listSize);
            sharedPreferences.edit().commit();

        }

        listView = findViewById(R.id.listView);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteContent);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, WriteTheWords.class);
                intent.putExtra("noteNumber", position);
                intent.putExtra("content", noteContent.get(position));
                startActivity(intent);


            }
        });

    }
}