package com.playgilround.schedule.client.hellowolrd.util;

import android.util.Log;

import io.reactivex.functions.Consumer;

public class ErrorHandler implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        Log.d("App", "Error on " + Thread.currentThread().getName() + ":", throwable);
    }

    private static final ErrorHandler INSTANCE = new ErrorHandler();

    public static ErrorHandler get() {
        return INSTANCE;
    }

    private ErrorHandler() {

    }
}
