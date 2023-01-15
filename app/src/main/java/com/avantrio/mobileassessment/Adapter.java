package com.avantrio.mobileassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        holder.fLetter.setText(users.get(position).getName().substring(0,1));
    }

    @Override
    public int getItemCount()
    {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, fLetter;
        ImageView imgMenu;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
            fLetter = itemView.findViewById(R.id.first_letter);
            imgMenu = itemView.findViewById(R.id.imgMenu);

            name.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    UserLogsFragment userLogsFragment = new UserLogsFragment();
                    activity.getSupportFragmentManager().beginTransaction().add(R.id.body_container,
                            new UserLogsFragment()).addToBackStack(null).commit();
                }
            });
            imgMenu.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    popupMenu.inflate(R.menu.three_dot_menu);
                    popupMenu.show();
                }
            });
        }
    }
}
