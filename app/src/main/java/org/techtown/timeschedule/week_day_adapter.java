package org.techtown.timeschedule;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class week_day_adapter extends RecyclerView.Adapter<week_day_adapter.ViewHolder> {
    private int height, width;

    ArrayList<WeekdayList> items = new ArrayList<WeekdayList>();
    public void addItem(WeekdayList item) {
        items.add(item);
    }
    public void addpositionItem(WeekdayList item, int position){
        items.add(position, item);
    }
    public void resetItem(){
        items.clear();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.week_recycle_item, parent, false);
        height = parent.getMeasuredHeight();
        width = parent.getMeasuredWidth()/frag_week.WEEK_CALENDER_SIZE;

        //Log.d("뷰타입",Integer.toString(viewType));

        itemView.setLayoutParams(new RecyclerView.LayoutParams(width, height));

        return new ViewHolder(itemView);//뷰홀더 객체생성(뷰 객체전달) return
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d("포지션은?",Integer.toString(position));
        WeekdayList item = items.get(position);
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
            textView = itemView.findViewById(R.id.recycle_items);
        }

        public void setItem(WeekdayList item){
            textView.setText(item.getDay());
            if(getDate2(0).equals(item.getDay())){
                textView.setTextColor(Color.parseColor("#1DDB16"));
            }
        }
    }

    public String getDate2(int iDay) {//08/11
        Calendar temp = Calendar.getInstance();
        StringBuffer sbDate = new StringBuffer();


        temp.add(Calendar.DAY_OF_MONTH, iDay);


        int nYear = temp.get(Calendar.YEAR);
        int nMonth = temp.get(Calendar.MONTH) + 1;
        int nDay = temp.get(Calendar.DAY_OF_MONTH);


        if (nMonth < 10)
            sbDate.append("0");
        sbDate.append(nMonth);
        sbDate.append("/");
        if (nDay < 10)
            sbDate.append("0");
        sbDate.append(nDay);


        return sbDate.toString();
    }
}
