package com.example.back4apptodoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.note_list_item, parent, false);
        }

        Note note = notes.get(position);

        TextView iconView = convertView.findViewById(R.id.note_icon);
        TextView titleView = convertView.findViewById(R.id.note_title);
        TextView contentView = convertView.findViewById(R.id.note_content);

        iconView.setText(note.getIcon());
        titleView.setText(note.getTitle());
        contentView.setText(note.getContent());

        return convertView;
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }
}
