package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;


public class RxJavaActivity extends Activity {

    private static final String TAG = RxJavaActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_main);

        ButterKnife.bind(this);
        //전달 Observable
        //rx.Observable<String> myObservable = Observable.create(
        //        subscripber -> {
        //            subscripber.onNext("Hello");
        //            Log.d(TAG,"Hello");
        //            subscripber.onNext("world");
        //            Log.d(TAG,"World");
        //            subscripber.onCompleted();
        //            Log.d(TAG,"onComplete1");
        //        }
        //);

        //Observable 요약
        Observable.from(new String[] {"Hello", "world"}).subscribe(System.out::println);
        //myObservable.subscribe(s -> Log.d(TAG, "s ->" +s));

        //수신받는 Observer
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onComplete2");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext ->" +s);
                //tvTest.setText(s);
            }
        };

        Observable.from(new String[]{"Hello", "world"})
                .map(s -> {
                    Log.d(TAG, "s size ->" + s);
                    return s.length();
                }).subscribe(i -> {
                    Log.d(TAG, "s size ->" + i);
                    System.out.print(i);
                });

        //myObservable.subscribe(myObserver);

        String[][] helloAndGoodBye = {{"Hello", "World!"}, {"GoodBye", "World.."}};
        Observable.from(helloAndGoodBye)
                .flatMap((Func1<String[], Observable<String>>) Observable::from)
                .map(String::length).subscribe(i -> Log.d(TAG, "Test ->" + i));

        String[][] helloAndGoodBye2 = {{"Hello", "World!"}, {"GoodBye", "World.."}};
        Observable.merge(Observable.from(helloAndGoodBye2[0]), Observable.from(helloAndGoodBye2[1]))
                .map(String::length).subscribe(i -> Log.d(TAG, "Merge ->" + i));


        //filter test
        String[][] helloAndGoodBye3 = {{"Hello", "World!"}, {"GoodBye", "World.."}};
        Observable.from(helloAndGoodBye3)
                .flatMap((Func1<String[], Observable<String>>) strings -> Observable.from(strings)).filter(s -> !s.equals("World!")).map(s -> s.length()).subscribe(integer -> Log.d(TAG ,"REsult ->" + integer));

    }
}
