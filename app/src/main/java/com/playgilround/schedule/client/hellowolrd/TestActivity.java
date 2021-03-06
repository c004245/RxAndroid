package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.adapter.StockDataAdapter;
import com.playgilround.schedule.client.hellowolrd.storio.StockUpdateTable;
import com.playgilround.schedule.client.hellowolrd.storio.StorIOFactory;
import com.playgilround.schedule.client.hellowolrd.util.ErrorHandler;
import com.playgilround.schedule.client.hellowolrd.util.StockUpdate;
import com.playgilround.schedule.client.hellowolrd.yahoo.RetrofitYahooServiceFactory;
import com.playgilround.schedule.client.hellowolrd.yahoo.YahooService;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends Activity {

    static final String TAG = TestActivity.class.getSimpleName();

    @BindView(R.id.hello_world_salute)
    TextView helloText;

    @BindView(R.id.stock_updates_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_data_available)
    TextView noDataAvailableView;

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
//        Observable.just(
//                new StockUpdate("GOOGLE", 12.43, new Date()),
//                new StockUpdate("APPL", 645.1, new Date()),
//                new StockUpdate("TWTR", 1.43, new Date())
//        ).subscribe(stockUpdate -> stockDataAdapter.add(stockUpdate));
//        ).subscribe(stockDataAdapter::add);

        YahooService yahooService = new RetrofitYahooServiceFactory().create();

        String query = "select * from yahoo.finance.quote where symbol in ('YHOO','AAPL''GOOG','MSFT')";
        String env = "store://datatables.org/alltableswithkeys";

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap(
                        i -> yahooService.yqlQuery(query, env).toObservable()

                ).subscribeOn(Schedulers.io())
                .map(r -> r.getQuery().getResults().getQuote())
                .flatMap(r -> Observable.fromIterable(r))
                .map(r -> StockUpdate.create(r))
                .doOnNext(this::saveStockUpdate)
                .onExceptionResumeNext(v2(StorIOFactory.get(this)
                        .get()
                        .listOfObjects(StockUpdate.class)
                        .withQuery(Query.builder()
                                .table(StockUpdateTable.TABLE)
                                .orderBy("date DESC")
                                .limit(50)
                                .build())
                        .prepare()
                        .asRxObservable()
                        .take(1)
                        .flatMap(Observable::fromIterable))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(stockUpdate -> {
                            Log.d(TAG, "New update " + stockUpdate.getStockSymbol());
                            noDataAvailableView.setVisibility(View.GONE);
                            stockDataAdapter.add(stockUpdate);
                        }, error -> {
                            if (stockDataAdapter.getItemCount() == 0) {
                                noDataAvailableView.setVisibility(View.VISIBLE);
                            }
                        });
        RxJavaPlugins.setErrorHandler(ErrorHandler.get());
        Observable.<String>error(new Error("Crash!"))
                .doOnError(ErrorHandler.get())
                .subscribe(item -> log("subscribe", item), ErrorHandler.get());

        Observable.interval(0, 5, TimeUnit.SECONDS)
                        .compose(bindToLifecycle()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(data -> log(
//                        data.getQuery().getResults().getQuote().get(0).getSymbol())
//                );
    }

    private void log(String stage, String item) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage) {
        Log.d("APP", stage + ":" + Thread.currentThread().getName());
    }

    private void saveStockUpdate(StockUpdate stockUpdate) {
        StorIOFactory.get(this)
                .put()
                .object(stockUpdate)
                .prepare()
                .asRxSingle()
                .subscribe();
    }
}
