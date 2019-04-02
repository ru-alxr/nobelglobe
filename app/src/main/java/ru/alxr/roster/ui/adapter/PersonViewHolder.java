package ru.alxr.roster.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import ru.alxr.roster.R;

class PersonViewHolder extends ChildViewHolder {

    PersonViewHolder(View itemView) {
        super(itemView);
        mStatusView = itemView.findViewById(R.id.status_view);
        mAvatarView = itemView.findViewById(R.id.avatar_view);
        mNameView = itemView.findViewById(R.id.name_view);
        mStatusMessageView = itemView.findViewById(R.id.status_message_view);
    }

    private ImageView mStatusView;
    private ImageView mAvatarView;
    private TextView mNameView;
    private TextView mStatusMessageView;

    void onBind(PersonObject object) {
        mStatusView.setImageResource(getStatusIconId(object.getStatusIcon()));
        mAvatarView.setImageResource(R.drawable.contacts_list_avatar_unknown);
        mNameView.setText(String.format("%s %s", object.getFirstName(), object.getLastName()));
        mStatusMessageView.setText(object.getStatusMessage());
    }

    private int getStatusIconId(String status) {
        switch (status) {
            case "online":
                return R.drawable.contacts_list_status_online;
            case "offline":
                return R.drawable.contacts_list_status_offline;
            case "pending":
                return R.drawable.contacts_list_status_pending;
            case "busy":
                return R.drawable.contacts_list_status_busy;
            case "away":
                return R.drawable.contacts_list_status_away;
            case "callforwarding":
                return R.drawable.contacts_list_call_forward;
            default:
                throw new RuntimeException("Unknown status " + status);
        }
    }

}