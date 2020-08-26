package org.techtown.timeschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class timetime_adapter extends RecyclerView.Adapter<timetime_adapter.ViewHolder> {

    ArrayList<timetimeList> items = new ArrayList<timetimeList>();
    public void addItem(timetimeList item) {
        items.add(item);
    }
    public void addpositionItem(timetimeList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.week_recycle_item2, parent, false);

        itemView.setLayoutParams(new RecyclerView.LayoutParams((MainActivity.width_size_main-80)/7, 200));

        return new timetime_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        timetimeList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.recycle_items2);

        }

        public void setItem(timetimeList item){
            time.setText(item.getTime());
        }
    }
}
