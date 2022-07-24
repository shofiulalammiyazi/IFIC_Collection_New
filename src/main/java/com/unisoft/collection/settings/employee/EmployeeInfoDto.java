package com.unisoft.collection.settings.employee;

import lombok.Data;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@Data
public class EmployeeInfoDto {
    private Long id;
    private String pin;
    private String firstName;
    private String lastName;
    private String unit;
    private String designation;

    public EmployeeInfoDto() {
    }

    public EmployeeInfoDto(Tuple data) {
        id = ((Number) Optional.ofNullable(data.get("ID")).orElse(0L)).longValue();
        pin = Objects.toString(data.get("PIN"), "");
        firstName = Objects.toString(data.get("FIRST_NAME"), "");
        lastName = Objects.toString(data.get("LAST_NAME"), "");
        designation = Objects.toString(data.get("DESIGNATION"), "");
        unit = Objects.toString(data.get("UNIT"), "");
    }

    public EmployeeInfoDto(@NotNull EmployeeInfoEntity employeeInfoEntity) {
        this.id = employeeInfoEntity.getId();
        this.pin = employeeInfoEntity.getPin();
        if (employeeInfoEntity.getUser() == null) return;
        this.firstName = employeeInfoEntity.getUser().getFirstName();
        this.lastName = employeeInfoEntity.getUser().getLastName();
        unit = employeeInfoEntity.getUnit();
    }

    public EmployeeInfoDto(Long id, String pin, String firstName, String lastName) {
        this.id = id;
        this.pin = pin;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "{'id':" + id + ", 'pin':'" + pin + "', 'firstName':'" + firstName + "', 'lastName':'" + lastName + "'}";

    }
}
