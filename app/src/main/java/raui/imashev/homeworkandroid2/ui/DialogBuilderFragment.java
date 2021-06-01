package raui.imashev.homeworkandroid2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import raui.imashev.homeworkandroid2.MainActivity;
import raui.imashev.homeworkandroid2.R;
import raui.imashev.homeworkandroid2.data.Note;

import static raui.imashev.homeworkandroid2.MainActivity.COLLECTION_NAME;
import static raui.imashev.homeworkandroid2.MainActivity.CURRENT_NOTE_NUMBER;
import static raui.imashev.homeworkandroid2.MainActivity.TAG_FIREBASE;
import static raui.imashev.homeworkandroid2.MainActivity.db;
import static raui.imashev.homeworkandroid2.MainActivity.notes;

public class DialogBuilderFragment extends DialogFragment {

    private int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_builder, null);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        position = bundle.getInt(CURRENT_NOTE_NUMBER, -1);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.action_delete)
                .setMessage(R.string.are_you_sure)
                .setView(contentView)
                .setPositiveButton(R.string.action_delete, (dialogInterface, i) -> {
                    Note note = notes.get(position);
                    notes.remove(position);
                    db.collection(COLLECTION_NAME).document(note.getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> Log.d(TAG_FIREBASE, "DocumentSnapshot successfully deleted!"))
                            .addOnFailureListener(e -> Log.w(TAG_FIREBASE, "Error deleting document", e));
                    Toast.makeText(getContext(), R.string.element_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.action_cancel, (dialog, which) -> Toast.makeText(getContext(), R.string.deletenig_canceled, Toast.LENGTH_SHORT).show());
        return builder.create();
    }
}
