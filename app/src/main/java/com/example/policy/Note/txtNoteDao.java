package com.example.policy.Note;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface txtNoteDao {

    @Insert
    void insert(TextNote textNote);

    @Update
    void update(TextNote textNote);

    @Delete
    void delete(TextNote textNote);

    @Query("DELETE FROM TXT_NOTE_TABLE")
    void deleteAllNotes();

    @Query("SELECT * FROM TXT_NOTE_TABLE")
    LiveData<List<TextNote>> getAllNotes();
}
