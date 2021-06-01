package raui.imashev.homeworkandroid2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import raui.imashev.homeworkandroid2.data.Note;
import raui.imashev.homeworkandroid2.R;

import static raui.imashev.homeworkandroid2.MainActivity.COLLECTION_NAME;
import static raui.imashev.homeworkandroid2.MainActivity.CREATE_NEW_NOTE;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_KEY;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;
import static raui.imashev.homeworkandroid2.MainActivity.TAG_FIREBASE;
import static raui.imashev.homeworkandroid2.MainActivity.db;
import static raui.imashev.homeworkandroid2.MainActivity.notes;

public class NotesFragment extends Fragment {

    private NotesAdapter adapter;
    private int currentNoteNumber;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView, notes);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView(RecyclerView recyclerView, List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(notes);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.SetOnItemClickListener((view, position) -> {
            registerForContextMenu(view);
            currentNoteNumber = position;
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                adapter.notifyDataSetChanged();
                break;
            case R.id.action_add:
                currentNoteNumber = CREATE_NEW_NOTE;
                NoteEditFragment nextFrag = new NoteEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CURRENT_NOTE_NUMBER, currentNoteNumber);
                nextFrag.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.notes_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_clear:
                for (int i = 0; i < notes.size(); i++) {
                    db.collection(COLLECTION_NAME).document(notes.get(i).getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> Log.d(TAG_FIREBASE, "DocumentSnapshot successfully deleted!"))
                            .addOnFailureListener(e -> Log.w(TAG_FIREBASE, "Error deleting document", e));
                }
                notes.clear();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.notes_context_menu, menu);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                NoteEditFragment nextFrag = new NoteEditFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(CURRENT_NOTE_KEY, notes.get(position));
                bundle.putInt(CURRENT_NOTE_NUMBER, currentNoteNumber);
                nextFrag.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.notes_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.action_delete:
                Log.d("Удаление","-1 шаг");
                currentNoteNumber = position;
                Bundle bundleDelete = new Bundle();
                Log.d("Удаление","-2 шаг");
                bundleDelete.putInt(CURRENT_NOTE_NUMBER, currentNoteNumber);
                DialogFragment dlgCustom = new DialogBuilderFragment();
                dlgCustom.setArguments(bundleDelete);
                Log.d("Удаление","-3 шаг");
                dlgCustom.show(getChildFragmentManager(), "transactionTag");
                Log.d("Удаление","-4 шаг");
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
