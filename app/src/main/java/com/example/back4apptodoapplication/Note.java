package com.example.back4apptodoapplication;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class Note {
    private String objectId;
    private String icon;
    private String title;
    private String content;

    // Constructor with objectId
    public Note(String objectId, String icon, String title, String content) {
        this.objectId = objectId;
        this.icon = icon;
        this.title = title;
        this.content = content;
    }

    // Additional constructor without objectId
    public Note(String icon, String title, String content) {
        this(null, icon, title, content); // Call the existing constructor with null for objectId
    }

    // Getters and Setters
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Method to create a copy of the current Note
    public Note copy() {
        return new Note(this.objectId, this.icon, this.title, this.content);
    }

    // Method to add Note to Parse
    public void addToParse(final Callback<String> callback) {
        if (objectId != null) {
            throw new RuntimeException("Note is already saved to Parse!");
        }

        ParseObject parseNote = new ParseObject("Note");
        parseNote.put("icon", icon);
        parseNote.put("title", title);
        parseNote.put("content", content);

        parseNote.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    throw new RuntimeException("Error: " + e.getMessage());
                }
                objectId = parseNote.getObjectId();
                callback.onSuccess(parseNote.getObjectId());
            }
        });
    }

    // Method to update Note in Parse
    public void updateToParse(final Callback<String> callback) {
        if (objectId == null) {
            throw new RuntimeException("Note hasn't been saved to Parse yet!");
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseNote, ParseException e) {
                if (e != null) {
                    throw new RuntimeException("Error: " + e.getMessage());
                }
                parseNote.put("icon", icon);
                parseNote.put("title", title);
                parseNote.put("content", content);

                parseNote.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            throw new RuntimeException("Error: " + e.getMessage());
                        }
                        callback.onSuccess(parseNote.getObjectId());
                    }
                });
            }
        });
    }

    // Method to delete Note from Parse
    public void deleteFromParse(final Callback<Void> callback) {
        if (objectId == null) {
            throw new RuntimeException("Note hasn't been saved to Parse yet!");
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseNote, ParseException e) {
                if (e != null) {
                    throw new RuntimeException("Error: " + e.getMessage());
                }

                parseNote.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            throw new RuntimeException("Error: " + e.getMessage());
                        }
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    // Callback interface for asynchronous operations
    public interface Callback<T> {
        void onSuccess(T result);
    }
}
