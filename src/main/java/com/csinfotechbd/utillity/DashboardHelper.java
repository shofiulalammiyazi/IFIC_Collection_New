package com.csinfotechbd.utillity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DashboardHelper {

    private static final int NUMBER_OF_ITEMS_IN_ONE_CHUNK = 1000;

    @Autowired
    private HttpSessionUtils httpSessionUtils;

    public Map getJpqlQueryParameter(String unit, HttpSession session) {
        Map map = new HashMap();

        List<Long> customerIds = getCustomerIds(unit, session);
        int numberOfChunk = getNumberOfChunk(customerIds);

        map.put("customerIds", customerIds);
        map.put("numberOfChunk", numberOfChunk);
        return map;
    }

    private List<Long> getCustomerIds(String unit, HttpSession session) {
        return unit.equalsIgnoreCase("Loan") ?
                httpSessionUtils.getLoanCustomerIds(session) :
                httpSessionUtils.getCardCustomerIds(session);
    }

    private int getNumberOfChunk(List<Long> customerIds) {
        int numberOfChunk = customerIds.size() / NUMBER_OF_ITEMS_IN_ONE_CHUNK;
        if (customerIds.size() % NUMBER_OF_ITEMS_IN_ONE_CHUNK > 0) numberOfChunk++;
        return numberOfChunk;
    }

}
