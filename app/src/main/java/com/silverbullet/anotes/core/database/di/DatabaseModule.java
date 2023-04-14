package com.silverbullet.anotes.core.database.di;

import android.app.Application;

import androidx.room.Room;

import com.silverbullet.anotes.core.database.ANotesDatabase;
import com.silverbullet.anotes.core.database.dao.NotesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static ANotesDatabase providesANotesDatabase(Application app){
        return Room
                .databaseBuilder(
                        app,
                        ANotesDatabase.class,
                        ANotesDatabase.DATABASE_NAME
                )
                .build();
    }

    @Provides
    @Singleton
    public static NotesDao providesNotesDao(ANotesDatabase db){
        return db.getNotesDao();
    }
}
