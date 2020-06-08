package com.example.sectiongrid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SectionGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> sectionTitles;
    private List<List<String>> sectionItems;

    public void setSections(List<String> sectionTitles, List<List<String>> sectionItems) {
        this.sectionTitles = sectionTitles;
        this.sectionItems = sectionItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = sectionTitles.size();
        for (List<String> items : sectionItems) {
            count += items.size();
        }
        return count;
    }

    public int[] getSectionItemIndex(int position) {
        for (int section = 0; section < sectionTitles.size(); section++) {
            if (position == 0) {
                return new int[] { section };
            } else if (position <= sectionItems.get(section).size()) {
                return new int[] { section, position - 1 };
            }
            position -= 1 + sectionItems.get(section).size();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getSectionItemIndex(position).length == 1) {
            return R.layout.layout_header;
        } else {
            return R.layout.layout_item;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.layout_header) {
            return new HeaderViewHolder(itemView);
        } else {
            return new ItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int[] index = getSectionItemIndex(position);
        if (getSectionItemIndex(position).length == 1) {
            ((HeaderViewHolder) holder).bindHeader(index[0], sectionTitles.get(index[0]));
        } else {
            ((ItemViewHolder) holder).bindItem(index[0], index[1], sectionItems.get(index[0]).get(index[1]));
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
        }

        public void bindHeader(int section, String title) {
            titleText.setText(title);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
        }

        public void bindItem(int section, int index, String item) {
            itemText.setText(item);
        }
    }
}
