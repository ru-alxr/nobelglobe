package ru.alxr.roster.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import ru.alxr.roster.R;

class PeopleGroupViewHolder extends GroupViewHolder {

    PeopleGroupViewHolder(View itemView) {
        super(itemView);
        mGroupNameView = itemView.findViewById(R.id.group_name_view);
        mDelimiter = itemView.findViewById(R.id.delimiter);
    }

    private TextView mGroupNameView;
    private View mDelimiter;

    void setTitle(ExpandableGroup group) {
        mGroupNameView.setText(group.getTitle());
    }

    void setDelimiter(boolean isExpanded) {
        mDelimiter.setVisibility(isExpanded ? View.INVISIBLE : View.VISIBLE);
    }
}