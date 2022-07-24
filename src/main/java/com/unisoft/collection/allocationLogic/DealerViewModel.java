package com.unisoft.collection.allocationLogic;

import javax.persistence.Tuple;
import java.util.Objects;

public class DealerViewModel {
    private String dealerPin;
    private String dealerName;

    public DealerViewModel() {
    }

    public DealerViewModel(String dealerPin, String dealerName) {
        this.dealerPin = dealerPin;
        this.dealerName = dealerName;
    }

    public DealerViewModel(Tuple data) {
        this.dealerPin = Objects.toString(data.get("PIN"), "");
        this.dealerName = Objects.toString(data.get("NAME"), "");
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
