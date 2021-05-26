package raui.imashev.homeworkandroid2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import raui.imashev.homeworkandroid2.data.Note;
import raui.imashev.homeworkandroid2.R;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<Note> notes;

    private OnItemClickListener itemClickListener;
    private int menuPosition;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.setData(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final TextView textViewDateOfCreate;
        private final TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            registerContextMenu(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDateOfCreate = itemView.findViewById(R.id.textViewDateOfCreate);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            textViewTitle.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                    menuPosition = getLayoutPosition();
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            itemView.setOnClickListener(v -> menuPosition = getLayoutPosition());

        }

        public void setData(Note note) {
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
        }
    }
}
