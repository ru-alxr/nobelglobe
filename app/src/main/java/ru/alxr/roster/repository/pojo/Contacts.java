package ru.alxr.roster.repository.pojo;

import com.squareup.moshi.Json;

import java.util.List;

import androidx.annotation.NonNull;

public class Contacts {

    @Json(name = "groups")
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Contacts contains %s groups", groups != null ? groups.size() : "ZERO");
    }

}