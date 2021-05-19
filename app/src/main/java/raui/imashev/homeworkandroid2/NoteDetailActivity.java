package raui.imashev.homeworkandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class NoteDetailActivity extends AppCompatActivity {


    private static final String NOTE = "NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewDateOfCreate = findViewById(R.id.textViewDateOfCreate);
        TextView textViewDate = findViewById(R.id.textViewDate);

        Note note = getIntent().getParcelableExtra(NOTE);
        textViewTitle.setText(note.getTitle());
        textViewDescription.setText(note.getDescription());
        textViewDate.setText(note.getDate());
        textViewDateOfCreate.setText(note.getDateOfCreate());
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
    }
}