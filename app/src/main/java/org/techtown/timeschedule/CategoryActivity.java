package org.techtown.timeschedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class CategoryActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private FloatingActionButton category_float;
    private int[] color_array;
    private String[] category_array;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private category_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        color_array = new int[100];
        category_array = new String[100];

        restore();

        toolbar = findViewById(R.id.appbar_category);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//왼쪽 메뉴
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_18dp);
        //getSupportActionBar().setTitle(month+"월 "+year+"년");

        recyclerView = findViewById(R.id.category_recycle);
        layoutManager = new LinearLayoutManager(CategoryActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new category_adapter();//기본색-2236963
        for(int i = 0; i<100; i++) {
            if (category_array[i] == null) {
                i = 100;
            }else{
                adapter.addItem(new categoryList(color_array[i],category_array[i]));
            }
        }
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnlistItemClickListener2() {
            @Override
            public void onItemClick(category_adapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(CategoryActivity.this, Category_insertActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("head",category_array[position]);
                intent.putExtra("color",color_array[position]);
                startActivity(intent);
            }
        });

        category_float = findViewById(R.id.category_float);
        category_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,Category_addActivity.class);
                intent.putExtra("plus",1);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restore();
        adapter.resetItem();
        for(int i = 0; i<100; i++) {
            if (category_array[i] == null) {
                i = 100;
            }else{
                adapter.addItem(new categoryList(color_array[i],category_array[i]));
            }
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d("id:", Integer.toString(id));
        if (id == 16908332) {//back
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int i = 0; i<100; i++){//use,타입,바디초기화 초기화 기준: 기존 설정되어있던 범위
            //Log.d("초이스1i"+i,Integer.toString(i));
            editor.putInt("color_array"+i, 0);
            editor.putString("category_array"+i, null);
            editor.putInt("color_array"+i, color_array[i]);
            editor.putString("category_array"+i, category_array[i]);
        }
        editor.commit();
    }

    public void restore(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if (pref != null) {

            for (int i = 0; i < 100; i++) {
                color_array[i] = pref.getInt("color_array" + i, 0);//해당시간 칸수
                category_array[i] = pref.getString("category_array" + i, null);

            }
        }
    }

}
