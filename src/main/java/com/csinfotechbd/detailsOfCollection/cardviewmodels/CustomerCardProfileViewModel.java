package com.csinfotechbd.detailsOfCollection.cardviewmodels;

public class CustomerCardProfileViewModel {
    private PersonalInformation personalInformation;
    private ProfileInformation profileInformation;
    private ReferenceInformation referenceInformation;

    public CustomerCardProfileViewModel() {
    }

    public CustomerCardProfileViewModel(PersonalInformation personalInformation, ProfileInformation profileInformation, ReferenceInformation referenceInformation) {
        this.personalInformation = personalInformation;
        this.profileInformation = profileInformation;
        this.referenceInformation = referenceInformation;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public ProfileInformation getProfileInformation() {
        return profileInformation;
    }

    public void setProfileInformation(ProfileInformation profileInformation) {
        this.profileInformation = profileInformation;
    }

    public ReferenceInformation getReferenceInformation() {
        return referenceInformation;
    }

    public void setReferenceInformation(ReferenceInformation referenceInformation) {
        this.referenceInformation = referenceInformation;
    }
}
