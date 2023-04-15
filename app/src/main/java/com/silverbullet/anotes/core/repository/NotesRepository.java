package com.silverbullet.anotes.core.repository;

import com.silverbullet.anotes.core.model.Note;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface NotesRepository {

    Observable<List<Note>> observeNotes();

    Completable create(Note note);

    Completable updateNote(Note note);
}
