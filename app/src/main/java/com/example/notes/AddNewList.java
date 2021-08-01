package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class AddNewList extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);

        editText = findViewById(R.id.editText);



    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);

        int listSize = sharedPreferences.getInt("listSize",0);

        String editContent = editText.getText().toString();

        sharedPreferences.edit().putString("content" + listSize, editContent).commit();

    }
}