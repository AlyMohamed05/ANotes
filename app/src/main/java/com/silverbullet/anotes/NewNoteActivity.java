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
    boolean isOldNote = false;
    Note oldNote;

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

            int noteId = 0;
            if(oldNote != null){
                noteId = oldNote.getId();
            }

            Note note = new Note(noteId, title, body, formatter.format(date), false);

            Intent intent = new Intent();
            if(isOldNote){
                intent.putExtra("updated_note", note);
            }else{
                intent.putExtra("note", note);
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        oldNote = (Note) getIntent().getSerializableExtra("note");
        if(oldNote !=null){
            isOldNote = true;
            binding.editTitle.setText(oldNote.getTitle());
            binding.editBody.setText(oldNote.getBody());
        }
    }
}