package raui.imashev.homeworkandroid2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import raui.imashev.homeworkandroid2.MainActivity;
import raui.imashev.homeworkandroid2.data.Note;
import raui.imashev.homeworkandroid2.R;

import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_KEY;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;

public class NotesFragment extends Fragment {

    private int currentNoteNumber;
    public static final int IDM_A = 101;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView, MainActivity.notes);
        return view;

    }

    private void initRecyclerView(RecyclerView recyclerView, List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final NotesAdapter adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener((view, position) -> {
            registerForContextMenu(view);
            currentNoteNumber = position;
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_A, Menu.NONE, "Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == IDM_A) {
            NoteEditFragment nextFrag = new NoteEditFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(CURRENT_NOTE_KEY, MainActivity.notes.get(currentNoteNumber));
            bundle.putInt(CURRENT_NOTE_NUMBER, currentNoteNumber);
            nextFrag.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.notes_fragment, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();

            return true;
        }
        return super.onContextItemSelected(item);
    }
}
