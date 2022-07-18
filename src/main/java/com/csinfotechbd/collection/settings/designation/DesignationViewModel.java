package com.csinfotechbd.collection.settings.designation;

public class DesignationViewModel {
    private Long id;
    private String desCode;
    private String name;

    public DesignationViewModel() {
    }

    public DesignationViewModel(String desCode, String name, Long id) {
        this.id = id;
        this.desCode = desCode;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
