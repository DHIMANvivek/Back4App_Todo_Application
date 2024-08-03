package com.example.back4apptodoapplication;

import androidx.lifecycle.ViewModel;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppViewModel extends ViewModel {
    private final Map<String, Note> notes = new HashMap<>();
    private NoteUpdateListener noteUpdateListener;

    public AppViewModel() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseNotes, ParseException e) {
                if (e == null) {
                    notes.clear();
                    for (ParseObject parseNote : parseNotes) {
                        Note note = new Note(
                                parseNote.getObjectId(),
                                parseNote.getString("icon"),
                                parseNote.getString("title"),
                                parseNote.getString("content")
                        );
                        notes.put(note.getObjectId(), note);
                    }
                    if (noteUpdateListener != null) {
                        noteUpdateListener.onNotesUpdated(new HashMap<>(notes));
                    }
                } else {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        });
    }

    public void setNoteUpdateListener(NoteUpdateListener listener) {
        this.noteUpdateListener = listener;
    }

    public Note getNoteById(String objectId) {
        return notes.get(objectId);
    }

    public void addOrUpdateNote(String objectId, Note note) {
        notes.put(objectId, note);
    }

    public void removeNoteById(String objectId) {
        notes.remove(objectId);
    }

    public interface NoteUpdateListener {
        void onNotesUpdated(Map<String, Note> updatedNotes);
    }

    public static class SingletonHolder {
        private static final AppViewModel INSTANCE = new AppViewModel();
    }

    public static AppViewModel getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
