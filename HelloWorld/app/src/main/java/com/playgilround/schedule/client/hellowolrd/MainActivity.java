package com.playgilround.schedule.client.hellowolrd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observable
                .just("Hello RxAndroid..~")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        ((TextView) findViewById(R.id.tvMain)).setText(s);
                    }
                });
    }
}
