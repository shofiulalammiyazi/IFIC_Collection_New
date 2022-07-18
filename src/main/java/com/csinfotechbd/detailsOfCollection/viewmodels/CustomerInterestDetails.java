package com.csinfotechbd.detailsOfCollection.viewmodels;

public class CustomerInterestDetails {
    private CustomerInterestDetail detail;

    public CustomerInterestDetails() {
    }

    public CustomerInterestDetails(CustomerInterestDetail detail) {
        this.detail = detail;
    }

    public CustomerInterestDetail getDetail() {
        return detail;
    }

    public void setDetail(CustomerInterestDetail detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "CustomerInterestDetails{" +
                "detail=" + detail +
                '}';
    }
}
