package com.example.policy.Note;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "txt_note_table")
public class TextNote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String note;

    public TextNote( String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
