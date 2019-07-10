package com.playgilround.schedule.client.hellowolrd.mock;

import android.os.Bundle;
import android.util.Log;

import com.playgilround.schedule.client.hellowolrd.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class MockActivity extends RxAppCompatActivity {

    BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mock);
        lifecycleSubject.onNext(ActivityEvent.CREATE);

        Observable.interval(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(i -> Log.i("App", "INSTANCE " + this.toString() + "reporting"));
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
