package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.adapter.StockDataAdapter;
import com.playgilround.schedule.client.hellowolrd.util.StockUpdate;

import java.util.Date;

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

//        Observable.just("APPL", "GOOGLE", "TWTR").subscribe(s -> stockDataAdapter.add(s));
        Observable.just(
                new StockUpdate("GOOGLE", 12.43, new Date()),
                new StockUpdate("APPL", 645.1, new Date()),
                new StockUpdate("TWTR", 1.43, new Date())
//        ).subscribe(stockUpdate -> stockDataAdapter.add(stockUpdate));
        ).subscribe(stockDataAdapter::add);

    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }


}
