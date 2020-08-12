package org.techtown.timeschedule;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class category_adapter extends RecyclerView.Adapter<category_adapter.ViewHolder> implements OnlistItemClickListener2 {

    OnlistItemClickListener2 listener;
    public ViewGroup parent_context;
    ArrayList<categoryList> items = new ArrayList<categoryList>();
    public void addItem(categoryList item) {
        items.add(item);
    }
    public void addpositionItem(categoryList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    public void setOnItemClickListener(OnlistItemClickListener2 listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.category_item, parent, false);
        parent_context = parent;
        return new ViewHolder(itemView, this);//뷰홀더 객체생성(뷰 객체전달) return
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        categoryList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView head;
        ImageView img;
        GradientDrawable drawable;
        private Object Context;

        public ViewHolder(@NonNull View itemView, final OnlistItemClickListener2 listener) {
            super(itemView);
            head = itemView.findViewById(R.id.category_item_head);
            img = itemView.findViewById(R.id.category_item_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(category_adapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(categoryList item){
            head.setText(item.getCategory());
            drawable = (GradientDrawable) ContextCompat.getDrawable(parent_context.getContext(), R.drawable.circle_style);
            drawable.setColor(item.getColor());
            img.setImageDrawable(drawable);
        }
    }
}
