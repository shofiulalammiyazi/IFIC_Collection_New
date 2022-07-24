package com.unisoft.trackUserWorking;

public interface TrackingUserService {
    void save(TrackUserWorking trackUserWorking);

    TrackUserWorking findByDealerPin(String pin);


//    TrackUserWorking findByDateAndDealerPin(Date date, String pin);
}
