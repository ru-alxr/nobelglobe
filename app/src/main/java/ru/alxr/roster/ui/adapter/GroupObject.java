package ru.alxr.roster.ui.adapter;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class GroupObject extends ExpandableGroup<PersonObject> {

    public GroupObject(String title, List<PersonObject> items) {
        super(title, items);
    }

}