package com.silverbullet.anotes.core.repository;

import androidx.annotation.NonNull;

import com.silverbullet.anotes.core.database.dao.NotesDao;
import com.silverbullet.anotes.core.database.entity.NoteEntity;
import com.silverbullet.anotes.core.mapper.NoteMapper;
import com.silverbullet.anotes.core.model.Note;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotesRepositoryImpl implements NotesRepository {

    private final NotesDao dao;

    @Inject
    NotesRepositoryImpl(NotesDao dao) {
        this.dao = dao;
    }

    @Override
    public Observable<List<Note>> observeNotes() {
        return dao
                .getAll()
                .map(noteEntities -> {
                    List<Note> mapped = new ArrayList<>();
                    for (NoteEntity note : noteEntities) {
                        mapped.add(NoteMapper.fromEntity(note));
                    }
                    return mapped;
                });
    }

    @Override
    public Completable create(@NonNull Note note) {
        final NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(note.getTitle());
        noteEntity.setBody(note.getBody());
        noteEntity.setDate(note.getDate());
        noteEntity.setPinned(false);
        return dao
                .insert(noteEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable updateNote(Note note) {
        return dao
                .update(note.getId(), note.getTitle(), note.getBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
