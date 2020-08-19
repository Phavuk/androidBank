package com.pavuk.myapplication.screens.charges;

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
import com.pavuk.myapplication.db.entity.Charge;


/** recyclerview s diffutil callbackom - po pridaní sa nerefreshne celý
 * list ale sa len checkne  ktorý item sa má pridať (diffutil je novinka od googlu pre recycler) */
public class ChargesAdapter extends ListAdapter<Charge,ChargesAdapter.ViewHolder> {

    private AdapterView.OnItemClickListener listener;

    public ChargesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Charge> DIFF_CALLBACK = new DiffUtil.ItemCallback<Charge>() {

        @Override
        public boolean areItemsTheSame(@NonNull Charge oldItem, @NonNull Charge newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Charge oldItem, @NonNull Charge newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ChargesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.charge_item_layout,parent,false);
        return new ChargesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChargesAdapter.ViewHolder holder, int position) {
        Charge currentItem = getItem(position);
        holder.nameField.setText(currentItem.getName());
        holder.priceField.setText("- "+String.valueOf(currentItem.getPrice())+ " €");
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
