package com.silverbullet.anotes.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverbullet.anotes.R;
import com.silverbullet.anotes.adapter.listener.NoteClickListener;
import com.silverbullet.anotes.core.model.Note;
import com.silverbullet.anotes.databinding.NoteItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    List<Note> notes;
    NoteClickListener listener;

    public NotesListAdapter(List<Note> notes, NoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteItemBinding binding = NoteItemBinding.inflate(inflater, parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note,listener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final NoteItemBinding binding;

    public NoteViewHolder(@NotNull NoteItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull Note note, NoteClickListener listener) {
        binding.noteItem.setCardBackgroundColor(Color.RED);

        binding.noteItem.setOnClickListener(v -> listener.onClick(note.getId()));

        binding.noteItem.setOnLongClickListener(v -> {
            listener.onLongClick(note.getId());
            return true;
        });

        binding.textNoteTitle.setText(note.getTitle());
        binding.textNoteTitle.setSelected(true);

        binding.textNoteBody.setText(note.getBody());

        binding.textDate.setText(note.getDate());
        binding.textDate.setSelected(true);

        if (note.isPinned()) {
            binding.imagePin.setImageResource(R.drawable.ic_pin);
        } else {
            binding.imagePin.setImageResource(0);
        }
    }
}