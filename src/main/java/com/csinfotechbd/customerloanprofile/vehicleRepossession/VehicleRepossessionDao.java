package com.csinfotechbd.customerloanprofile.vehicleRepossession;

import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class VehicleRepossessionDao {


    @Autowired
    private EntityManager entityManager;



    public boolean save (VehicleRepossession vehicleRepossession) {
        boolean save = false;

        try {
            Session session = entityManager.unwrap(Session.class);
            session.save(vehicleRepossession);
            session.flush();
            save= true;
            session.clear();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return save;
    }
}
