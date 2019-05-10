package com.playgilround.schedule.client.hellowolrd;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvMain)
    TextView tvMain;

    @BindView(R.id.checkBox1)
    CheckBox checkBox1;

    @BindView(R.id.editText1)
    EditText editText1;

    @BindView(R.id.checkBox2)
    CheckBox checkBox2;

    @BindView(R.id.editText2)
    EditText editText2;

    @BindView(R.id.checkBox3)
    CheckBox checkBox3;

    @BindView(R.id.editText3)
    EditText editText3;

    @BindView(R.id.loginBtn)
    Button loginBtn;

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

        //한번에 처리 Combine
        //https://academy.realm.io/kr/posts/rxandroid-3/
        Observable<Boolean> checks1 = RxCompoundButton.checkedChanges(checkBox1);

        checks1.subscribe(check -> editText1.setEnabled(check));

        //Observable<Boolean> textExists1 = RxTextView.text(editText1).map(MainActivity::isEmpty);

        //Observable<Boolean> textValidations1 = Observable.combineLatest(checks1, textExists1, (check, exist) -> !check || exist);

        Observable<Boolean> checks2 = RxCompoundButton.checkedChanges(checkBox2);

        checks2.subscribe(check -> editText2.setEnabled(check));

        //Observable<Boolean> textExists2 = RxTextView.text(editText2)
        //        .map(MainActivity::isEmpty);


        //Observable<Boolean> textValidations2 = Observable.combineLatest(checks2, textExists2, (check, exist) -> !check || exist);

        Observable<Boolean> checks3 = RxCompoundButton.checkedChanges(checkBox3);

        checks3.subscribe(check -> editText3.setEnabled(check));

        //Observable<Boolean> textExists3 = RxTextView.text(editText3).map(MainActivity::isEmpty);

        //Observable<Boolean> textValidations3 = Observable.combineLatest(checks3, textExists3, (check, exist) -> !check || exist);


        //Observable.combineLatest(textValidations1, textValidations2, textValidations3,
        //        (validation1, validation2, validation3) ->
                    //validation1 && validation2 && validation3).subscribe(validation -> loginBtn.setEnabled(validation));

    }

    public static boolean isEmpty(CharSequence sequence) {
        return sequence.length() != 0;
    }
}

