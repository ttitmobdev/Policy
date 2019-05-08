package com.example.policy.Note;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private txtNoteDao noteDao;
    private LiveData<List<TextNote>> allNote;

    public NoteRepository(Application application){
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNote = noteDao.getAllNotes();
    }

    public void insert(TextNote note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(TextNote note){
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }

    public void delete(TextNote note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<TextNote>> getAllNotes(){
        return allNote;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<TextNote,Void,Void>{

        private txtNoteDao noteDao;

        public InsertNoteAsyncTask(txtNoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(TextNote... textNotes) {
            noteDao.insert(textNotes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<TextNote,Void,Void>{

        private txtNoteDao noteDao;

        public UpdateNoteAsyncTask(txtNoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(TextNote... textNotes) {
            noteDao.update(textNotes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<TextNote,Void,Void>{

        private txtNoteDao noteDao;

        public DeleteNoteAsyncTask(txtNoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(TextNote... textNotes) {
            noteDao.delete(textNotes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private txtNoteDao noteDao;

        public DeleteAllNotesAsyncTask(txtNoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
