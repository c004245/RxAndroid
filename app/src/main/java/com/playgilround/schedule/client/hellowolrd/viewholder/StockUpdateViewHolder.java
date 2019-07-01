package com.playgilround.schedule.client.hellowolrd.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockUpdateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stock_item_symbol)
    public TextView stockSymbol;

    public StockUpdateViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

    }
}
