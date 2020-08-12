package org.techtown.timeschedule;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class week_body_adapter extends RecyclerView.Adapter<week_body_adapter.ViewHolder> implements OnlistItemClickListener {

    //ViewGroup parent_context;
    //View itemview_1;
    OnlistItemClickListener listener;
    ArrayList<WeekbodyList> items = new ArrayList<WeekbodyList>();
    public void addItem(WeekbodyList item) {
        items.add(item);
    }
    public void addpositionItem(WeekbodyList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }

    @Override
    public int getItemViewType(int position)    {
        return items.get(position).getType();
    }//타입씀

    public void setOnItemClickListener(OnlistItemClickListener listener){
        this.listener = listener;
    }

    public void onItemClick(ViewHolder holder,View view, int position){
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.week_recycle_item2, parent, false);
        //parent_context = parent;
        Log.d("loglog뷰타입",Integer.toString(viewType));
        itemView.setLayoutParams(new RecyclerView.LayoutParams(frag_week.WIDTH_SIZE/frag_week.WEEK_CALENDER_SIZE, 200*viewType));
        return new ViewHolder(itemView, this);//뷰홀더 객체생성(뷰 객체전달) return//width_size 전체 화면 가로 길이
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        WeekbodyList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        LinearLayout linearLayout;
        private GradientDrawable drawable;
        public ViewHolder(@NonNull View itemView, final OnlistItemClickListener listener) {
            super(itemView);
            //itemview_1 = itemView;
            textView = itemView.findViewById(R.id.recycle_items2);
            //linearLayout = itemView.findViewById(R.id.recycle_items2_p);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(WeekbodyList item){
            Log.d("loglog검사","ㅇㅇ");
            textView.setText(item.getBody());
            if(items.get(getAdapterPosition()).getColor() == 0){
                //itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }else {
                itemView.setBackgroundColor(items.get(getAdapterPosition()).getColor());
            }
            /*
            drawable = (GradientDrawable) ContextCompat.getDrawable(parent_context.getContext(), R.drawable.item_style);
            if(item.getColor() == 0){
                //초기값
                drawable.setColor(Color.parseColor("#ffffff"));
            }else {
                drawable.setColor(item.getColor());
            }
            textView.setBackground(drawable);

             */
        }
    }
}
