package com.playgilround.schedule.client.hellowolrd;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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
    private PublishSubject<Temperature> subject = PublishSubject.create();

    void setTemperature(Temperature temperature) {
        subject.onNext(temperature);
    }

    Observable<Temperature> updateEvent() {
        return subject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
