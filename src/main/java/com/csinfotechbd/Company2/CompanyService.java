package com.csinfotechbd.Company2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    public void saveCompany(CompanyEntity companyEntity) {
        companyDao.saveCompanys(companyEntity);

    }

    public CompanyEntity getCompany() {
        return companyDao.getCompanyEntityByItsId();
    }

    public String getCompanyName() {
        return companyDao.FindComapnyName();
    }

    public String getCompanyLogoUrl() {
        // TODO Auto-generated method stub
        return companyDao.FindComapnyLogo();
    }

    public String getCompanyLogo() {
        return companyDao.getLogo();
    }

}
