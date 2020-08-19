package com.pavuk.myapplication.screens.incomes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pavuk.myapplication.R;
import com.pavuk.myapplication.db.entity.Income;

/** recyclerview s diffutil callbackom - po pridaní sa nerefreshne celý
 * list ale sa len checkne  ktorý item sa má pridať (diffutil je novinka od googlu pre recycler) */
public class IncomesAdapter extends ListAdapter<Income,IncomesAdapter.ViewHolder> {

    private AdapterView.OnItemClickListener listener;

    public IncomesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Income> DIFF_CALLBACK = new DiffUtil.ItemCallback<Income>() {
        @Override
        public boolean areItemsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Income oldItem, @NonNull Income newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public IncomesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_item_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomesAdapter.ViewHolder holder, int position) {
        Income currentItem = getItem(position);
        holder.nameField.setText(currentItem.getName());
        holder.priceField.setText(String.valueOf(currentItem.getPrice())+ " €");
        holder.categoryField.setText(currentItem.getCategory());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameField;
        private TextView priceField;
        private TextView categoryField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.nameField);
            priceField = itemView.findViewById(R.id.priceField);
            categoryField = itemView.findViewById(R.id.categoryField);
        }
    }

}