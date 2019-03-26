package com.playgilround.schedule.client.hellowolrd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxAndroid.");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error " + e.toString());
            }


            @Override
            public void onNext(String s) {
                Log.d(TAG, "OnNext");
                ((TextView) findViewById(R.id.tvMain)).setText(s);
            }
        });
    }
}
