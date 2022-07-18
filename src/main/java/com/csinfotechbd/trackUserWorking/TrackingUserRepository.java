package com.csinfotechbd.trackUserWorking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TrackingUserRepository extends JpaRepository<TrackUserWorking, Long> {
//    @Query(value = "SELECT * " +
//            "FROM TRACK_USER_WORKING TUW " +
//            "WHERE TUW.DEALER_PIN = ? " +
//            "  AND to_char(TUW.CREATED_DATE, 'dd-Mon-yyyy') = (select to_char(current_timestamp, 'dd-Mon-yyyy') from dual) " +
//            "ORDER BY TUW.CREATED_DATE DESC " +
//            "FETCH FIRST 1 ROWS ONLY ", nativeQuery = true)
//    TrackUserWorking findTrackUserWorkingByDealerPin(String pin);

//    TrackUserWorking findFirstByCurrentDateAndDealerPinOrderByCurrentDateDesc(Date currentDate, String dealerPin);

    @Query(value = "SELECT * FROM TRACK_USER_WORKING TUW WHERE TUW.DEALER_PIN = ? ORDER BY TUW.CREATED_DATE DESC FETCH FIRST 1 ROW ONLY ", nativeQuery = true)
    TrackUserWorking findTrackUserWorkingByDealerPinOrderByCreatedDateDesc(String pin);
}
