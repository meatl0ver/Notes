package raui.imashev.homeworkandroid2.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NotesImpl implements NotesData {
    private List<Note> notes;

    public NotesImpl() {
        notes = new ArrayList<>(7);
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

    public int size() {
        return notes.size();
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notes.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void clearNotes() {
        notes.clear();
    }
}

