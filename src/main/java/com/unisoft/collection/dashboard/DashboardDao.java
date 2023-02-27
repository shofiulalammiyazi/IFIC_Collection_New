package com.unisoft.collection.dashboard;


import com.unisoft.collection.KPI.Card.TargetByManager.DealerTargetCardManager;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.visitLedger.VisitLedgerEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerloanprofile.dailynote.DailyNoteEntity;
import com.unisoft.customerloanprofile.diarynote.DairyNotesLoan;
import com.unisoft.customerloanprofile.followup.FollowUpEntity;
import com.unisoft.customerloanprofile.hotnote.HotNoteEntity;
import com.unisoft.retail.card.dataEntry.dailyNotes.DailyNotesCard;
import com.unisoft.retail.card.dataEntry.dairynotes.DairyNotes;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.followup.CardFollowUp;
import com.unisoft.retail.card.dataEntry.hotnotes.CardHotNotes;
import com.unisoft.retail.card.dataEntry.ptp.CardPtp;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import com.unisoft.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
@RequiredArgsConstructor
public class DashboardDao {

    private final EntityManager entityManager;
    private final DateUtils dateUtils;
    private static final int NUMBER_OF_ITEM_IN_ONE_CHUNK = 1000;


    public List<CardAccountDistributionInfo> getAccountDistInfoAllUserCurrentmonth() {
        List<CardAccountDistributionInfo> distributionInfos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.ne("dealerPin", "0"));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
            crt.add(Restrictions.eq("latest", "1"));

