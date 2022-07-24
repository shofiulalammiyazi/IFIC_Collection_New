package com.unisoft.trackUserWorking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackingUserServiceImpl implements TrackingUserService {

    @Autowired
    private TrackingUserRepository trackingUserRepository;


    @Override
    public void save(TrackUserWorking trackUserWorking) {
        trackingUserRepository.save(trackUserWorking);
    }

    @Override
    public TrackUserWorking findByDealerPin(String pin) {
        return trackingUserRepository.findTrackUserWorkingByDealerPinOrderByCreatedDateDesc(pin);
    }

//    @Override
//    public TrackUserWorking findByDateAndDealerPin(Date date, String pin) {
//        TrackUserWorking trackUserWorking = trackingUserRepository.findFirstByCurrentDateAndDealerPinOrderByCurrentDateDesc(date, pin);
//        System.out.println("test");
//        return trackUserWorking;
//    }

}
