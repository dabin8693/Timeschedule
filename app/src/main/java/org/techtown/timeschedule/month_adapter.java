package org.techtown.timeschedule;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class month_adapter extends RecyclerView.Adapter<month_adapter.ViewHolder> implements OnlistItemClickListener_month {

    private OnlistItemClickListener_month listener_month;
    ArrayList<monthList> items = new ArrayList<monthList>();
    public void addItem(monthList item) {
        items.add(item);
    }
    public void addpositionItem(monthList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    public void setOnItemClickListener(OnlistItemClickListener_month listener_month){
        this.listener_month = listener_month;
    }

    public void onItemClick(month_adapter.ViewHolder holder, View view, int position){
        if(listener_month != null){
            listener_month.onItemClick(holder, view, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.month_item, parent, false);


        return new ViewHolder(itemView, this);//뷰홀더 객체생성(뷰 객체전달) return
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        monthList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView body, time, category;
        ImageView circle;
        GradientDrawable drawable;
        public ViewHolder(@NonNull View itemView, final OnlistItemClickListener_month listener_month) {
            super(itemView);
            body = itemView.findViewById(R.id.item_body);
            time = itemView.findViewById(R.id.item_time);
            category = itemView.findViewById(R.id.item_category);
            circle = itemView.findViewById(R.id.item_circle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener_month != null){
                        listener_month.onItemClick(month_adapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(monthList item){
            body.setText(item.getBody());
            time.setText(item.getTime());
            category.setText(item.getCategory());
            if(items.get(getAdapterPosition()).getColor() == 0){
                //itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
                drawable = (GradientDrawable) ContextCompat.getDrawable(itemView.getContext(), R.drawable.circle_style);
                drawable.setColor(item.getColor());
                circle.setImageDrawable(drawable);
            }
        }
    }
}
