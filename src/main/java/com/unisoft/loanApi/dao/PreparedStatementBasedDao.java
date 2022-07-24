package com.unisoft.loanApi.dao;

import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.loanApi.model.AdvancedSearchDataModel;
import com.unisoft.loanApi.model.BaseLoanApiModel;
import com.unisoft.loanApi.model.LoanAccStatement;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;


@Repository
@RequiredArgsConstructor
@Slf4j
public class PreparedStatementBasedDao implements FunctionDao {

    @Autowired
    @Qualifier("clientDbServer1DataSource")
    private final DataSource clientDbServer1DataSource;

    @Autowired
    @Qualifier("clientDbServer2DataSource")
    private final DataSource clientDbServer2DataSource;

    @Autowired
    private final DateUtils dateUtils;

    @Nullable
    @Value("${client.db.server1.schema.name}")
    private final String clientDbServer1SchemaName;

    @Nullable
    @Value("${client.db.server1.package.name}")
    private final String clientDbServer1PackageName;

    @Nullable
    @Value("${client.db.server2.schema.name}")
    private final String clientDbServer2SchemaName;

    @Nullable
    @Value("${client.db.server2.package.name}")
    private final String clientDbServer2PackageName;


    @Override
    public <T extends BaseLoanApiModel> List<T> getDataFromServer1(String functionName, Map<String, Object> params, Class<T> type) {

        Connection conn = null;
        PreparedStatement statement = null;
        List result = new ArrayList();

        try {
            String query = getQuery(functionName, params, clientDbServer1SchemaName, clientDbServer1PackageName);

            conn = clientDbServer1DataSource.getConnection();
            statement = conn.prepareStatement(query);

            boolean isParametersSet = setQueryParameters(statement, params);

            if (StringUtils.hasText(clientDbServer1SchemaName) && StringUtils.hasText(clientDbServer1PackageName) && isParametersSet) {
                result = getResultSet(statement, type);
            }
        } catch (Exception e) {
            log.error("error in getDataFromServer1");
        } finally {
            closeConnection(conn);
            closeStatement(statement);
        }
        return result;
    }

    @Override
    public <T extends BaseLoanApiModel> List<T> getDataFromServer2(String functionName, Map<String, Object> params, Class<T> type) {
        Connection conn = null;
        PreparedStatement statement = null;
        List result = new ArrayList();

        try {
            String query = getQuery(functionName, params,clientDbServer2SchemaName, clientDbServer2PackageName);

            conn = clientDbServer2DataSource.getConnection();
            statement = conn.prepareStatement(query);

            boolean isParametersSet = setQueryParameters(statement, params);

            if (StringUtils.hasText(clientDbServer2SchemaName) && StringUtils.hasText(clientDbServer2PackageName) && isParametersSet) {
                result = getResultSet(statement, type);
            }
        } catch (Exception e) {
            log.error("error in getDataFromServer2");
        } finally {
            closeConnection(conn);
            closeStatement(statement);
        }
        return result;
    }

//    @Override
//    public List test() {
//        List list = new ArrayList();
//        Connection conn = null;
//        CallableStatement callableStatement = null;
//        try {
//            conn = clientDbServer1DataSource.getServer1Connection();
//            PreparedStatement stmt = conn.prepareStatement("SELECT ubsprod.upks_coll_recovery.FXN_GET_LOAN_ACC_INFO('050RHLN142910007') FROM dual");
////            conn = clientDbServer2DataSource.getServer1Connection();
////            PreparedStatement stmt = conn.prepareStatement("SELECT ucbprod.upks_coll_recovery.FXN_GET_LOAN_ACC_INFO('050RHLN142910007') FROM dual");
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                ResultSet resultSet = (ResultSet) rs.getObject(1);
//
//                while (resultSet.next()) {
//                    String objectMap = resultSet.getString(1);
//                    System.out.println("Test Successful");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return list;
//    }

