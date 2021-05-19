package raui.imashev.homeworkandroid2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "NOTE";

    private Note currentNote;
    private boolean isLandscape;


    private final Note note1 = new Note("Футбол", "Поиграть в футбол с друзьями", "19.05.2021", "16.05.2021", 3);
    private final Note note2 = new Note("Парикмахер", "Сходить к парикмахеру", "26.05.2021", "16.05.2021", 2);
    private final Note note3 = new Note("Собеседование", "Собеседование в крутую компанию", "23.05.2021", "16.05.2021", 1);
    private final Note note4 = new Note("Кино", "Сходить в кино", "29.05.2021", "16.05.2021", 3);
    private final Note[] notes = {note1, note2, note3, note4};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;

        for (Note note : notes) {
            String title = note.getTitle();
            TextView tv = new TextView(getContext());
            tv.setText(title);
            tv.setTextSize(30);
            tv.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black));
            switch (note.getPriority()) {
                case 1:
                    tv.setBackgroundResource(R.color.red);
                    break;
                case 2:
                    tv.setBackgroundResource(R.color.yellow);
                    break;
                case 3:
                    tv.setBackgroundResource(R.color.green);
                    break;
            }
            layoutView.addView(tv);

            tv.setOnClickListener(v -> showNote(note));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = notes[0];
        }

        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void showPortNote(Note note) {
        Intent intent = new Intent(getContext(), NoteDetailActivity.class);
        intent.putExtra("NOTE", note);
        startActivity(intent);
    }

    private void showLandNote(Note note) {
        NoteDetailFragment detail = NoteDetailFragment.newInstance(note);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_note_detail, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
