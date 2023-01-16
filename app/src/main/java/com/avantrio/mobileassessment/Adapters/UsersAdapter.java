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

import java.util.List;
import java.util.Random;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<UsersModel> usersModels;
    private Context context;
    SharedPreferenceClass sharedPreferenceClass;

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

        int color = getColorForLetter(usersModels.get(position).getName().substring(0,1));
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_name, null);
        if(drawable != null && drawable instanceof GradientDrawable)
        {
            ((GradientDrawable) drawable).setColor(color);
        }
        holder.fLetter.setBackground(drawable);


        //holder.fLetter.setBackgroundColor(color);

        holder.name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int adapterPosition = holder.getAdapterPosition();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                UserLogsFragment userLogsFragment = new UserLogsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userName", usersModels.get(adapterPosition).getName());
                sharedPreferenceClass.setValue_int("adapterPosition",adapterPosition);
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

    private int getColorForLetter(String firstLetter)
    {
        //int color = Color.GRAY;
        Random rnd = new Random();
        int alpha = 255;
        int red = (int) (rnd.nextInt(256) + (255 - rnd.nextInt(256)) * 0.7);
        int green = (int) (rnd.nextInt(256) + (255 - rnd.nextInt(256)) * 0.7);
        int blue = (int) (rnd.nextInt(256) + (255 - rnd.nextInt(256)) * 0.7);
        int color = Color.argb(alpha, red, green, blue);
        return color;
    }
}
