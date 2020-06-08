package com.example.sectiongrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> sectionTitles = new ArrayList<>();
        List<List<String>> sectionItems = new ArrayList<>();
        for (int section = 0; section < 10; section++) {
            sectionTitles.add("Section " + (section + 1));
            List<String> items = new ArrayList<>();
            int numItems = (int) (Math.random() * 20 + 1);
            for (int i = 0; i < numItems; i++) {
                items.add("Item " + (section + 1) + "-" + (i + 1));
            }
            sectionItems.add(items);
        }

        final SectionGridAdapter adapter = new SectionGridAdapter();
        adapter.setSections(sectionTitles, sectionItems);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getSectionItemIndex(position).length == 1) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
