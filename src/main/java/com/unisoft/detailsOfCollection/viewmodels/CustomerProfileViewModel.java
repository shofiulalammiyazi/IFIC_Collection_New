package com.unisoft.detailsOfCollection.viewmodels;

public class CustomerProfileViewModel {
    private CustomerProfileInformation profileInformation;
    private CustomerPersonalInformation personalInformation;
    private CustomerAccountInformation accountInformation;
    private CustomerInterestDetails interestDetails;

    public CustomerProfileViewModel() {
        this.profileInformation = new CustomerProfileInformation();
        this.personalInformation = new CustomerPersonalInformation();
        this.accountInformation = new CustomerAccountInformation();
        this.interestDetails = new CustomerInterestDetails();
    }

    public CustomerProfileViewModel(CustomerProfileInformation profileInformation, CustomerPersonalInformation personalInformation, CustomerAccountInformation accountInformation, CustomerInterestDetails interestDetails) {
        this.profileInformation = profileInformation;
        this.personalInformation = personalInformation;
        this.accountInformation = accountInformation;
        this.interestDetails = interestDetails;
    }

    public CustomerProfileInformation getProfileInformation() {
        return profileInformation;
    }

    public void setProfileInformation(CustomerProfileInformation profileInformation) {
        this.profileInformation = profileInformation;
    }

    public CustomerPersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(CustomerPersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public CustomerAccountInformation getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(CustomerAccountInformation accountInformation) {
        this.accountInformation = accountInformation;
    }

    public CustomerInterestDetails getInterestDetails() {
        return interestDetails;
    }

    public void setInterestDetails(CustomerInterestDetails interestDetails) {
        this.interestDetails = interestDetails;
    }

}
