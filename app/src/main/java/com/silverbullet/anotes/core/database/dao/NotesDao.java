package com.silverbullet.anotes.core.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.silverbullet.anotes.core.database.entity.NoteEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(NoteEntity note);

    @Query("SELECT * FROM t_notes ORDER BY id DESC")
    Observable<List<NoteEntity>> getAll();

    @Query("UPDATE t_notes SET title = :title, body = :body WHERE id = :id")
    Completable update(int id, String title, String body);

    @Query("DELETE FROM t_notes WHERE id = :id")
    Completable deleteById(int id);
}
