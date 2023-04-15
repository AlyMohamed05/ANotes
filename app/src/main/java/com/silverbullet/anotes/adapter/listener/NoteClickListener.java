package com.silverbullet.anotes.adapter.listener;

import com.silverbullet.anotes.core.model.Note;

public interface NoteClickListener{

    void onClick(Note note);
    void onLongClick(Note note);
}
