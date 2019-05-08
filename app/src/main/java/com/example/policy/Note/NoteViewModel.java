package com.example.policy.Note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private  NoteRepository repository;
    private LiveData<List<TextNote>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(TextNote note){
        repository.insert(note);
    }
    public void update(TextNote note){
        repository.update(note);
    }
    public void delete(TextNote note){
        repository.delete(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<TextNote>> getAllNotes(){
        return allNotes;
    }
}
