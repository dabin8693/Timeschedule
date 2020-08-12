package org.techtown.timeschedule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class day_adapter extends RecyclerView.Adapter<day_adapter.ViewHolder> implements OnlistItemClickListener3{

    OnlistItemClickListener3 listener;
    ArrayList<dayList> items = new ArrayList<dayList>();
    public void addItem(dayList item) {
        items.add(item);
    }
    public void addpositionItem(dayList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void setOnItemClickListener(OnlistItemClickListener3 listener){
        this.listener = listener;
    }

    public void onItemClick(day_adapter.ViewHolder holder, View view, int position){
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.week_recycle_item2, parent, false);

        itemView.setLayoutParams(new RecyclerView.LayoutParams((MainActivity.width_size_main-80)*3/8, 200*viewType));
        Log.d("사이즈는",Integer.toString(MainActivity.width_size_main));
        return new ViewHolder(itemView, this);//뷰홀더 객체생성(뷰 객체전달) return
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        dayList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolder(@NonNull View itemView, final OnlistItemClickListener3 listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycle_items2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(day_adapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(dayList item){
            textView.setText(item.getBody());
            if(items.get(getAdapterPosition()).getColor() == 0){
                //itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
                itemView.setBackgroundColor(items.get(getAdapterPosition()).getColor());
            }
        }
    }
}
