package com.csinfotechbd.legal.report.managerial.statementOfOwnershipCertificateUnderSection33OfARA;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfOwnershipCertificateUnderSection33OfARADto {

    private String branchName;
    private String accountNumber;
    private String typeOfProperty;
    private String propertyDistrict;
    private String propertyMouza;
    private String propertySro;
    private String propertyPs;
    private double forcedSaleValue;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private String certificateObtainingDate;
    private String registrationDate;
    private String mutationDate;
    private String otherCollateralSecurit;
    private String remarks;

    public StatementOfOwnershipCertificateUnderSection33OfARADto() {
    }

    public StatementOfOwnershipCertificateUnderSection33OfARADto(Tuple data) {
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        typeOfProperty = Objects.toString(data.get("TYPE_OF_PROPERTY"), "-");
        propertyDistrict = Objects.toString(data.get("PROPERTY_DISTRICT"), "-");
        propertyMouza = Objects.toString(data.get("PROPERTY_MOUZA"), "-");
        propertySro = Objects.toString(data.get("PROPERTY_SRO"), "-");
        propertyPs = Objects.toString(data.get("PROPERTY_PS"), "-");
        forcedSaleValue = ((Number) Optional.ofNullable(data.get("FORCED_SALE_VALUE")).orElse(0)).doubleValue();
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        certificateObtainingDate = Objects.toString(data.get("CERTIFICATE_OBTAINING_DATE"), "-");
        registrationDate = Objects.toString(data.get("REGISTRATION_DATE"), "-");
        mutationDate = Objects.toString(data.get("MUTATION_DATE"), "-");
        otherCollateralSecurit = Objects.toString(data.get("OTHER_COLLATERAL_SECURITY"), "-");
        remarks = Objects.toString(data.get("REMARKS"), "-");
    }
}
