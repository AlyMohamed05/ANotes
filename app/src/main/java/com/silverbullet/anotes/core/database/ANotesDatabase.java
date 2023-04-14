package com.silverbullet.anotes.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.silverbullet.anotes.core.database.dao.NotesDao;
import com.silverbullet.anotes.core.database.entity.NoteEntity;

@Database(
        entities = NoteEntity.class,
        version = 1,
        exportSchema = false
)
public abstract class ANotesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "a_notes.db";

    public abstract NotesDao getNotesDao();
}
