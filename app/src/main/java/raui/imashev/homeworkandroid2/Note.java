package raui.imashev.homeworkandroid2;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String title;
    private String description;
    private String date;
    private String dateOfCreate;
    private int priority;


    public Note(String title, String description, String date, String dateOfCreate, int priority) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.dateOfCreate = dateOfCreate;
        this.priority = priority;
    }

    public Note() {

    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
        dateOfCreate = in.readString();
        priority = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(String dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(dateOfCreate);
        dest.writeInt(priority);
    }
}
