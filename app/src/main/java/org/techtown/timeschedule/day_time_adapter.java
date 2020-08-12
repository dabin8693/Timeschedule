package org.techtown.timeschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class day_time_adapter extends RecyclerView.Adapter<day_time_adapter.ViewHolder> {

    ArrayList<WeektimeList> items = new ArrayList<WeektimeList>();
    public void addItem(WeektimeList item) {
        items.add(item);
    }
    public void addpositionItem(WeektimeList item, int position){
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

        itemView.setLayoutParams(new RecyclerView.LayoutParams((MainActivity.width_size_main-80)/8, 200));
        return new ViewHolder(itemView);//뷰홀더 객체생성(뷰 객체전달) return
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        WeektimeList item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycle_items2);
        }

        public void setItem(WeektimeList item){
            textView.setText(item.getTime());
        }
    }
}
