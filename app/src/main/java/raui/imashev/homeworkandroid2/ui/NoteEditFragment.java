package raui.imashev.homeworkandroid2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import raui.imashev.homeworkandroid2.R;
import raui.imashev.homeworkandroid2.data.Note;

import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_KEY;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;


public class NoteEditFragment extends Fragment {

    int currentNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        EditText editTextDateOfCreate = view.findViewById(R.id.editTextDateOfCreate);
        EditText editTextDate = view.findViewById(R.id.editTextDate);
        RadioButton radioButtonHightPriority = view.findViewById(R.id.radioHightPriority);
        RadioButton radioButtonMiddlePriority = view.findViewById(R.id.radioMiddlePriority);
        RadioButton radioButtonLowPriority = view.findViewById(R.id.radioLowPriority);
        Button button = view.findViewById(R.id.button);

        Bundle bundle = this.getArguments();
        currentNote = bundle.getInt(CURRENT_NOTE_NUMBER, -1);
        if (currentNote != -2) {
            Note note = bundle.getParcelable(CURRENT_NOTE_KEY);
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
        }


        button.setOnClickListener(v -> {
            Note note = new Note();
            note.setTitle(editTextTitle.getText().toString());
            note.setDescription(editTextDescription.getText().toString());
            note.setDate(editTextDate.getText().toString());
            note.setDateOfCreate(editTextDateOfCreate.getText().toString());
            note.setTitle(editTextTitle.getText().toString());
            if (radioButtonHightPriority.isChecked()) {
                note.setPriority(1);
            }
            if (radioButtonMiddlePriority.isChecked()) {
                note.setPriority(2);
            }
            if (radioButtonLowPriority.isChecked()) {
                note.setPriority(3);
            }
        });

        return view;
    }
}