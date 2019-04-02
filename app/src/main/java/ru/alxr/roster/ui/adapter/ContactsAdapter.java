package ru.alxr.roster.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import ru.alxr.roster.R;

public class ContactsAdapter extends ExpandableRecyclerViewAdapter<PeopleGroupViewHolder, PersonViewHolder> {

    public ContactsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        setOnGroupClickListener(flatPos -> {
            notifyItemChanged(flatPos);
            return false;
        });
    }

    @Override
    public PeopleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new PeopleGroupViewHolder(view);
    }

    @Override
    public PersonViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(PersonViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        PersonObject object = ((GroupObject) group).getItems().get(childIndex);
        holder.onBind(object);
    }

    @Override
    public void onBindGroupViewHolder(PeopleGroupViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group);
        holder.setDelimiter(isGroupExpanded(flatPosition));
    }

    public void expandAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (isGroupExpanded(i)) continue;
            toggleGroup(i);
        }
    }

}