    @Override
    public LoanAccStatement getLatestAccountStatement(Map<String, Object> params) {
        LoanAccStatement latestStatement = new LoanAccStatement();
        String sql = getQuery("FXN_GET_LOAN_ACC_STATEMENT", params, clientDbServer1SchemaName, clientDbServer1PackageName);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet outerResultSet = null;
        ResultSet innerResultSet = null;
        try {
            conn = getServer1Connection();

            stmt = conn.prepareStatement(sql);
            setQueryParameters(stmt, params);

            outerResultSet = stmt.executeQuery();
            if (outerResultSet.next()) {

                Date monthStartDate = dateUtils.getMonthEndDate(-1);

                double totalPrincipalPaid = 0;
                double totalInterestPaid = 0;
                double totalPaymentCurrentMonth = 0;
                double totalSubsidyFromBbCovid = 0;
                double totalRolloverOfContract = 0;
                double totalWaivedInterest = 0;
                double totalPayment = 0;
                double otherCharge = 0;


                innerResultSet = (ResultSet) outerResultSet.getObject(1);
                while (innerResultSet.next()) {
                    LoanAccStatement loanStatement = new LoanAccStatement();
                    loanStatement.setPropertiesFromResultset(innerResultSet);
                    totalPrincipalPaid += loanStatement.getPrincipalCredit();
                    totalInterestPaid += loanStatement.getInterestCredit();
                    loanStatement.setLastPayment();

                    if (loanStatement.getValueDate().after(monthStartDate))
                        totalPaymentCurrentMonth += loanStatement.getPrincipalCredit() + loanStatement.getInterestCredit();

                    if (loanStatement.getLastPayment() > 0) {
                        latestStatement = loanStatement;

//                        System.out.println(loanStatement.getEvent());
//
//                        if (loanStatement.getEvent().equals("ALIQ") || latestStatement.getEvent().equals("MLIQ")){
//                            totalPayment += loanStatement.getPrincipalCredit()+loanStatement.getInterestCredit();
//                        }

                        if (loanStatement.getDescriptoion().equals("Manual Liquidation for Subsidy from BB COVID")) {
                            totalSubsidyFromBbCovid += loanStatement.getPrincipalCredit()+loanStatement.getInterestCredit();
                        }
                        if (loanStatement.getDescriptoion().equalsIgnoreCase("Rollover of Contract")) {
                            totalRolloverOfContract += loanStatement.getPrincipalCredit()+loanStatement.getInterestCredit();
                        }


                        if (loanStatement.getDescriptoion().equalsIgnoreCase(" Waived Interest") || loanStatement.getDescriptoion().equalsIgnoreCase("Waived Interest")) {
                            totalWaivedInterest += loanStatement.getPrincipalCredit()+loanStatement.getInterestCredit();
                        }

                        if (loanStatement.getEvent().equalsIgnoreCase("ADCH") || loanStatement.getEvent().equalsIgnoreCase("INIT")){
                            if (!loanStatement.getDescriptoion().equalsIgnoreCase("VAT")){
                                otherCharge += loanStatement.getPrincipalCredit()+loanStatement.getInterestCredit();
                            }
                        }


                    }
                }

                double customerPaidAmt = 0;
                customerPaidAmt = (totalInterestPaid + totalPrincipalPaid) - (totalSubsidyFromBbCovid + totalRolloverOfContract + totalWaivedInterest);

                latestStatement.setTotalInterest(totalInterestPaid - totalRolloverOfContract);
                latestStatement.setTotalPrinciple(totalPrincipalPaid);
//                latestStatement.setLastPayment(totalInterestPaid + totalPrincipalPaid);
//                latestStatement.setLastPayment(customerPaidAmt);
                latestStatement.setTotalPaymentCurrentMonth(totalPaymentCurrentMonth);
                latestStatement.setTotalSubsidyFromBbCovid(totalSubsidyFromBbCovid);
                latestStatement.setTotalRolloverOfContract(totalRolloverOfContract);
                latestStatement.setTotalWaivedInterest(totalWaivedInterest);
                latestStatement.setOtherCharge(otherCharge);
                latestStatement.setTotalCustomerPaid(customerPaidAmt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultset(innerResultSet);
            closeResultset(outerResultSet);
            closeStatement(stmt);
            closeConnection(conn);
        }
        return latestStatement;
    }

    @Override
    public List<AdvancedSearchDataModel> getSamdAdvancedSearchData(Map<String, Object> params) {
        List<AdvancedSearchDataModel> data = new LinkedList<>();
        String sql = "{ call ucbprod.rpks_coll_recovery.rsp_gen_Advance_Search(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet result = null;
        try {
            conn = clientDbServer2DataSource.getConnection();
            stmt = conn.prepareCall(sql);
            boolean isParameterSet = setQueryParameters(stmt, params);

            if (!isParameterSet) return data;

            stmt.registerOutParameter(24, OracleTypes.CURSOR);
            stmt.execute();

            result = (ResultSet) stmt.getObject(24);

            while (result.next()) {
                AdvancedSearchDataModel dataModel = new AdvancedSearchDataModel();
                dataModel.setPropertiesFromResultset(result);
                data.add(dataModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultset(result);
            closeStatement(stmt);
            closeConnection(conn);
        }
        return data;
    }

    @Override
    public List<AdvanceSearchDataModel> getAdvanceSearchData(Map<String, Object> params) {
        List<AdvanceSearchDataModel> data = new LinkedList<>();
        String sql = "{ call ucbprod.rsp_gen_Advance_Search(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet result = null;
        try {
            conn = clientDbServer2DataSource.getConnection();
            stmt = conn.prepareCall(sql);
            boolean isParameterSet = setQueryParameters(stmt, params);

            if (!isParameterSet) return data;

            stmt.registerOutParameter(17, OracleTypes.CURSOR);
            stmt.execute();

            result = (ResultSet) stmt.getObject(17);

            while (result.next()) {
                AdvanceSearchDataModel dataModel = new AdvanceSearchDataModel();
                dataModel.setPropertiesFromResultset(result);
                data.add(dataModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultset(result);
            closeStatement(stmt);
            closeConnection(conn);
        }
        return data;
    }

    private String getQuery(String functionName, Map<String, Object> params, String schemaName, String packageName) {

        StringBuilder namedParameters = new StringBuilder();
        int limit = params.values().size() - 1;
        for (int i = 0; i < limit; i++) {
            namedParameters.append("?,");
        }
        namedParameters.append("?");

        String query = "SELECT ";
        query = setSchemaNameToQuery(query, schemaName);
        query = setPackageNameToQuery(query, packageName);
        query = setFunctionNameToQuery(query, functionName);

        return query + "(" + namedParameters.toString() + ") FROM dual";
    }

    private boolean setQueryParameters(PreparedStatement statement, Map<String, Object> params) {
        int count = 0;

        try {
            for (Object param : params.values()) {
                if (param instanceof String) {
                    statement.setString(++count, Objects.toString(param, ""));
                } else if (param instanceof java.sql.Date) {
                    statement.setDate(++count, (java.sql.Date) param);
                } else {
                    statement.setObject(++count, param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private <T extends BaseLoanApiModel> List<T> getResultSet(PreparedStatement statement, Class<T> type) {

        List result = new ArrayList();
        ResultSet outerResultSet = null;
        ResultSet innerResultSet = null;
        try {
            outerResultSet = statement.executeQuery();
            if (outerResultSet.next()) {
                innerResultSet = (ResultSet) outerResultSet.getObject(1);

                while (innerResultSet.next()) {
                    T dbModel = type.newInstance();
                    dbModel.setPropertiesFromResultset(innerResultSet);
                    result.add(dbModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultset(innerResultSet);
            closeResultset(outerResultSet);
        }

        return result;
    }

    private String setSchemaNameToQuery(String query, String schemaName) {
        if (StringUtils.hasText(schemaName))
            query += schemaName + ".";
        return query;
    }

    private String setPackageNameToQuery(String query, String packageName) {
        if (StringUtils.hasText(packageName))
            query += packageName + ".";
        return query;
    }

    private String setFunctionNameToQuery(String query, String functionName) {
        if (StringUtils.hasText(functionName))
            query += functionName;
        return query;
    }

    private Connection getServer1Connection() throws SQLException {
        return clientDbServer1DataSource.getConnection();
    }

    private Connection getServer2Connection() throws SQLException {
        return clientDbServer2DataSource.getConnection();
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResultset(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
