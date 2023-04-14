package com.silverbullet.anotes.core.mapper;

import androidx.annotation.NonNull;

import com.silverbullet.anotes.core.database.entity.NoteEntity;
import com.silverbullet.anotes.core.model.Note;

public class NoteMapper {

    public static Note fromEntity(@NonNull NoteEntity entity){
        return new Note(
          entity.getId(),
          entity.getTitle(),
          entity.getBody(),
          entity.getDate(),
          entity.isPinned()
        );
    }
}
