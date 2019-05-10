package com.playgilround.schedule.client.hellowolrd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.playgilround.schedule.client.hellowolrd.databinding.RxMainBinding;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.databinding.DataBindingUtil;
import io.reactivex.disposables.Disposable;


public class ReactiveActivity extends Activity {
    private static final String TAG = RxJavaActivity.class.getSimpleName();

    private final TemperatureManager manager = new TemperatureManager();

    private RxMainBinding binding;
    private Disposable disposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.rx_main);
        binding.tvTemperature.setText("받아온 온도가 없습니다.");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            //Create test.
            int nextTemperature = (new Random()).nextInt(15) + 10;
            manager.setTemperature(new TemperatureManager.Temperature(nextTemperature));
        }, 0L, 3, TimeUnit.SECONDS);

        disposable = manager.updateEvent().subscribe(this::updateView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
    @SuppressLint("DefaultLocale")
    private void updateView(TemperatureManager.Temperature temperature) {
        binding.tvTemperature.setText(
                String.format("현재 온도는 %d도입니다요.", temperature.getDegree()));
    }
}
