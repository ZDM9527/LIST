package com.example.lenovo.list;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private GridView gr;
    ImageButton arrow;
    SimpleAdapter sa;
    List<Map<String, Object>> ListItems;
    int[] imageIds = new int[]{
            R.drawable.d11, R.drawable.d12, R.drawable.d13, R.drawable.d14,
            R.drawable.d21, R.drawable.d22, R.drawable.d23, R.drawable.d24,
            R.drawable.d31, R.drawable.d32, R.drawable.d33, R.drawable.d34,
            R.drawable.d41, R.drawable.d42, R.drawable.d43, R.drawable.d44,
            R.drawable.d51, R.drawable.d52, R.drawable.d53, R.drawable.d54,
            R.drawable.d61, R.drawable.d62, R.drawable.d63, R.drawable.d64,
            R.drawable.d71, R.drawable.d72, R.drawable.d73, R.drawable.d74,
            R.drawable.d81, R.drawable.d82, R.drawable.d83, R.drawable.d84,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gr = (GridView) findViewById(R.id.grid);
        arrow = (ImageButton) findViewById(R.id.arrow);


        ListItems = new ArrayList<Map<String, Object>>();

        //创建适配器
        sa = new SimpleAdapter(MainActivity.this, getData(), R.layout.grid, new String[]{"1"},
                new int[]{R.id.list_item11});
        gr.setAdapter(sa);
        gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, thActivity.class);
                Bundle bd = new Bundle();
                //bd.putCharSequence("pic",imageIds[position]);
                // 传递图片
                intent.putExtra("pic", imageIds[position]);
                // bd.putCharSequence("pic",imageIds[position]);//传递图片
                startActivity(intent);
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    private List<Map<String, Object>> getData() {
        for (int i = 0; i < imageIds.length; i++) {
            Map<String, Object> list = new HashMap<>();
            list.put("1", imageIds[i]);
            ListItems.add(list);
        }
        return ListItems;
    }


    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }


}
