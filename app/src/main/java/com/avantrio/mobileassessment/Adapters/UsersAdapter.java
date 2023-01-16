package com.avantrio.mobileassessment.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.avantrio.mobileassessment.Models.UsersModel;
import com.avantrio.mobileassessment.R;
import com.avantrio.mobileassessment.SharedPreferenceClass;
import com.avantrio.mobileassessment.UserLogsFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<UsersModel> usersModels;
    private final Context context;
    SharedPreferenceClass sharedPreferenceClass;
    HashMap<Character, Integer> letterColors;
    Random rnd = new Random();

    public void setFilteredList (List<UsersModel> filteredList)
    {
        this.usersModels = filteredList;
        notifyDataSetChanged();
    }
    public UsersAdapter(Context context, List<UsersModel> usersModels)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.usersModels = usersModels;
        letterColors = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            letterColors.put(c, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.user_list_layout, parent, false);
        sharedPreferenceClass = new SharedPreferenceClass(parent.getContext());
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(usersModels.get(position).getName());
        holder.fLetter.setText(usersModels.get(position).getName().substring(0,1));

        char firstLetter = usersModels.get(position).getName().charAt(0);
        if(letterColors.containsKey(firstLetter)){
            int color = letterColors.get(firstLetter);
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_name, null);
            if (drawable instanceof GradientDrawable)
            {
                ((GradientDrawable) drawable).setColor(color);
            }
            holder.fLetter.setBackground(drawable);
        }

        holder.name.setOnClickListener(view ->
        {
            int adapterPosition = holder.getAdapterPosition();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            UserLogsFragment userLogsFragment = new UserLogsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("userName", usersModels.get(adapterPosition).getName());
            sharedPreferenceClass.setValue_int("adapterPosition",adapterPosition);
            userLogsFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().add(R.id.body_container, userLogsFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount()
    {
        return usersModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, fLetter;
        ImageView imgMenu;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
            fLetter = itemView.findViewById(R.id.first_letter);
            imgMenu = itemView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(view ->
            {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.three_dot_menu);
                popupMenu.show();
            });
        }
    }
}
