package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class WriteTheWords extends AppCompatActivity {

    EditText editText;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_the_words);

        sharedPreferences = getSharedPreferences("com.example.notes", MODE_PRIVATE);

        editText = findViewById(R.id.editText);

        Intent intent = getIntent();

        int noteNumber = intent.getIntExtra("noteNumber", 0);

        String noteContent = intent.getStringExtra("content");

        editText.setText(noteContent);


    }

    @Override
    protected void onStop() {
        super.onStop();

        Intent intent = getIntent();

        int noteNumber = intent.getIntExtra("noteNumber", 0);

        String editContent = editText.getText().toString();

        sharedPreferences.edit().putString("content" + String.valueOf(noteNumber), editContent);

        sharedPreferences.edit().commit();

    }
}