package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);

        int listSize = sharedPreferences.getInt("listSize", 0);

        for (int j = 0; j < listSize; j++) {
            noteContent.add("");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.addNewNote:

                SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);
                int listSize = sharedPreferences.getInt("listSize", 0);

                noteContent.add("");
                listSize++;
                sharedPreferences.edit().putInt("listSize", listSize).commit();

                Intent intent = new Intent(MainActivity.this, AddNewList.class);

                startActivity(intent);
                return true;

            default:
                return false;

        }
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
            sharedPreferences.edit().putInt("listSize", listSize).commit();

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete it?")
                        .setMessage("Really want to delete this note?")
                        .setPositiveButton("Delete it", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                noteContent.remove(position);
                                int listSize = sharedPreferences.getInt("listSize", 0);


                                for (int i = position; i < listSize; i++) {
                                    sharedPreferences.edit().putString("content" + String.valueOf(i), sharedPreferences.getString("content" + String.valueOf(i + 1), "")).commit();
                                }

                                listSize--;

                                sharedPreferences.edit().putInt("listSize", listSize).commit();

                                for (int k = 0; k < listSize; k++) {
                                    noteContent.set( k, sharedPreferences.getString("content" + String.valueOf(k), ""));
                                }
                                arrayAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


                return true;
            }
        });


    }
}