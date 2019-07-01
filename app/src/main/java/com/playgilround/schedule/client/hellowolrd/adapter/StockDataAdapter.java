package com.playgilround.schedule.client.hellowolrd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.R;
import com.playgilround.schedule.client.hellowolrd.viewholder.StockUpdateViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StockDataAdapter extends RecyclerView.Adapter<StockUpdateViewHolder> {

    private final List<String> data = new ArrayList<>();

    @Override
    public StockUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_update_item, parent, false);

        return new StockUpdateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockUpdateViewHolder holder, int position) {
        holder.stockSymbol.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(String stockSymbol) {
        this.data.add(stockSymbol);
        notifyItemInserted(data.size() -1);
    }
}
