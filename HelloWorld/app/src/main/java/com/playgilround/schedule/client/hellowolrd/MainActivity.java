package com.playgilround.schedule.client.hellowolrd;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvMain)
    TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        Observable
                .just("Hello RxAndroid..~")
                .subscribe(s -> tvMain.setText(s));
    }
}
