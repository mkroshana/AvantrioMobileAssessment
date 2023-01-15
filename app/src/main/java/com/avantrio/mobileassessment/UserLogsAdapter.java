package com.avantrio.mobileassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserLogsAdapter extends RecyclerView.Adapter<UserLogsAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<UserLogsModel> userLogsModels;
    public UserLogsAdapter(Context context, List<UserLogsModel> userLogsModels)
    {
        this.inflater = LayoutInflater.from(context);
        this.userLogsModels = userLogsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.user_logs_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.txtDate.setText(userLogsModels.get(position).getDate());
        holder.txtTime.setText(userLogsModels.get(position).getTime());
        holder.txtAlertView.setText(userLogsModels.get(position).getAlertView());
    }

    @Override
    public int getItemCount() {
        return userLogsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView txtDate, txtTime, txtAlertView;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtAlertView = itemView.findViewById(R.id.txtAlertView);
        }
    }
}
