package com.example.studentinformationmanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.studentinformationmanagement.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseCustomAdapter extends ArrayAdapter<String> {

    private Set<Integer> selectedItems = new HashSet<>();

    public CourseCustomAdapter(Context context, List<String> dataList) {
        super(context, 0, dataList);
    }

    public void toggleItemSelection(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String currentItem = getItem(position);

        TextView textView = itemView.findViewById(android.R.id.text1);
        textView.setText(currentItem);

        if (selectedItems.contains(position)) {
            textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.selected_item_color));
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        return itemView;
    }

    public Set<Integer> getSelectedItems() {
        return selectedItems;
    }

    public boolean checkInSetSelected(int position) {
        return selectedItems.contains(position);
    }
}