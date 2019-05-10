package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;

import com.playgilround.schedule.client.hellowolrd.databinding.RxMainBinding;

import androidx.databinding.DataBindingUtil;
import io.reactivex.disposables.Disposable;


public class ReactiveActivity extends Activity {
    private static final String TAG = RxJavaActivity.class.getSimpleName();

    private RxMainBinding binding;
    private Disposable disposable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.rx_main);
        binding.tvTemperature.setText("받아온 온도가 없습니다.");

        disposable =
    }
}
