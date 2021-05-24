package raui.imashev.homeworkandroid2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import raui.imashev.homeworkandroid2.MainActivity;
import raui.imashev.homeworkandroid2.R;
import raui.imashev.homeworkandroid2.data.Note;

import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_KEY;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;
import static raui.imashev.homeworkandroid2.MainActivity.notes;


public class NoteEditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        EditText editTextDateOfCreate = view.findViewById(R.id.editTextDateOfCreate);
        EditText editTextDate = view.findViewById(R.id.editTextDate);
        Button button = view.findViewById(R.id.button);
        Bundle bundle = this.getArguments();
        Note note = bundle.getParcelable(CURRENT_NOTE_KEY);
        int currentNote = bundle.getInt(CURRENT_NOTE_NUMBER, -1);
        editTextTitle.setText(note.getTitle());
        editTextDescription.setText(note.getDescription());
        editTextDateOfCreate.setText(note.getDateOfCreate());
        editTextDate.setText(note.getDate());
        switch (note.getPriority()) {
            case 1:
                editTextTitle.setBackgroundResource(R.color.red);
                break;
            case 2:
                editTextTitle.setBackgroundResource(R.color.yellow);
                break;
            case 3:
                editTextTitle.setBackgroundResource(R.color.green);
                break;
        }

        button.setOnClickListener(v -> {
            notes.remove(currentNote);
            MainActivity.notes.get(currentNote).setTitle(editTextTitle.getText().toString());
            MainActivity.notes.get(currentNote).setTitle(editTextDescription.getText().toString());
            MainActivity.notes.get(currentNote).setTitle(editTextDateOfCreate.getText().toString());
            MainActivity.notes.get(currentNote).setTitle(editTextDate.getText().toString());
        });
        return view;
    }
}