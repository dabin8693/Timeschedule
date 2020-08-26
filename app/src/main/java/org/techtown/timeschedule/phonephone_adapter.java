package org.techtown.timeschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class phonephone_adapter extends RecyclerView.Adapter<phonephone_adapter.ViewHolder> {

    ArrayList<phonephoneList> items = new ArrayList<phonephoneList>();
    public void addItem(phonephoneList item) {
        items.add(item);
    }
    public void addpositionItem(phonephoneList item, int position){
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

        return new phonephone_adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        phonephoneList item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.recycle_items2);

        }

        public void setItem(phonephoneList item){
            phone.setText(item.getPhone());

        }
    }
}
