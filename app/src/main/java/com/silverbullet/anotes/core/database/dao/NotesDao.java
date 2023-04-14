package com.silverbullet.anotes.core.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.silverbullet.anotes.core.database.entity.NoteEntity;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Query("SELECT * FROM t_notes ORDER BY id DESC")
    List<NoteEntity> getAll();

    @Query("UPDATE t_notes SET title = :title, body = :body WHERE id = :id")
    void update(int id, String title, String body);

    @Query("DELETE FROM t_notes WHERE id = :id")
    void deleteById(int id);
}
