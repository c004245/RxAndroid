package com.playgilround.schedule.client.hellowolrd;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

class TemperatureManager {

    static class Temperature {
        private int currentTemperature;

        Temperature(int degree) {
            this.currentTemperature = degree;
        }

        int getDegree() {
            return this.currentTemperature;
        }
    }

    //UI Event 연결
    private io.reactivex.subjects.PublishSubject<Temperature> subject = PublishSubject.create();

    void setTemperature(Temperature temperature) {
        subject.onNext(temperature);
    }

    Observable<Temperature> updateEvent() {
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
