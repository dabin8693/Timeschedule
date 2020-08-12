package org.techtown.timeschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<GridList> items = new ArrayList<GridList>();
    Context context;

    public void addItem(GridList item) {
        items.add(item);
    }
    public void addpositionItem(GridList item, int position){
        items.add(position, item);
    }
    @Override
    public int getCount() {//list사이즈
        return items.size();
    }

    @Override
    public Object getItem(int position) {//해당 포지션의 아이템을 리턴
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {//클릭했을때 해당포지션의 아이템을 알기위해
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//화면에 어떻게 보일지
        context = parent.getContext();
        //GridList gridList = items.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }
        if(position == 4){
            int height = parent.getMeasuredHeight()*2;
            int width = parent.getMeasuredWidth();

            TextView timeText = convertView.findViewById(R.id.grid_items);
            timeText.setHeight(height);
            timeText.setText(items.get(position).getItem());
        }else {
            TextView timeText = convertView.findViewById(R.id.grid_items);
            timeText.setText(items.get(position).getItem());
        }
        return convertView;
    }

}
