package ru.alxr.roster.repository.pojo;

import com.squareup.moshi.Json;

import java.util.List;

public class Group {

    @Json(name = "groupName")
    private String groupName;

    @Json(name = "people")
    private List<Person> people;

    public String getGroupName() {
        return groupName;
    }

    public List<Person> getPeople() {
        return people;
    }

}