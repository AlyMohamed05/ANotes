package com.silverbullet.anotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.silverbullet.anotes.core.model.Note;
import com.silverbullet.anotes.databinding.ActivityNewNoteBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private ActivityNewNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageSave.setOnClickListener(v -> {
            String title = binding.editTitle.getText().toString();
            String body = binding.editBody.getText().toString();

            if (body.isEmpty()) {
                Toast.makeText(this, "Note body can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MM yyyy HH:mm");
            Date date = new Date();

            Note note = new Note(0, title, body, formatter.format(date), false);

            Intent intent = new Intent();
            intent.putExtra("note", note);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}