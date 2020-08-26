package org.techtown.timeschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bodybody_adapter extends RecyclerView.Adapter<bodybody_adapter.ViewHolder> {

    ArrayList<bodybodyList> items = new ArrayList<bodybodyList>();
    public void addItem(bodybodyList item) {
        items.add(item);
    }
    public void addpositionItem(bodybodyList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.week_recycle_item2, parent, false);

        itemView.setLayoutParams(new RecyclerView.LayoutParams((MainActivity.width_size_main-80)*3/7, 200*viewType));

        return new bodybody_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bodybodyList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.recycle_items2);

        }

        public void setItem(bodybodyList item){
            body.setText(item.getBody());

        }
    }
}
