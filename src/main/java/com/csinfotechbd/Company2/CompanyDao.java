package com.csinfotechbd.Company2;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CompanyDao {

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void saveCompanys(CompanyEntity companyEntity) {
        getSession().saveOrUpdate(companyEntity);
        getSession().flush();
    }

    //getOne by ID

		/*public CompanyEntity getCompanyEntityByItsId(){
			return (CompanyEntity) getSession().createCriteria(CompanyEntity.class).setMaxResults(1).uniqueResult();

		}*/

    /**
     * @return
     * @author Tajkia
     * @Date Apr 2, 2017 -- 2:43:26 PM
     */
    public CompanyEntity getCompanyEntityByItsId() {

        return (CompanyEntity) getSession().createCriteria(CompanyEntity.class).setMaxResults(1).uniqueResult();
    }

    public String FindComapnyName() {
        List<Object> objs = new ArrayList<>();
        try {
            Criteria name = getSession().createCriteria(CompanyEntity.class);
            name.setProjection(Projections.property("name"));
            objs = name.list();
            String url = String.valueOf(objs.get(0));
            return url;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }

    }

    public String FindComapnyLogo() {
        Criteria logo = getSession().createCriteria(CompanyEntity.class);
        logo.setProjection(Projections.property("logo"));
        @SuppressWarnings("unchecked")
        List<Object> objs = logo.list();
        return String.valueOf(objs.get(0));
    }


    public String getLogo() {
        Session session = getSession();
        CompanyEntity companyEntity = new CompanyEntity();
        try {
            Criteria crt = session.createCriteria(CompanyEntity.class);
            List<CompanyEntity> list = crt.list();
            System.err.println("COMPANY : " + list.get(0));
            if (list.size() > 0)
                companyEntity = list.get(0);
            else
                companyEntity.setLogoUrl("/images/logo.png");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return companyEntity.getLogoUrl();
    }

}
