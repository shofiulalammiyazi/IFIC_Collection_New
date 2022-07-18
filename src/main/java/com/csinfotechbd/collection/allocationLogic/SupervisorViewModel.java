package com.csinfotechbd.collection.allocationLogic;

import javax.persistence.Tuple;
import java.util.Objects;

public class SupervisorViewModel {

    private String supervisorPin;
    private String supervisorName;

    public SupervisorViewModel() {
    }

    public SupervisorViewModel(String dealerPin, String dealerName) {
        this.supervisorPin = supervisorPin;
        this.supervisorName = supervisorName;
    }

    public SupervisorViewModel(Tuple data) {
        this.supervisorPin = Objects.toString(data.get("PIN"), "");
        this.supervisorName = Objects.toString(data.get("NAME"), "");
    }

    public String getSupervisorPin() {
        return supervisorPin;
    }

    public void setSupervisorPin(String supervisorPin) {
        this.supervisorPin = supervisorPin;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }
}