            distributionInfos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return distributionInfos;

    }

    public List<CardAccountDistributionInfo> getAccountDistInfoAllUserCurrentmonthForDealer(String dealerPin) {
        List<CardAccountDistributionInfo> distributionInfos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.ne("dealerPin", "0"));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", dealerPin));


            distributionInfos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return distributionInfos;

    }

    public List<PeopleAllocationLogicInfo> getAllDealerList(String userId, String designation, String unit) {
        List<PeopleAllocationLogicInfo> logicInfos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);
            EmployeeInfoEntity emp = new EmployeeInfoEntity();
            emp.setId(Long.parseLong(userId));
            //emp.setPin(userPin);

            Criteria crt = session.createCriteria(PeopleAllocationLogicInfo.class);

            if (designation.toUpperCase().contains("TEAM LEADER")) {
                crt.add(Restrictions.eq("teamlead", emp));
            } else if (designation.toUpperCase().contains("MANAGER")) {
                crt.add(Restrictions.eq("manager", emp));
            } else if (designation.toUpperCase().contains("SUPERVISOR")) {
                crt.add(Restrictions.eq("supervisor", emp));
            } else if (designation.toUpperCase().contains("DEALER")) {
                crt.add(Restrictions.eq("dealer", emp));
            }
            crt.add(Restrictions.eq("unit", unit));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            logicInfos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return logicInfos;
    }

    public List<PeopleAllocationLogicInfo> AccountWiseSummaryGetProducts(String designation, String userPin, String unit, String userId) {

        List<PeopleAllocationLogicInfo> logicInfos = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);

            EmployeeInfoEntity emp = new EmployeeInfoEntity();
            emp.setId(Long.parseLong(userId));
            //emp.setPin(userPin);

            Criteria crt = session.createCriteria(PeopleAllocationLogicInfo.class);
            if (designation.contains("Dealer")) {
                crt.add(Restrictions.eq("dealer", emp));
            } else if (designation.contains("Team Leader")) {
                crt.add(Restrictions.eq("teamlead", emp));
            } else if (designation.contains("Manager")) {
                crt.add(Restrictions.eq("manager", emp));
            } else if (designation.contains("Supervisor")) {
                crt.add(Restrictions.eq("supervisor", emp));
            }
            crt.createAlias("productGroupEntity", "productGroup");
            crt.add(Restrictions.eq("productGroup.cardAccount", unit));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            logicInfos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return logicInfos;
    }

    public List<LoanAccountDistributionInfo> getAccountListByUser(String userPin) {
        List<LoanAccountDistributionInfo> infos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            //crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));
            crt.addOrder(Order.desc("outStanding"));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            infos = crt.list();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return infos;
    }

    public Long getNumberOfAcPerPt(String userPin, String productCode) {
        Long accPerPg = 0l;

        try {


            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));
            crt.add(Restrictions.eq("schemeCode", productCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.rowCount());

            //System.err.println("HERE "+startDate+"  "+endDate);
            accPerPg = (Long) crt.uniqueResult();

            //System.err.println("PER PG NO :"+accPerPg+" PT:"+productCode);
        } catch (Exception e) {
            accPerPg = 0l;
            System.out.println(e.getMessage());
        }

        return accPerPg;
    }

    public List<RfdMenuModel> getRfdSummary(List<Long> customerIds, String jpqlQuery, int queryCount){
        List<RfdMenuModel> rfdMenuModels = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);

            Query query = session.createQuery(jpqlQuery);
            query.setParameter("startDate", dateUtils.getMonthStartDate());
            query = updateQueryWithNumberOfChunk(queryCount, query, customerIds);
            System.err.println(query.toString());
            rfdMenuModels = ((org.hibernate.query.Query) query).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rfdMenuModels;
    }

    private Query updateQueryWithNumberOfChunk(int queryCount, Query query, List<Long> customerIds) {
        int startIndex = 0;
        int endIndex = 0;

        for(int i=1; i<=queryCount; i++){
            final String parameter = "custIdList" + i;
            endIndex = Math.min((i * NUMBER_OF_ITEM_IN_ONE_CHUNK), customerIds.size());
            final List<Long> ids = customerIds.subList(startIndex, endIndex);
            startIndex = endIndex;
            query.setParameter(parameter, ids);
        }

        return query;
    }

    public double getTotalAmountPerPt(String userPin, String productCode) {
        double accPerPg = 0;

        try {


            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);

            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));

            crt.add(Restrictions.eq("schemeCode", productCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.sum("outStanding"));

            // System.err.println("HERE "+startDate+"  "+endDate);
            if (crt.uniqueResult() != null)
                accPerPg = Double.parseDouble(crt.uniqueResult().toString());

            // System.err.println("PER PG NO :"+accPerPg+" PT:"+accPerPg);
        } catch (Exception e) {
            accPerPg = 0;
            System.out.println(e.getMessage());
        }

        return accPerPg;
    }

    public List<CardAccountDistributionInfo> getListBYUserPinCard(String userPin) {
        List<CardAccountDistributionInfo> infos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ne("vvip", "VVIP"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.addOrder(Order.desc("outstandingAmount"));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            infos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return infos;
    }

    public double getTotalAmountPerPtCard(String userPin, String productCode) {
        double accPerPg = 0;

        try {


            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));

            crt.add(Restrictions.eq("productGroup", productCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.sum("outstandingAmount"));

            // System.err.println("HERE "+startDate+"  "+endDate);
            if (crt.uniqueResult() != null)
                accPerPg = Double.parseDouble(crt.uniqueResult().toString());

            //System.err.println("PER PG NO :"+accPerPg+" PT:"+accPerPg);
        } catch (Exception e) {
            accPerPg = 0;
            System.out.println(e.getMessage());
        }

        return accPerPg;
    }

    public double getTotalMinDueAmntPerPtCard(String userPin, String productCode) {
        double amntPerPg = 0;

        try {


            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));

            crt.add(Restrictions.eq("productGroup", productCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.sum("minDuePayment"));

            // System.err.println("HERE "+startDate+"  "+endDate);
            amntPerPg = Double.parseDouble(crt.uniqueResult().toString());

            //System.err.println("PER PG NO :"+accPerPg+" PT:"+accPerPg);
        } catch (Exception e) {
            amntPerPg = 0;
            System.out.println(e.getMessage());
        }

        return amntPerPg;
    }

    public Long getNumberOfAcPerPtCard(String userPin, String productCode) {
        Long accPerPg = 0l;

        try {


            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));

            crt.add(Restrictions.eq("productGroup", productCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.rowCount());

            //System.err.println("HERE "+startDate+"  "+endDate);
            accPerPg = (Long) crt.uniqueResult();

            //System.err.println("PER PG NO :"+accPerPg+" PT:"+productCode);
        } catch (Exception e) {
            accPerPg = 0l;
            System.out.println(e.getMessage());
        }

        return accPerPg;
    }

    public long getNoOfAccPerAgeCode(String userPin, String ageCode) {
        long accPerAgeCode = 0l;

        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));
            crt.add(Restrictions.eq("ageCode", ageCode));
            //crt.add(Restrictions.eq("dpdBucket",dpdBucketName.toUpperCase()));
            crt.setProjection(Projections.rowCount());

            //System.err.println("HERE "+startDate+"  "+endDate);
            accPerAgeCode = (Long) crt.uniqueResult();
        } catch (Exception e) {
            accPerAgeCode = 0l;
            System.out.println(e.getMessage());
        }
        return accPerAgeCode;
    }

    public List<CardPtp> getCardPtpByCustomer(Long custId) {
        List<CardPtp> ptpList = new ArrayList<>();


        try {
            Session session = entityManager.unwrap(Session.class);

            CustomerBasicInfoEntity customerBasicInfo = new CustomerBasicInfoEntity();
            customerBasicInfo.setId(custId);

            Criteria crt = session.createCriteria(CardPtp.class);
            crt.add(Restrictions.between("card_ptp_date", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfo));
            crt.addOrder(Order.desc("card_ptp_date"));

            ptpList = crt.list();
            session.clear();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ptpList;
    }

    public List<LoanPtp> getLoanPtpBYUser(Long cusId) {
        List<LoanPtp> loanPtps = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            CustomerBasicInfoEntity customerBasicInfo = new CustomerBasicInfoEntity();
            customerBasicInfo.setId(cusId);

            Criteria crt = session.createCriteria(LoanPtp.class);
            crt.add(Restrictions.between("loan_ptp_date", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfo));
            crt.addOrder(Order.desc("loan_ptp_date"));

            loanPtps = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return loanPtps;
    }

    public Long getTotalDailyNoteNumber(Long cusId, String userId, String unit) {
        Long totalDailyNote = 0l;
        try {
            Session session = entityManager.unwrap(Session.class);
            CustomerBasicInfoEntity basicInfoEntity = new CustomerBasicInfoEntity();
            basicInfoEntity.setId(cusId);

            if (unit == "Loan") {
                Criteria crt = session.createCriteria(DailyNoteEntity.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", basicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                totalDailyNote = (Long) crt.uniqueResult();
            } else if (unit.equals("Card")) {
                Criteria crt = session.createCriteria(DailyNotesCard.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", basicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                totalDailyNote = (Long) crt.uniqueResult();
            }


        } catch (Exception e) {
            totalDailyNote = 0l;
            System.out.println(e.getMessage());
        }
        return totalDailyNote;
    }

    public Long getTotalFollowUpNumber(Long cusId) {
        Long totalFollowUp = 0l;
        try {
            Session session = entityManager.unwrap(Session.class);
            CustomerBasicInfoEntity basicInfoEntity = new CustomerBasicInfoEntity();
            basicInfoEntity.setId(cusId);

            Criteria crt = session.createCriteria(FollowUpEntity.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("customerBasicInfo", basicInfoEntity));
            crt.setProjection(Projections.rowCount());

            totalFollowUp = (Long) crt.uniqueResult();

        } catch (Exception e) {
            totalFollowUp = 0l;
            System.out.println(e.getMessage());
        }
        return totalFollowUp;
    }

    public Long getTotalDiaryNoteNumber(Long cusId, String userId, String unit) {
        Long totalDiaryNote = 0l;
        try {
            Session session = entityManager.unwrap(Session.class);
            CustomerBasicInfoEntity basicInfoEntity = new CustomerBasicInfoEntity();
            basicInfoEntity.setId(cusId);

            if (unit.equals("Card")) {
                Criteria crt = session.createCriteria(DairyNotes.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", basicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                totalDiaryNote = (Long) crt.uniqueResult();
            } else if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(DairyNotesLoan.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", basicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                totalDiaryNote = (Long) crt.uniqueResult();
            }

        } catch (Exception e) {
            totalDiaryNote = 0l;
            System.out.println(e.getMessage());
        }
        return totalDiaryNote;
    }

    public DealerTargetCardManager getBySuperVisor(EmployeeInfoEntity superVisor) {
        DealerTargetCardManager amountCardManager = new DealerTargetCardManager();
        List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            employeeInfoEntities.add(superVisor);

            Criteria crt = session.createCriteria(DealerTargetCardManager.class);
            crt.add(Restrictions.eq("employeeInfoEntityList", employeeInfoEntities));
            crt.setMaxResults(1);

            amountCardManager = (DealerTargetCardManager) crt.uniqueResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return amountCardManager;
    }

    public List<String> getAgencyNameLoan() {
        List<String> agencyNameList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);

        try {
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.setProjection(Projections.distinct(Projections.property("agencyName")));
            agencyNameList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return agencyNameList;
    }

    public List<String> getAgencyNameCard() {
        List<String> agencyNameList = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);

        try {
            Criteria crt = session.createCriteria(CardAgencyDistributionInfo.class);
            crt.setProjection(Projections.distinct(Projections.property("agencyName")));
            agencyNameList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return agencyNameList;
    }

    public List<LoanAgencyDistributionInfo> getAgencyWiseDistList(String agencyName) {
        Session session = entityManager.unwrap(Session.class);
        List<LoanAgencyDistributionInfo> infos = new ArrayList<>();
        try {
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("agencyName", agencyName));
            infos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return infos;
    }

    public List<LoanAgencyDistributionInfo> getAllAgencyDistLoan() {
        Session session = entityManager.unwrap(Session.class);
        List<LoanAgencyDistributionInfo> infos = new ArrayList<>();
        try {
            Criteria crt = session.createCriteria(LoanAgencyDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            infos = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return infos;
    }

    public List<CardAgencyDistributionInfo> getAllAgencyDistCard() {
        Session session = entityManager.unwrap(Session.class);
        List<CardAgencyDistributionInfo> infos = new ArrayList<>();
        try {
            Criteria crt = session.createCriteria(CardAgencyDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            infos = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return infos;
    }

    public int getTotalLoggedInUser(String unit) {

        int rowNo = 0;
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(EmployeeInfoEntity.class);
            crt.add(Restrictions.ilike("unit", unit + "%", MatchMode.ANYWHERE));
            crt.createAlias("user", "user");
            crt.add(Restrictions.eq("user.loggedIn", true));
            crt.setProjection(Projections.rowCount());
            rowNo = Integer.parseInt(crt.uniqueResult().toString());

        } catch (Exception e) {
            rowNo = 0;
            System.out.println(e.getMessage());
        }
        return rowNo;
    }

    public int getVisitLedgerNo(Date visitDate, String unit) {
        Session session = entityManager.unwrap(Session.class);
        int rowNo = 0;


        Calendar c1 = Calendar.getInstance();
        c1.setTime(visitDate);

        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        visitDate = c1.getTime();
        try {
            Criteria crt = session.createCriteria(VisitLedgerEntity.class);
            crt.add(Restrictions.ilike("productUnit", unit + "%", MatchMode.ANYWHERE));
            crt.add(Restrictions.ge("dateOfVisit", visitDate));
            crt.setProjection(Projections.rowCount());
            rowNo = Integer.parseInt(crt.uniqueResult().toString());
        } catch (Exception e) {
            rowNo = 0;
            System.out.println(e.getMessage());
        }

        return rowNo;
    }

    public int getVisitLedgerByAcc(String accNo, String unit, String dealerPin) {
        int amount = 0;
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(VisitLedgerEntity.class);
            crt.add(Restrictions.eq("accountCardNumber", accNo));
            //crt.add(Restrictions.eq("createdBy",dealerPin));
            crt.add(Restrictions.ilike("productUnit", unit + "%", MatchMode.ANYWHERE));
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.setProjection(Projections.rowCount());

            amount = Integer.parseInt(crt.uniqueResult().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return amount;
    }

    public List<ResourceDetailModel> getNoOfLateEmp(String unit) {
        List<ResourceDetailModel> modelList = new ArrayList<>();
        Date today = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(today);

        c1.set(Calendar.HOUR_OF_DAY, 1);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        today = c1.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YY HH:mm:ss a");


        try {
            String d = formatter.format(today);
//            Map<String, String> parameters = new HashMap<>();
//            parameters.put("d", d);
            //Session session=entityManager.unwrap(Session.class);
            String sql = "SELECT COUNT(B.REASON_TITLE) TOTAL, B.REASON_TITLE FROM LATE_REASON_EXPLAIN_INFO A, LATE_REASON_ENTITY B, EMPLOYEE_INFO_ENTITY C WHERE A.LATE_REASON_ID=B.ID AND A.USER_ID=C.USER_ID AND A.CREATED_DATE >= :d  GROUP BY B.REASON_TITLE";
            //SQLQuery query=session.createSQLQuery(sql);

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("d", d);
            List<Object[]> rows = query.getResultList();

            for (Object[] data : rows) {
                ResourceDetailModel model = new ResourceDetailModel();
                if (data[0] != null)
                    model.setNoOfEmp(Integer.parseInt(data[0].toString()));
                if (data[1] != null)
                    model.setStatusName(data[1].toString());

                modelList.add(model);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return modelList;
    }

    public long getTotalFollowUpByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(FollowUpEntity.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(CardFollowUp.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public long getTotalDailyNoteByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(DailyNoteEntity.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(DailyNotesCard.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public long getTotalDairyNoteByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(DairyNotesLoan.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(DairyNotes.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public long getTotalPtpNoteByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(LoanPtp.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(CardPtp.class);
                crt.add(Restrictions.eq("createdBy", userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public long getTotalHotNoteNoteByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(HotNoteEntity.class);
                crt.add(Restrictions.eq("createdBy",userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(CardHotNotes.class);
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.add(Restrictions.between("createdDate", start, end));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public long getNoOfHotNotesByCustomerId(Long customrId, String unit, String userId) {
        long rowCount = 0;
        CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
        customerBasicInfoEntity.setId(customrId);
        try {
            Session session = entityManager.unwrap(Session.class);
            if (unit.equals("Loan")) {
                Criteria crt = session.createCriteria(HotNoteEntity.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            } else {
                Criteria crt = session.createCriteria(CardHotNotes.class);
                crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
                crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfoEntity));
                //crt.add(Restrictions.eq("createdBy",userId));
                crt.setProjection(Projections.rowCount());
                rowCount = (long) crt.uniqueResult();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowCount;
    }

    public long getTotalVisitLedgerNoteByDealer(String userId, String unit, String startTime, String endTIme, Date selectedDate) {
        long rowCount = 0;

        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(selectedDate);

            c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, startTime.lastIndexOf(":"))));
            c1.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(startTime.lastIndexOf(":") + 1)));
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            Date start = c1.getTime();

            Calendar c2 = Calendar.getInstance();
            c2.setTime(selectedDate);

            c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTIme.substring(0, startTime.lastIndexOf(":"))));
            c2.set(Calendar.MINUTE, Integer.parseInt(endTIme.substring(startTime.lastIndexOf(":") + 1)));
            c2.set(Calendar.SECOND, 0);
            c2.set(Calendar.MILLISECOND, 0);
            Date end = c2.getTime();

            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(VisitLedgerEntity.class);
            crt.add(Restrictions.eq("createdBy", userId));
            crt.add(Restrictions.eq("productUnit", unit));
            crt.add(Restrictions.between("createdDate", start, end));
            crt.setProjection(Projections.rowCount());
            rowCount = (long) crt.uniqueResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return rowCount;
    }

    public LoanAccountDistributionInfo getDistInfoByLoanBasicInfo(LoanAccountBasicInfo basicInfo) {
        LoanAccountDistributionInfo distributionInfo = new LoanAccountDistributionInfo();
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(LoanAccountDistributionInfo.class);
            crt.add(Restrictions.eq("loanAccountBasicInfo", basicInfo));
            crt.addOrder(Order.desc("id"));
            distributionInfo = (LoanAccountDistributionInfo) crt.list().get(0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return distributionInfo;
    }

//    public LoanPayment getLoanPaymentSummaryByAcc(String accNo, Date allocationDate) {
//        List<LoanPayment> loanPayment = new ArrayList<>();
//        try {
//            allocationDate = dateManager.getStartingPointOfDay(allocationDate);
//
//            Session session = entityManager.unwrap(Session.class);
//            Criteria crt = session.createCriteria(LoanPayment.class);
//            crt.add(Restrictions.eq("accountNo", accNo));
//            crt.add(Restrictions.between("paymentDate", allocationDate, new Date()));
//            loanPayment = crt.list();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return loanPayment;
//    }

    public List<CardPaymentModel> getCardPaymentByAcc(String cardNo, Date date) {
        List<CardPaymentModel> paymentList = new ArrayList<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd");
            String sDate = formatter.format(date);

//            Map<String, String> parameters = new HashMap<>();
//            parameters.put("sDate", sDate);
//            parameters.put("cardNo", cardNo);

            //Session session=entityManager.unwrap(Session.class);
            //String s="select a.AO_CARDHDR_NO,a.AO_BILLING_AMT,to_date(a.AO_MATCH_DATE,'rrrrmmdd')AO_MATCH_DATE  from CARD_APONUS a where to_date(a.AO_MATCH_DATE,'rrrrmmdd') >= to_date('"+sDate+"','dd-MON-RRRR') and a.AO_CARDHDR_NO='"+cardNo+"'";
            // String sql="SELECT a.AO_CARDHDR_NO, a.AO_BILLING_AMT, b.CB_FIN_ACCTNO, b.CB_LAST_AGE_CD, TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') AO_MATCH_DATE FROM CARD_APONUS a join cp_fintbl b ON TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') >= TO_DATE ('"+sDate+"') AND LTRIM (RTRIM (a.AO_FIN_ACCTNO))=LTRIM (RTRIM (b.CB_FIN_ACCTNO)) AND LTRIM (RTRIM (a.AO_CARDHDR_NO ))= '"+cardNo+"'";

            String sql = "select a.AO_CARDHDR_NO,a.AO_BILLING_AMT,to_date(a.AO_MATCH_DATE,'rrrrmmdd')AO_MATCH_DATE, b.CB_LAST_AGE_CD from CARD_APONUS a, CP_FINTBL b where a.AO_MATCH_DATE >= :sDate and a.AO_CARDHDR_NO= :cardNo and a.AO_TRANS_TYPE='Payment' and b.CB_FIN_ACCTNO=a.AO_FIN_ACCTNO";
            //SQLQuery query = session.createSQLQuery(sql);
            //System.err.println("SQL "+sql);
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("sDate", sDate);
            query.setParameter("cardNo", cardNo);
            List<Object[]> rows = query.getResultList();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Object[] row : rows) {
                CardPaymentModel paymentModel = new CardPaymentModel();

                if (row[0] != null)
                    paymentModel.setCardNo(row[0].toString());
                if (row[1] != null)
                    paymentModel.setAmount(Double.parseDouble(row[1].toString()));
                if (row[2] != null) {
                    //System.err.println("DATE "+row[2].toString());
                    paymentModel.setPaymentDate(format.parse(row[2].toString()));
                }
                if (row[3] != null) {
                    if (row[3].toString().toUpperCase().equals("CR"))
                        paymentModel.setCurrentAgeCode(0);
                    else
                        paymentModel.setCurrentAgeCode(Integer.parseInt(row[3].toString()));
                }
//                if(row[4]!=null)
//                    paymentModel.setPaymentDate(format.parse(row[4].toString()));

                paymentList.add(paymentModel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return paymentList;
    }

    public List<CardAccountDistributionInfo> getSecuredCard(String userPin) {
        List<CardAccountDistributionInfo> distributionInfos = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
            crt.add(Restrictions.ne("secureCard", "."));
            crt.add(Restrictions.ne("vvip", "VVIP"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));

            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            distributionInfos = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return distributionInfos;
    }

    //need to change the query for last payment
    public CardPaymentModel getCardPaymentByAccLastPayment(String cardNo, Date date) {
        CardPaymentModel payment = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY");
            String sDate = formatter.format(date);

            //Session session=entityManager.unwrap(Session.class);
            //String s="select a.AO_CARDHDR_NO,a.AO_BILLING_AMT,to_date(a.AO_MATCH_DATE,'rrrrmmdd')AO_MATCH_DATE  from CARD_APONUS a where to_date(a.AO_MATCH_DATE,'rrrrmmdd') >= to_date('"+sDate+"','dd-MON-RRRR') and a.AO_CARDHDR_NO='"+cardNo+"'";
            String sql = "SELECT a.AO_CARDHDR_NO, a.AO_BILLING_AMT, b.CB_FIN_ACCTNO, b.CB_LAST_AGE_CD, TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') AO_MATCH_DATE FROM CARD_APONUS a join cp_fintbl b ON TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') >= TO_DATE ('" + sDate + "')  AND a.AO_FIN_ACCTNO=b.CB_FIN_ACCTNO AND a.AO_CARDHDR_NO= '" + cardNo + "' ORDER BY a.AO_MATCH_DATE FETCH FIRST 1 ROW ONLY";

            //SQLQuery query = session.createSQLQuery(sql);
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> rows = query.getResultList();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            for (Object[] row : rows) {
//                CardPaymentModel paymentModel=new CardPaymentModel();

                if (row[0] != null)
                    payment.setCardNo(row[0].toString());
                if (row[1] != null)
                    payment.setAmount(Double.parseDouble(row[1].toString()));
                if (row[2] != null) {
                    payment.setFinAccNo(row[2].toString());
                }
                if (row[3] != null) {
                    if (row[3].toString().toUpperCase().equals("CR"))
                        payment.setCurrentAgeCode(0);
                    else
                        payment.setCurrentAgeCode(Integer.parseInt(row[3].toString()));
                }
                if (row[4] != null)
                    payment.setPaymentDate(format.parse(row[4].toString()));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return payment;
    }

    public LoanAccountDpd getCurrentDpd(String accNo) {
        LoanAccountDpd accountDpd = null;

        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(LoanAccountDpd.class);
            crt.add(Restrictions.eq("accNo", accNo));
            crt.setMaxResults(1);
            accountDpd = (LoanAccountDpd) crt.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountDpd;
    }

    public List<CardDetailPaymentModel> getCardDetailedPaymentInfo(String cardNo, Date date, int MO_ageCode) {
        List<CardDetailPaymentModel> cardDetailPaymentList = new ArrayList<>();

        try {
            String ageCodeSql = "";
            if (MO_ageCode > 0) {
                for (int i = 1; i <= MO_ageCode - 1; i++) {
                    String temp = ",b.CB_";
                    int dpd = 30 * i;
                    temp = temp + dpd + "DAYS_BAL ";

                    ageCodeSql = ageCodeSql + temp;
                }
            }


            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY");
            String sDate = formatter.format(date);
            //Session session=entityManager.unwrap(Session.class);

            String sql = "SELECT a.AO_CARDHDR_NO, a.AO_BILLING_AMT, b.CB_FIN_ACCTNO, b.CB_LAST_AGE_CD, TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') AO_MATCH_DATE," +
                    "b.CB_FIN_ACCT_TYPE ," +
                    "b.CB_PRODUCT_CD," +
                    "b.CB_LAST_AGING_DATE," +
                    "b.CB_MIN_PMT," +
                    "b.CB_CURMTH_BAL," +
                    "b.CB_CURMTH_CNT," +
                    "b.CB_CURMTH_PAYDUE_DATE," +
                    "b.CB_LAST_PAYMENT_DATE," +
                    "b.CB_LAST_PAYMENT_AMT," +
                    "b.CB_INDIVIDUAL_ACCTNO," +
                    "b.CB_LAST_ACTUAL_STMT_DATE " + ageCodeSql +
                    "FROM CARD_APONUS a join cp_fintbl b ON TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') >= TO_DATE ('" + sDate + "')  AND a.AO_FIN_ACCTNO=b.CB_FIN_ACCTNO AND a.AO_CARDHDR_NO = '" + cardNo + "' ORDER BY a.AO_MATCH_DATE";

            System.err.println("FINAL SQL :" + sql);
            //SQLQuery query = session.createSQLQuery(sql);
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> rows = query.getResultList();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


            for (Object[] row : rows) {
                CardDetailPaymentModel payment = new CardDetailPaymentModel();

                if (row[0] != null)
                    payment.setCardNo(row[0].toString());
                if (row[1] != null)
                    payment.setAmount(Double.parseDouble(row[1].toString()));
                if (row[2] != null) {
                    payment.setFinAccNo(row[2].toString());
                }
                if (row[3] != null) {
                    if (row[3].toString().toUpperCase().equals("CR"))
                        payment.setCurrentAgeCode(0);
                    else
                        payment.setCurrentAgeCode(Integer.parseInt(row[3].toString()));
                }
                if (row[4] != null)
                    payment.setPaymentDate(format.parse(row[4].toString()));

                //Extra cells
                if (row[5] != null)
                    payment.setAccType(row[5].toString());
                if (row[6] != null)
                    payment.setProductCD(row[6].toString());
                if (row[7] != null)
                    payment.setLastAgingDate(format.parse(row[7].toString()));
                if (row[8] != null)
                    payment.setMinPayment(Double.parseDouble(row[8].toString()));
                if (row[9] != null)
                    payment.setCurrentMonthBal(Double.parseDouble(row[9].toString()));
                if (row[10] != null)
                    payment.setCurrentMontCount(Double.parseDouble(row[10].toString()));
                if (row[11] != null)
                    payment.setCurrentMonthPayDueDate(format.parse(row[11].toString()));
                if (row[12] != null) {
                    if (row[12].toString().trim().length() > 0) {
                        payment.setLastPaymentDate(format.parse(row[12].toString()));
                    } else {
                        payment.setLastPaymentDate(null);
                    }
                }
                if (row[13] != null)
                    payment.setLastPaymentAmount(Double.parseDouble(row[13].toString()) * (-1));
                if (row[14] != null)
                    payment.setIndividualAccNo(row[14].toString());
                if (row[15] != null)
                    payment.setLastActualStatementDate(format.parse(row[15].toString()));


                //age code bal
                List<AgeCodeData> codeData = new ArrayList<>();
                //System.err.println("MO_ageCode :"+MO_ageCode);
                if (MO_ageCode > 0) {
                    int index = 15;

                    for (int i = 1; i <= MO_ageCode - 1; i++) {
                        int dpd = 30 * i;
                        index++;

                        AgeCodeData ageCodeData = new AgeCodeData();
                        ageCodeData.setDpdName(dpd);
                        ageCodeData.setAgeCodeName(Integer.toString(i));
//                        System.err.println("INDEX :"+index);
                        if (row[index] != null) {
                            ageCodeData.setBalance(Double.parseDouble(row[index].toString()));
                        } else
                            ageCodeData.setBalance(0);

                        codeData.add(ageCodeData);
                    }
                }

                payment.setAgeCodeDataList(codeData);

                cardDetailPaymentList.add(payment);
            }

            entityManager.clear();
            //System.err.println("DETAIL :"+cardDetailPaymentList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return cardDetailPaymentList;
    }

    public CardDetailPaymentModel getCardDetailedPaymentInfoForUnPaidThisMonth(String cardNo, int MO_ageCode) {
        CardDetailPaymentModel cardDetailPayment = new CardDetailPaymentModel();

        try {
            String ageCodeSql = "";
            if (MO_ageCode > 0) {
                for (int i = 1; i <= MO_ageCode; i++) {
                    String temp = ",b.CB_";
                    int dpd = 30 * i;
                    temp = temp + dpd + "DAYS_BAL ";

                    ageCodeSql = ageCodeSql + temp;
                }
            }


            //Session session=entityManager.unwrap(Session.class);

            String sql = "SELECT a.AO_CARDHDR_NO, a.AO_BILLING_AMT, b.CB_FIN_ACCTNO, b.CB_LAST_AGE_CD, TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))), 'rrrrmmdd') AO_MATCH_DATE," +
                    "b.CB_FIN_ACCT_TYPE ," +
                    "b.CB_PRODUCT_CD," +
                    "b.CB_LAST_AGING_DATE," +
                    "b.CB_MIN_PMT," +
                    "b.CB_CURMTH_BAL," +
                    "b.CB_CURMTH_CNT," +
                    "b.CB_CURMTH_PAYDUE_DATE," +
                    "b.CB_LAST_PAYMENT_DATE," +
                    "b.CB_LAST_PAYMENT_AMT," +
                    "b.CB_INDIVIDUAL_ACCTNO," +
                    "b.CB_LAST_ACTUAL_STMT_DATE " + ageCodeSql +
                    "FROM CARD_APONUS a join cp_fintbl b ON a.AO_FIN_ACCTNO=b.CB_FIN_ACCTNO AND a.AO_CARDHDR_NO = '" + cardNo + "' ORDER BY a.AO_MATCH_DATE FETCH FIRST 1 ROW ONLY";


            System.out.println("Final SQL: " + sql);
            //SQLQuery query = session.createSQLQuery(sql);
            Query query = entityManager.createNativeQuery(sql);

            List<Object[]> rows = query.getResultList();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

            for (Object[] row : rows) {
                CardDetailPaymentModel payment = new CardDetailPaymentModel();

                if (row[0] != null)
                    payment.setCardNo(row[0].toString());
                if (row[1] != null)
                    payment.setAmount(Double.parseDouble(row[1].toString()));
                if (row[2] != null) {
                    payment.setFinAccNo(row[2].toString());
                }
                if (row[3] != null) {
                    if (row[3].toString().toUpperCase().equals("CR"))
                        payment.setCurrentAgeCode(0);
                    else
                        payment.setCurrentAgeCode(Integer.parseInt(row[3].toString()));
                }
                if (row[4] != null)
                    payment.setPaymentDate(format.parse(row[4].toString()));

                //Extra cells
                if (row[5] != null)
                    payment.setAccType(row[5].toString());
                if (row[6] != null)
                    payment.setProductCD(row[6].toString());
                if (row[7] != null)
                    payment.setLastAgingDate(format.parse(row[7].toString()));
                if (row[8] != null)
                    payment.setMinPayment(Double.parseDouble(row[8].toString()));
                if (row[9] != null)
                    payment.setCurrentMonthBal(Double.parseDouble(row[9].toString()));
                if (row[10] != null)
                    payment.setCurrentMontCount(Double.parseDouble(row[10].toString()));
                if (row[11] != null) {
                    String source = row[11].toString();
                    payment.setCurrentMonthPayDueDate(format.parse(source));
                }
                if (row[12] != null) {
                    String source = row[12].toString();
                    payment.setLastPaymentDate(format.parse(source));
                }
                if (row[13] != null)
                    payment.setLastPaymentAmount(Double.parseDouble(row[13].toString()) * (-1));
                if (row[14] != null)
                    payment.setIndividualAccNo(row[14].toString());
                if (row[15] != null)
                    payment.setLastActualStatementDate(format.parse(row[15].toString()));


                //age code bal
                List<AgeCodeData> codeData = new ArrayList<>();
                //System.err.println("MO_ageCode :"+MO_ageCode);
                if (MO_ageCode > 0) {
                    int index = 15;
                    for (int i = 1; i <= MO_ageCode; i++) {
                        int dpd = 30 * i;
                        index++;

                        AgeCodeData ageCodeData = new AgeCodeData();
                        ageCodeData.setDpdName(dpd);
                        ageCodeData.setAgeCodeName(Integer.toString(i));
//                        System.err.println("INDEX :"+index);
                        if (row[index] != null) {
                            ageCodeData.setBalance(Double.parseDouble(row[index].toString()));
                        } else
                            ageCodeData.setBalance(0);

                        codeData.add(ageCodeData);
                    }
                }
                payment.setAgeCodeDataList(codeData);

                cardDetailPayment = payment;
            }
            //System.err.println("DETAIL :"+cardDetailPayment);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }
        entityManager.clear();
        return cardDetailPayment;
    }

    public List<CardPaymentModel> getCardPaymentAmntOnlyByCardNo(String cardNo, Date date) {
        List<CardPaymentModel> paymentList = new ArrayList<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY");
            String sDate = formatter.format(date);

            //Session session=entityManager.unwrap(Session.class);
            String sql = "SELECT a.AO_CARDHDR_NO, a.AO_BILLING_AMT,TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))),'rrrrmmdd') AO_MATCH_DATE FROM CARD_APONUS a join cp_fintbl b ON TO_DATE (LTRIM (RTRIM ((AO_MATCH_DATE))),'rrrrmmdd') >= TO_DATE ('" + sDate + "') AND a.AO_FIN_ACCTNO=b.CB_FIN_ACCTNO AND a.AO_CARDHDR_NO = '" + cardNo + "'";

            //SQLQuery query = session.createSQLQuery(sql);
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> rows = query.getResultList();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            for (Object[] row : rows) {
                CardPaymentModel payment = new CardPaymentModel();

                if (row[0] != null)
                    payment.setCardNo(row[0].toString());
                if (row[1] != null)
                    payment.setAmount(Double.parseDouble(row[1].toString()));
                if (row[2] != null) {
                    payment.setPaymentDate(format.parse(row[2].toString()));
                }

                paymentList.add(payment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return paymentList;
    }

    public String getCardCusMblByCusId(String cusId) {
        String mbl = "";
        try {
            //Session session=entityManager.unwrap(Session.class);

            String sql = "SELECT CB_MOBILE_NO,CB_EMAIL FROM CP_CSTTBL WHERE CB_CUSTOMER_IDNO='" + cusId + "'";
            // SQLQuery query=session.createSQLQuery(sql);

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> rows = query.getResultList();

            for (Object row[] : rows) {
                if (row[0] != null)
                    mbl = row[0].toString();
                else
                    mbl = "";
            }
            entityManager.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mbl;
    }

    public List<CardAccountDistributionInfo> getAllCardsForKipCal() {
        List<CardAccountDistributionInfo> infos = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            Criteria crt = session.createCriteria(CardAccountDistributionInfo.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("latest", "1"));
            crt.add(Restrictions.ne("dealerPin", "0"));
            crt.add(Restrictions.eq("samAccount", "0"));
            crt.add(Restrictions.eq("writeOffAccount", "0"));
            crt.add(Restrictions.eq("latest", "1"));

            infos = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return infos;
    }


    public List<PtpStatusSummary> getLoanPtpStatusSummary(List<Long> accNoList) {
        List<PtpStatusSummary> loanPtpSummary = new ArrayList<>();
        try {
            Session session = entityManager.unwrap(Session.class);
            loanPtpSummary = session.createQuery(
                    "select new com.unisoft.collection.dashboard.PtpStatusSummary(" +
                            "lp.loan_ptp_status as ptp, count(distinct lp.customer_id) as noOfAc, SUM(lp.loan_amount) as amount) " +
                            "from com.unisoft.customerloanprofile.ptploan.LoanPtp lp " +
                            "WHERE lp.customer_id IN(:accNoList) and (lp.loan_ptp_date between :startDate and :endDate) " +
                            "GROUP BY lp.loan_ptp_status")
                    .setParameter("accNoList", accNoList)
                    .setParameter("startDate", dateUtils.getMonthStartDate(), TemporalType.DATE)
                    .setParameter("endDate", dateUtils.getMonthEndDate(), TemporalType.DATE)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loanPtpSummary;
    }

}
