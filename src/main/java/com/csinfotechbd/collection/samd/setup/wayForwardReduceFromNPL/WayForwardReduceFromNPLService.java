package com.csinfotechbd.collection.samd.setup.wayForwardReduceFromNPL;


import java.util.List;

public interface WayForwardReduceFromNPLService {
    List<WayForwardReduceFromNPL> findAll();

    WayForwardReduceFromNPL findWayForwardReduceFromNPLById(Long id);

    void save(WayForwardReduceFromNPL wayForwardReduceFromNPL);
}
