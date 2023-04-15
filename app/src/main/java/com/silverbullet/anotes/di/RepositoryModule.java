package com.silverbullet.anotes.di;

import com.silverbullet.anotes.core.repository.NotesRepository;
import com.silverbullet.anotes.core.repository.NotesRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    public abstract NotesRepository bindNotesRepository(NotesRepositoryImpl repo);
}
