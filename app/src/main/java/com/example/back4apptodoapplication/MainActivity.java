package com.example.back4apptodoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.back4apptodoapplication.AppViewModel;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private AppViewModel viewModel;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = AppViewModel.getInstance();

        ListView noteListView = findViewById(R.id.note_list_view);
        Button addNoteButton = findViewById(R.id.add_note_button);
        Button logoutButton = findViewById(R.id.button_logout);

        noteAdapter = new NoteAdapter(this, new ArrayList<>());
        noteListView.setAdapter(noteAdapter);

        // Set up item click listener
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("objectId", note.getObjectId());
                startActivity(intent);
            }
        });

        // Set up add button click listener
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                ParseUser.logOut();
                Toast.makeText(MainActivity.this, "great", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        // Observe changes in ViewModel
        viewModel.setNoteUpdateListener(new AppViewModel.NoteUpdateListener() {
            @Override
            public void onNotesUpdated(Map<String, Note> updatedNotes) {
                noteAdapter.updateNotes(new ArrayList<>(updatedNotes.values()));
            }
        });
    }
}
