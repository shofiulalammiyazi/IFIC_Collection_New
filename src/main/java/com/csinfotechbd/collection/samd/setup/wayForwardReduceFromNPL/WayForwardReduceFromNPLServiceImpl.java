package com.csinfotechbd.collection.samd.setup.wayForwardReduceFromNPL;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WayForwardReduceFromNPLServiceImpl implements WayForwardReduceFromNPLService{


    @Autowired
    private WayForwardReduceFromNPLRepository wayForwardReduceFromNPLRepository;


    @Override
    public List<WayForwardReduceFromNPL> findAll() {
        return wayForwardReduceFromNPLRepository.findAll();
    }

    @Override
    public WayForwardReduceFromNPL findWayForwardReduceFromNPLById(Long id) {
        return wayForwardReduceFromNPLRepository.findWayForwardReduceFromNPLById(id);
    }

    @Override
    public void save(WayForwardReduceFromNPL wayForwardReduceFromNPL) {
        wayForwardReduceFromNPLRepository.save(wayForwardReduceFromNPL);
    }
}
