package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.adapter.StockDataAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class TestActivity extends Activity {

    static final String TAG = TestActivity.class.getSimpleName();

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private StockDataAdapter stockDataAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d(TAG, "TestActivity --->");
        ButterKnife.bind(this);

        Observable.just("Hello! Please use this app responsibly")
                .subscribe(s -> helloText.setText(s));
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        stockDataAdapter = new StockDataAdapter();
        recyclerView.setAdapter(stockDataAdapter);

        Observable.just("APPL", "GOOGLE", "TWTR").subscribe(s -> stockDataAdapter.add(s));
    }
}
