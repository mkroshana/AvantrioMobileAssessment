package com.avantrio.mobileassessment;

import android.content.Context;
import android.os.Bundle;
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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<UsersModel> usersModels;

    public void setFilteredList (List<UsersModel> filteredList)
    {
        this.usersModels = filteredList;
        notifyDataSetChanged();
    }
    public UsersAdapter(Context ctx, List<UsersModel> usersModels)
    {
        this.inflater = LayoutInflater.from(ctx);
        this.usersModels = usersModels;
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
        holder.name.setText(usersModels.get(position).getName());
        holder.fLetter.setText(usersModels.get(position).getName().substring(0,1));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int adapterPosition = holder.getAdapterPosition();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                UserLogsFragment userLogsFragment = new UserLogsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userName", usersModels.get(adapterPosition).getName());
                bundle.putInt("userLocation", adapterPosition);
                userLogsFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().add(R.id.body_container, userLogsFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return usersModels.size();
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
