package com.avantrio.mobileassessment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avantrio.mobileassessment.Models.UserLogsModel;
import com.avantrio.mobileassessment.R;

import java.util.List;
import java.util.Locale;

public class UserLogsAdapter extends RecyclerView.Adapter<UserLogsAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<UserLogsModel> userLogsModels;
    private Context context;
    public UserLogsAdapter(Context context, List<UserLogsModel> userLogsModels)
    {
        this.context = context;
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
        holder.imgLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int adapterPosition = holder.getAdapterPosition();
                String latitude = String.valueOf(userLogsModels.get(adapterPosition).getLocationLat());
                String longitude = String.valueOf(userLogsModels.get(adapterPosition).getLocationLong());
                String uri = "https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;
                Uri navUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userLogsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView txtDate, txtTime, txtAlertView;
        public final ImageView imgLocation;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtAlertView = itemView.findViewById(R.id.txtAlertView);
            imgLocation = itemView.findViewById(R.id.imgLocation);
        }
    }
}
