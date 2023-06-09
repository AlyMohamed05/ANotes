package com.silverbullet.anotes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
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
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NotesListAdapter adapter;
    private MainViewModel viewModel;
    private ActivityResultLauncher<Intent> newNoteLauncher;
    private ActivityResultLauncher<Intent> editNoteLauncher;
    private final List<Note> mNotes = new ArrayList<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private Observer<List<Note>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.silverbullet.anotes.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        newNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Note note = (Note) data.getSerializableExtra("note");
                        viewModel.saveNote(note);
                    }
                });

        editNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() != RESULT_OK){
                        return;
                    }
                    Intent data = result.getData();
                    if(data == null){
                        return;
                    }
                    Note newNote = (Note) data.getSerializableExtra("updated_note");
                    viewModel.updateNote(newNote);
                }
        );

        binding.fabNewNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            newNoteLauncher.launch(intent);
        });

        binding.searchNotes.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        viewModel.setSearchQuery(newText);
                        return true;
                    }
                }
        );

        NoteClickListener noteClickListener = new NoteClickListener() {
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
                intent.putExtra("note",note);
                editNoteLauncher.launch(intent);
            }

            @Override
            public void onLongClick(Note note) {
                // TODO: handle on note long click
            }
        };

        observer = notes -> {
            mNotes.clear();
            mNotes.addAll(notes);
            adapter.notifyDataSetChanged();
        };

        adapter = new NotesListAdapter(mNotes, noteClickListener);

        binding.recyclerNotes.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        binding.recyclerNotes.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.notesLiveData().observe(this,observer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.dispose();
        viewModel.notesLiveData().removeObserver(observer);
    }
}