package com.playgilround.schedule.client.hellowolrd;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvMain)
    TextView tvMain;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Observable<String> simpleObservable =
                Observable.create(subscriber -> {
                    subscriber.onNext("RxAndroid Test.");
                    subscriber.onCompleted();
                });

        simpleObservable
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error -->" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        tvMain.setText(s);
                    }
                });

        simpleObservable.map(text -> {
            Log.d(TAG, "Text here..." + text);
            return text.toUpperCase();
        }).subscribe(s -> tvMain.setText(s));

        RxView.clicks(findViewById(R.id.btnRx))
                .map(event -> new Random().nextInt())
                .subscribe(value -> {
                    TextView textView = findViewById(R.id.tvRxView);
                    textView.setText("number ->" + value.toString());
                }, throwable -> {
                    Log.d(TAG, "Error -> " +throwable.getMessage());
                    throwable.printStackTrace();
                });

        //Event 동시 처리
        Observable<String> lefts = RxView.clicks(findViewById(R.id.leftButton))
                .map(event -> "left");

        Observable<String> rights = RxView.clicks(findViewById(R.id.rightButton))
                .map(event -> "right");

        Observable<String> together = Observable.merge(lefts, rights);

        together.subscribe(text -> ((TextView) findViewById(R.id.tvMerge)).setText(text));

        together.map(String::toUpperCase).subscribe(text -> Toast.makeText(this, text, Toast.LENGTH_LONG).show());

        //데이터 누적적으로 처리
        Observable<Integer> minuses = RxView.clicks(findViewById(R.id.minusBtn))
                .map(event -> -1);

        Observable<Integer> plus = RxView.clicks(findViewById(R.id.plusBtn))
                .map(event -> 1);

        Observable<Integer> together2 = Observable.merge(minuses, plus);
        together2.scan(0, (sum, number) -> sum + 1)
                .subscribe(count ->
                        ((TextView) findViewById(R.id.tvMain)).setText(count.toString()));

        together2.scan(0, (sum, number) -> sum + number)
                .subscribe(number ->
                        ((TextView) findViewById(R.id.tvMain)).setText(number.toString()));
    }
}

