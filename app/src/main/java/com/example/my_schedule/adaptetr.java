package com.example.my_schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class adaptetr extends RecyclerView.Adapter<adaptetr.MyViewHolder> {
    Context context;
    ArrayList<String> Name,Calendar, E1;
    adaptetr(Context context, ArrayList<String> name, ArrayList<String> calendar, ArrayList<String> e1)
    {
        this.context = context;
        this.Name= name;
        this.Calendar= calendar;
        this.E1 = e1;

    }    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameT.setText(String.valueOf(Name.get(position)));
        holder.eventName.setText(String.valueOf(Calendar.get(position)));
        holder.dateEvent.setText(String.valueOf(E1.get(position)));
    }

    @Override
    public int getItemCount() {
        return Name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameT,eventName, dateEvent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameT= itemView.findViewById(R.id.name_text);
            eventName= itemView.findViewById(R.id.event_name);
            dateEvent= itemView.findViewById(R.id.date_of_event);
        }
    }

}
