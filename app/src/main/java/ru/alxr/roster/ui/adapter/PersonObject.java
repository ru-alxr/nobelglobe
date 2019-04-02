package ru.alxr.roster.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import ru.alxr.roster.repository.pojo.Person;

public class PersonObject implements Parcelable {

    public PersonObject(Person person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.statusIcon = person.getStatusIcon();
        this.statusMessage = person.getStatusMessage();
    }

    private String firstName;
    private String lastName;
    private String statusIcon;
    private String statusMessage;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatusIcon() {
        return statusIcon;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    private PersonObject(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        statusIcon = in.readString();
        statusMessage = in.readString();
    }

    public static final Creator<PersonObject> CREATOR = new Creator<PersonObject>() {
        @Override
        public PersonObject createFromParcel(Parcel in) {
            return new PersonObject(in);
        }

        @Override
        public PersonObject[] newArray(int size) {
            return new PersonObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(statusIcon);
        dest.writeString(statusMessage);
    }

}