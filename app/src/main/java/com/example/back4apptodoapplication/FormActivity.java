package com.example.back4apptodoapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

public class FormActivity extends ComponentActivity {
    private AppViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        viewModel = AppViewModel.getInstance();
        String objectId = getIntent().getStringExtra("objectId");
        Note note = (objectId != null) ? viewModel.getNoteById(objectId) : null;

        EditText iconEditText = findViewById(R.id.edit_note_icon);
        EditText titleEditText = findViewById(R.id.edit_note_title);
        EditText contentEditText = findViewById(R.id.edit_note_content);
        Button saveButton = findViewById(R.id.button_save);
        Button deleteButton = findViewById(R.id.button_delete);

        if (note != null) {
            iconEditText.setText(note.getIcon());
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
            deleteButton.setVisibility(View.VISIBLE);
        }

        saveButton.setOnClickListener(v -> {
            String icon = iconEditText.getText().toString();
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();

            if (note == null) {
                Note newNote = new Note(icon, title, content);
                newNote.addToParse(result -> {
                    viewModel.addOrUpdateNote(result, newNote);
                    finish();
                });
            } else {
                Note updatedNote = note.copy();
                updatedNote.setIcon(icon);
                updatedNote.setTitle(title);
                updatedNote.setContent(content);
                updatedNote.updateToParse(result -> {
                    viewModel.addOrUpdateNote(result, updatedNote);
                    finish();
                });
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (note != null) {
                viewModel.removeNoteById(note.getObjectId());
                note.deleteFromParse(result -> finish());
            }
        });
    }
}
