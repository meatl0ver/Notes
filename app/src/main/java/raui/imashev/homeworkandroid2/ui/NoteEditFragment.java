package raui.imashev.homeworkandroid2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Objects;

import raui.imashev.homeworkandroid2.MainActivity;
import raui.imashev.homeworkandroid2.R;
import raui.imashev.homeworkandroid2.data.Note;

import static raui.imashev.homeworkandroid2.MainActivity.COLLECTION_NAME;
import static raui.imashev.homeworkandroid2.MainActivity.CREATE_NEW_NOTE;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_KEY;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;
import static raui.imashev.homeworkandroid2.MainActivity.PRIORITY_HIGHT;
import static raui.imashev.homeworkandroid2.MainActivity.PRIORITY_LOW;
import static raui.imashev.homeworkandroid2.MainActivity.PRIORITY_MIDDLE;
import static raui.imashev.homeworkandroid2.MainActivity.db;


public class NoteEditFragment extends Fragment {

    int currentNote;
    String currentId;
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
        assert bundle != null;
        currentNote = bundle.getInt(CURRENT_NOTE_NUMBER, -1);

        if (currentNote != CREATE_NEW_NOTE) {
            Note note = bundle.getParcelable(CURRENT_NOTE_KEY);
            currentId = note.getId();
            editTextTitle.setText(note.getTitle());
            editTextDescription.setText(note.getDescription());
            editTextDateOfCreate.setText(note.getDateOfCreate());
            editTextDate.setText(note.getDate());
            switch (note.getPriority()) {
                case PRIORITY_HIGHT:
                    editTextTitle.setBackgroundResource(R.color.red);
                    break;
                case PRIORITY_MIDDLE:
                    editTextTitle.setBackgroundResource(R.color.yellow);
                    break;
                case PRIORITY_LOW:
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
                note.setPriority(PRIORITY_HIGHT);
            }
            if (radioButtonMiddlePriority.isChecked()) {
                note.setPriority(PRIORITY_MIDDLE);
            }
            if (radioButtonLowPriority.isChecked()) {
                note.setPriority(PRIORITY_LOW);
            }

            if (currentNote==CREATE_NEW_NOTE){
                note.setId(""+Math.random()* 1000);
            } else {
                note.setId(currentId);
            }
            db.collection(COLLECTION_NAME).document(note.getId()).set(note);

            MainActivity.downloadData();
            NotesFragment nextFrag = new NotesFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.notes_fragment, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

        });

        return view;
    }
}