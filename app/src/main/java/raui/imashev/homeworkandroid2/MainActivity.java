package raui.imashev.homeworkandroid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import raui.imashev.homeworkandroid2.data.Note;
import raui.imashev.homeworkandroid2.ui.NotesFragment;

public class MainActivity extends AppCompatActivity {


    private final Note note1 = new Note("Футбол", "Поиграть в футбол с друзьями", "19.05.2021", "16.05.2021", 3);
    private final Note note2 = new Note("Парикмахер", "Сходить к парикмахеру", "26.05.2021", "16.05.2021", 2);
    private final Note note3 = new Note("Собеседование", "Собеседование в крутую компанию", "23.05.2021", "16.05.2021", 1);
    private final Note note4 = new Note("Кино", "Сходить в кино", "29.05.2021", "16.05.2021", 3);
    public static List<Note> notes = new ArrayList<>();

    public static final String CURRENT_NOTE_KEY = "NOTE";
    public static final String CURRENT_NOTE_NUMBER = "NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes.add(note1);
        notes.add(note2);
        notes.add(note3);
        notes.add(note4);
        addFragment(NotesFragment.newInstance());

    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}