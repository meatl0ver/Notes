package raui.imashev.homeworkandroid2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteDetailFragment extends Fragment {

    public static final String NOTE_KEY = "NOTE";
    private Note note;


    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment f = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_KEY, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);
        TextView textViewDateOfCreate = view.findViewById(R.id.textViewDateOfCreate);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        textViewTitle.setText(note.getTitle());
        textViewDescription.setText(note.getDescription());
        textViewDateOfCreate.setText(note.getDateOfCreate());
        textViewDate.setText(note.getDate());
        switch (note.getPriority()) {
            case 1:
                textViewTitle.setBackgroundResource(R.color.red);
                break;
            case 2:
                textViewTitle.setBackgroundResource(R.color.yellow);
                break;
            case 3:
                textViewTitle.setBackgroundResource(R.color.green);
                break;
        }

        return view;
    }
}