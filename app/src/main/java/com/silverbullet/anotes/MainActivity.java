package com.silverbullet.anotes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.silverbullet.anotes.adapter.NotesListAdapter;
import com.silverbullet.anotes.adapter.listener.NoteClickListener;
import com.silverbullet.anotes.core.model.Note;
import com.silverbullet.anotes.databinding.ActivityMainBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotesListAdapter adapter;
    private MainViewModel viewModel;
    private NoteClickListener noteClickListener;
    private ActivityResultLauncher<Intent> newNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        newNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Note note = data.getSerializableExtra("note", Note.class);
                        // TODO: Save this new note
                    }
                });

        binding.fabNewNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            newNoteLauncher.launch(intent);
        });

        noteClickListener = new NoteClickListener() {
            @Override
            public void onClick(int noteId) {
                // TODO: handle on note click
            }

            @Override
            public void onLongClick(int noteId) {
                // TODO: handle on note long click
            }
        };

        ArrayList<Note> notes = new ArrayList<>(); // TODO: find another way to set initial value.
        adapter = new NotesListAdapter(notes, noteClickListener);

        binding.recyclerNotes.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        binding.recyclerNotes.setAdapter(adapter);
    }
}