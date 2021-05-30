
package raui.imashev.homeworkandroid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import raui.imashev.homeworkandroid2.data.Note;
import raui.imashev.homeworkandroid2.ui.NotesFragment;

public class MainActivity extends AppCompatActivity {

    public static List<Note> notes = new ArrayList<>();

    public static final String CURRENT_NOTE_KEY = "NOTE";
    public static final String CURRENT_NOTE_NUMBER = "NUMBER";

    public static final String COLLECTION_NAME = "notes";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_DESCRIPTION = "description";
    public static final String TABLE_DATE = "date";
    public static final String TABLE_DATE_OF_CREATE = "dateOfCreate";
    public static final String TABLE_PRIORITY = "priority";

    public static final String TAG_FIREBASE = "firebase_error";

    public static final int CREATE_NEW_NOTE = -2;
    public static final int PRIORITY_HIGHT = 1;
    public static final int PRIORITY_MIDDLE = 2;
    public static final int PRIORITY_LOW = 3;

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        downloadData();
        addFragment(NotesFragment.newInstance());
    }



    public static void downloadData(){
        db.collection(COLLECTION_NAME).orderBy(TABLE_DATE)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes.clear();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Note note = new Note(String.valueOf(document.getData().get(TABLE_TITLE)),
                                    String.valueOf(document.getData().get(TABLE_DESCRIPTION)),
                                    String.valueOf(document.getData().get(TABLE_DATE)),
                                    String.valueOf(document.getData().get(TABLE_DATE_OF_CREATE)),
                                    Integer.parseInt(String.valueOf(document.getData().get(TABLE_PRIORITY))));
                            note.setId(document.getId());
                            notes.add(note);
                        }
                    } else {
                        Log.d(TAG_FIREBASE, "Error getting documents: ", task.getException());
                    }
                });

    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}