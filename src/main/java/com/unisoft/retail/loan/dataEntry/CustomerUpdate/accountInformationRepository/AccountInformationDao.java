package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountInformationDao {


    @Autowired
    @Qualifier("clientDbServerIfic")
    private DataSource clientDbServerIfic;

//    @Autowired
//    @Qualifier("clientDbServer2DataSource")
//    private DataSource clientDbServerIfic2;

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    public String getMaxTimestampFromApi() {
        String query = "SELECT MAX(TIMEST) AS TIMEST FROM USRBASELIB.$CRS1F";
        //String query = "SELECT MAX(CREATED_DATE) AS CREATED_DATE FROM ACCOUNT_INFORMATION_ENTITY";

        Statement statement = null;
        Connection connection = null;
        String maxTime = null;

        try {

            connection = clientDbServerIfic.getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
           // System.out.println(resultSet.next()==true?resultSet.getString("TIMEST"):"");
            maxTime = resultSet.next()==true?resultSet.getString("TIMEST"):"";

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return maxTime;
    }

    public  List<AccountInformationDto> getData(){

        // String query = "select * from CRLIB.CRPF";
        String query = "select * from USRBASELIB.$CRS1F";

        //co-applicant data
        //String query = "select * from USRBASELIB.$CRS3F";

        List<AccountInformationDto> accountInformationDtoList = new ArrayList<>();
        Statement statement=null;
        Connection connection = null;

        try{

            connection = clientDbServerIfic.getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){

                AccountInformationDto accountInformationDto = new AccountInformationDto();

                accountInformationDto.setLoanACNo(resultSet.getString("NEEAN"));
                accountInformationDto.setLastPaymentDate(resultSet.getString("LPAYDATE")); //updated at 16-01-2023
                //accountInformationDto.setLastPaymentDate(resultSet.getString("OMDTE01"));
                //accountInformationDto.setLastPaymentAmount(resultSet.getString("OMARC01"));
                accountInformationDto.setSettlementLinkAccountBalance(resultSet.getString("SCBAL"));
                accountInformationDto.setLinkMotherAccountNo(resultSet.getString("NEEAN01"));
                accountInformationDto.setRoutingNo(resultSet.getString("BSBBRN"));
                accountInformationDto.setMobile(resultSet.getString("AAZMPHN"));
                accountInformationDto.setBranchName(resultSet.getString("CABRN"));
                accountInformationDto.setBranchCode(resultSet.getString("V5ABD"));
                accountInformationDto.setOverdue(resultSet.getString("OVERDUE"));
                //accountInformationDto.setEmiAmount(resultSet.getString("OMNWR"));
                //accountInformationDto.setEmiAmount(resultSet.getString("IZCRA"));// updated at 16-01-2023
                accountInformationDto.setEmiAmount(resultSet.getString("EMIAMNT"));// updated at 19-01-2023
                //accountInformationDto.setEmiDate(resultSet.getString("IZNDT"));  // -- update 16/01/2023
                accountInformationDto.setEmiDate(resultSet.getString("FSTEMIDATE"));  // -- update 19/01/2023
                accountInformationDto.setNextEMIDate(resultSet.getString("NXTEMIDT")); // -- update 16/01/2023
                //accountInformationDto.setEmiDate(resultSet.getString("OMDTE"));
                //accountInformationDto.setLastPaymentAmount(resultSet.getString("OMNWR01"));
                accountInformationDto.setProductType(resultSet.getString("V5DLP"));
                accountInformationDto.setProductCode(resultSet.getString("V5DLP"));
                //accountInformationDto.setSanctionAmount(resultSet.getString("OTDLA2"));
                //accountInformationDto.setActualTenor(resultSet.getString("OTMDT"));
                //accountInformationDto.setDisbursementDate(resultSet.getString("OTSDTE"));
                accountInformationDto.setDisbursementDate(resultSet.getString("STDATE"));// updated at 19-01-2023
                //accountInformationDto.setTotalOutstanding(resultSet.getString("V5BAL"));
                accountInformationDto.setTotalOutstanding(resultSet.getString("OUTSTAMNT"));// updated at 19-01-2023
                accountInformationDto.setBorrowersName(resultSet.getString("GFCUN"));
                accountInformationDto.setProfession(resultSet.getString("BGPROF"));
                accountInformationDto.setProfessionSegment(resultSet.getString("BHDSC"));
                accountInformationDto.setEmail(resultSet.getString("AAZEAD1"));
                accountInformationDto.setNid(resultSet.getString("AAZIDN"));
                //accountInformationDto.setProfessionSegment(resultSet.getString("BGPROF"));
                //accountInformationDto.setExpiryDate(resultSet.getString("OTMDT"));
                accountInformationDto.setExpiryDate(resultSet.getString("EXPDATE"));//updated at 19-01-2023
                //accountInformationDto.setDisbursementAmount(resultSet.getString("OTDLA2"));
                accountInformationDto.setDisbursementAmount(resultSet.getString("TDISAMNT"));// updated at 19-01-2023

                accountInformationDto.setCustomerName(resultSet.getString("GFCUN"));
                accountInformationDto.setCustomerId(resultSet.getString("GFCPNC"));
                accountInformationDto.setCustomerType(resultSet.getString("GFCTP"));
                accountInformationDto.setSpouse(resultSet.getString("BGSNAM"));
                accountInformationDto.setDob(resultSet.getString("AAZBDTE"));
                accountInformationDto.setGender(resultSet.getString("BGSEX"));
                accountInformationDto.setFatherName(resultSet.getString("BGFNAM"));
                accountInformationDto.setMotherName(resultSet.getString("BGMNAM"));
                accountInformationDto.setNi(resultSet.getString("AAZIDN"));
                accountInformationDto.setTin(resultSet.getString("AAZTIN"));

                //new add
                accountInformationDto.setContractNo(resultSet.getString("AAZBPHN"));
                accountInformationDto.setContractNoHome(resultSet.getString("AAZHPHN"));
                accountInformationDto.setEconomicPurposeName(resultSet.getString("BHDSC02"));
                accountInformationDto.setEconomicPurposeCode(resultSet.getString("B0ECON"));
                accountInformationDto.setProductName(resultSet.getString("OBDPD"));
                accountInformationDto.setSectorCode(resultSet.getString("B0SECC"));
                accountInformationDto.setSectorName(resultSet.getString("BHDSC01"));
                accountInformationDto.setAddress1(resultSet.getString("SVNA1"));
                accountInformationDto.setAddress2(resultSet.getString("SVNA2"));
                accountInformationDto.setAddress3(resultSet.getString("SVNA3"));
                accountInformationDto.setAddress4(resultSet.getString("SVNA4"));
                accountInformationDto.setAddress5(resultSet.getString("SVNA5"));
                accountInformationDto.setAccountTitle(resultSet.getString("SCSHN"));
                accountInformationDto.setSmeCodeIndustryScaleID(resultSet.getString("SCC1R"));
                accountInformationDto.setInterestRate(resultSet.getString("V5APRR"));
                //accountInformationDto.setSanctionAmount(resultSet.getString("OTDLA2"));
                accountInformationDto.setLinkAccountStatus(resultSet.getString("SCAI20"));
                accountInformationDto.setLinkACProductCode(resultSet.getString("SCACT"));

                accountInformationDto.setBranchMnemonic(resultSet.getString("V5BRNM"));
                accountInformationDto.setDealReference(resultSet.getString("V5DLR"));
                accountInformationDto.setDealAcBasic(resultSet.getString("V5AND"));
                accountInformationDto.setDealAcSuffix(resultSet.getString("V5ASD"));
                accountInformationDto.setPartyId(resultSet.getString("GFPID"));
                accountInformationDto.setDocType(resultSet.getString("AAZIDC"));

                //added at 16/01/2023
                accountInformationDto.setDivision(resultSet.getString("DIVISION"));
                accountInformationDto.setDistrict(resultSet.getString("DISTRICT"));
                accountInformationDto.setScheduleStartDate(resultSet.getString("IZDTES"));
                accountInformationDto.setDealBalanceAtStartDate(resultSet.getString("IZBAL"));
                accountInformationDto.setCalculatedMaturityDate(resultSet.getString("IZMDT"));
                accountInformationDto.setFirstRepaymentAmount(resultSet.getString("IZCRAN"));
                accountInformationDto.setLastRepaymentAmount(resultSet.getString("IZCRAL"));
                //accountInformationDto.setTotalNoOfInstallment(resultSet.getString("IZNPY"));
                accountInformationDto.setTotalNoOfInstallment(resultSet.getString("NUMOFEMI"));// updated at 19-01-2023
                //accountInformationDto.setFrequencyCode(resultSet.getString("IZRFRQ"));
                accountInformationDto.setFrequencyCode(resultSet.getString("FREQCODE"));// updated at 19-01-2023
                //accountInformationDto.setLoanCLStatus(resultSet.getString("T9LSD"));
                accountInformationDto.setLoanCLStatus(resultSet.getString("CLSTATUS"));// updated at 19-01-2023
                accountInformationDto.setLastPaymentAmount(resultSet.getString("LPAYAMNT"));
                accountInformationDto.setLatestDisbursementDate(resultSet.getString("LDISDATE"));
                accountInformationDto.setLatestDisbursementAmount(resultSet.getString("LDISAMNT"));
                //accountInformationDto.setSanctionAmount(resultSet.getString("SANCAMNT")); removed at 1/2/2023
                accountInformationDto.setJointStatus(resultSet.getString("SCAIC7"));
                accountInformationDto.setNoOfInstallmentDue(resultSet.getString("NUMOFDUE"));
                accountInformationDto.setFirstInstDueDate(resultSet.getString("ODUEDATE"));

                accountInformationDtoList.add(accountInformationDto);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {

                if (statement !=null){
                    statement.close();
                }

                if(connection !=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return accountInformationDtoList;
    }


    public void updateCloseStatus() {
        Session session = entityManager.unwrap(Session.class);
        try {
//            String sql = "DECLARE\n" +
//                    "  CURSOR EXP_CUR IS\n" +
//                    "    SELECT IS_CLOSED FROM ACCOUNT_INFORMATION_ENTITY\n" +
//                    "    WHERE IS_CLOSED IS NOT NULL;\n" +
//                    "\n" +
//                    "  TYPE NT_FNAME IS TABLE OF VARCHAR2(100);\n" +
//                    "  FNAME NT_FNAME;\n" +
//                    "BEGIN\n" +
//                    "  OPEN EXP_CUR;\n" +
//                    "  FETCH EXP_CUR BULK COLLECT INTO FNAME LIMIT 1000;\n" +
//                    "  FORALL IDX IN FNAME.FIRST..FNAME.LAST\n" +
//                    "  UPDATE ACCOUNT_INFORMATION_ENTITY\n" +
//                    "  SET IS_CLOSED = 'Y'\n" +
//                    "  WHERE IS_CLOSED = FNAME(IDX);\n" +
//                    "\n" +
//                    "  COMMIT;\n" +
//                    "  CLOSE EXP_CUR;\n" +
//                    "END;";

            String sql = "UPDATE ACCOUNT_INFORMATION_ENTITY SET IS_CLOSED = 'Y'";
            SQLQuery query = session.createSQLQuery(sql);
            //session.flush();
            //String branchId = query.uniqueResult().toString();
           // return branchId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //return null;
        }
    }

}
