package com.playgilround.schedule.client.hellowolrd;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    }
}

