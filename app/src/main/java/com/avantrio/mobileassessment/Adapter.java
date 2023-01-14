package com.avantrio.mobileassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    LayoutInflater inflater;
    List<User> users;

    public void setFilteredList (List<User> filteredList)
    {
        this.users = filteredList;
        notifyDataSetChanged();
    }
    public Adapter (Context ctx, List<User> users)
    {
        this.inflater = LayoutInflater.from(ctx);
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.user_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
        }
    }
}
