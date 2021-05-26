package raui.imashev.homeworkandroid2.data;

public interface NotesData {
    Note getNote(int position);

    int size();

    void deleteNote(int position);

    void updateNote(int position, Note cardData);

    void addNote(Note cardData);

    void clearNotes();
}
