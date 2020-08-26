package org.techtown.timeschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class stat_adapter extends RecyclerView.Adapter<stat_adapter.ViewHolder> {

    ArrayList<statList> items = new ArrayList<statList>();
    public void addItem(statList item) {
        items.add(item);
    }
    public void addpositionItem(statList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.stat_item, parent, false);

        return new stat_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        statList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView category, percent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.item_cate);
            percent = itemView.findViewById(R.id.item_percent);
        }

        public void setItem(statList item){
            category.setText(item.getCategory());
            percent.setText(item.getPersent()+"%");
        }
    }
}
