package com.djaphar.babysitter.SupportClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.djaphar.babysitter.Fragments.EventFragment;
import com.djaphar.babysitter.R;
import com.djaphar.babysitter.SupportClasses.ApiClasses.Child;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventChildrenRecyclerViewAdapter extends RecyclerView.Adapter<EventChildrenRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Child> children;
    private EventFragment eventFragment;

    public EventChildrenRecyclerViewAdapter(ArrayList<Child> children, EventFragment eventFragment) {
        this.children = children;
        this.eventFragment = eventFragment;
        if (children.size() == 0) {
            children.add(new Child(eventFragment.getString(R.string.kids_null_text), null, null, null,
                    null, null, null, null, null));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.event_children_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        if (children.get(0).getSurname() != null) {
            viewHolder.parentLayout.setOnClickListener(lView -> eventFragment.showChildEvent(children.get(viewHolder.getAdapterPosition())));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Child child = children.get(position);
        if (position > 0) {
            holder.itemView.setBackground(eventFragment.getResources().getDrawable(R.drawable.thin_top_border));
        }
        String fullName = child.getName();
        int color;
        if (child.getSurname() != null) {
            color = eventFragment.getResources().getColor(R.color.colorBlack60);
            fullName += " " + child.getSurname();
        } else {
            color = eventFragment.getResources().getColor(R.color.colorBlack30);
        }
        holder.eventChildrenTv.setTextColor(color);
        holder.eventChildrenTv.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout parentLayout;
        private TextView eventChildrenTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout_event_children);
            eventChildrenTv = itemView.findViewById(R.id.event_children_tv);
        }
    }
}
