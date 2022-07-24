package com.unisoft.loanApi.dao;

import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.loanApi.model.AdvancedSearchDataModel;
import com.unisoft.loanApi.model.BaseLoanApiModel;
import com.unisoft.loanApi.model.LoanAccStatement;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.unisoft.utillity.StringUtils;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;



@Repository
@RequiredArgsConstructor
public class SimpleJdbcCallBasedDao implements FunctionDao {

    @Autowired
    @Qualifier("clientDbServer2DataSource")
    private final DataSource clientDbServer2DataSource;

    @Autowired
    @Qualifier("clientDbServer1DataSource")
    private final DataSource clientDbServer1DataSource;

    @Nullable
    @Value("${client.db.server2.schema.name}")
    private final String misreadSchemaName;

    @Nullable
    @Value("${client.db.server2.package.name}")
    private final String misreadPackageName;

    @Nullable
    @Value("${client.db.server1.schema.name}")
    private final String cbsreadSchemaName;

    @Nullable
    @Value("${client.db.server1.package.name}")
    private final String cbsreadPackageName;

    @Override
    public <T extends BaseLoanApiModel> List<T> getDataFromServer1(String functionName, Map<String, Object> params, Class<T> elementClass) {
        SimpleJdbcCall call = createJdbcCall(functionName, elementClass, "CBSREAD");
        return getResultSet(call, params);
    }

    @Override
    public <T extends BaseLoanApiModel> List<T> getDataFromServer2(String functionName, Map<String, Object> params, Class<T> elementClass) {
        SimpleJdbcCall call = createJdbcCall(functionName, elementClass, "misread");
        return getResultSet(call, params);
    }

    @Override
    public LoanAccStatement getLatestAccountStatement(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<AdvanceSearchDataModel> getAdvanceSearchData(Map<String, Object> params) {
        MapSqlParameterSource paramSource = getParameterSourceForJdbcCall(params);
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(clientDbServer2DataSource)
                .withProcedureName("ucbprod.rsp_gen_Advance_Search").withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_date", Types.VARCHAR),
                        new SqlParameter("p_cif_no", Types.VARCHAR),
                        new SqlParameter("p_TIN", Types.VARCHAR),
                        new SqlParameter("p_Status", Types.VARCHAR),
                        new SqlParameter("p_Passport_No", Types.VARCHAR),
                        new SqlParameter("p_Organization", Types.VARCHAR),
                        new SqlParameter("p_NID", Types.VARCHAR),
                        new SqlParameter("p_Mother_Name", Types.VARCHAR),
                        new SqlParameter("p_Mobile_No", Types.VARCHAR),
                        new SqlParameter("p_Link_Acc", Types.VARCHAR),
                        new SqlParameter("p_Email", Types.VARCHAR),
                        new SqlParameter("p_DOB", Types.VARCHAR),
                        new SqlParameter("p_Customer_Name", Types.VARCHAR),
                        new SqlParameter("p_Cls_Flag", Types.VARCHAR),
                        new SqlParameter("p_Auto_Debit_Acc", Types.VARCHAR),
                        new SqlParameter("p_Account_No", Types.VARCHAR),
                        new SqlOutParameter("pReport", OracleTypes.CURSOR))
                .returningResultSet(" pReport", BeanPropertyRowMapper.newInstance(AdvanceSearchDataModel.class));
        Map<String, Object> result = simpleJdbcCall.execute(new HashMap<>(), paramSource);
        return (List) result.get(" pReport");
    }

    //advanced search method for SAMD
    @Override
    public List<AdvancedSearchDataModel> getSamdAdvancedSearchData(Map<String, Object> params) {
        MapSqlParameterSource paramSource = getParameterSourceForJdbcCall(params);
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(clientDbServer2DataSource)
                .withProcedureName("ucbprod.rsp_gen_Advance_Search").withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_ld_number", Types.VARCHAR),
                        new SqlParameter("p_loan_account_number", Types.VARCHAR),
                        new SqlParameter("p_loan_account_name", Types.VARCHAR),
                        new SqlParameter("p_case_number", Types.VARCHAR),
                        new SqlParameter("p_customer_id", Types.VARCHAR),
                        new SqlParameter("p_lln", Types.VARCHAR),
                        new SqlParameter("p_lawyers_id", Types.VARCHAR),
                        new SqlParameter("p_lawyers_name", Types.VARCHAR),
                        new SqlParameter("p_case_type", Types.VARCHAR),
                        new SqlParameter("p_case_status", Types.VARCHAR),
                        new SqlParameter("p_next_date", Types.VARCHAR),
                        new SqlParameter("p_next_date_status", Types.VARCHAR),
                        new SqlParameter("p_borrowers_organization", Types.VARCHAR),
                        new SqlParameter("p_borrowers_name", Types.VARCHAR),
                        new SqlParameter("p_borrowers_dob", Types.VARCHAR),
                        new SqlParameter("p_borrowers_dl", Types.VARCHAR),
                        new SqlParameter("p_borrowers_mobile_no", Types.VARCHAR),
                        new SqlParameter("p_existing_case_status", Types.VARCHAR),
                        new SqlParameter("p_borrowers_email", Types.VARCHAR),
                        new SqlParameter("p_borrowers_nid", Types.VARCHAR),
                        new SqlParameter("p_result_of_the_case", Types.VARCHAR),
                        new SqlParameter("p_borrowers_passport", Types.VARCHAR),
                        new SqlParameter("p_borrowers_father_name", Types.VARCHAR),
                        new SqlParameter("p_status", Types.VARCHAR),

                        new SqlOutParameter("pReport", OracleTypes.CURSOR))
                .returningResultSet(" pReport", BeanPropertyRowMapper.newInstance(AdvancedSearchDataModel.class));
        Map<String, Object> result = simpleJdbcCall.execute(new HashMap<>(), paramSource);
        return (List) result.get(" pReport");
    }

    public List test() {
        List list = new ArrayList();
        // Test implementation
        return list;
    }

    private <T extends BaseLoanApiModel> SimpleJdbcCall createJdbcCall(String functionName, Class<T> type, String serverUserName) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(clientDbServer1DataSource);
        try {
            switch (serverUserName.toLowerCase()) {
                case "misread":
                    simpleJdbcCall = new SimpleJdbcCall(clientDbServer2DataSource);
                    setSchemaNameToJdbcCall(simpleJdbcCall, misreadSchemaName);
                    setPackageNameToJdbcCall(simpleJdbcCall, misreadPackageName);
                    break;
                case "cbsread":
                    simpleJdbcCall = new SimpleJdbcCall(clientDbServer1DataSource);
                    setSchemaNameToJdbcCall(simpleJdbcCall, cbsreadSchemaName);
                    setPackageNameToJdbcCall(simpleJdbcCall, cbsreadPackageName);
                    break;
                default:
                    return simpleJdbcCall;
            }
            setFunctionNameToJdbcCall(simpleJdbcCall, functionName);
            simpleJdbcCall.returningResultSet("entities", BeanPropertyRowMapper.newInstance(type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleJdbcCall;
    }

    private <T extends BaseLoanApiModel> List<T> getResultSet(SimpleJdbcCall simpleJdbcCall, Map<String, Object> params) {
        LinkedList<T> list = new LinkedList<>();
        if (simpleJdbcCall.isFunction()) {
            MapSqlParameterSource inputParams = getParameterSourceForJdbcCall(params);
            list = simpleJdbcCall.executeFunction(LinkedList.class, inputParams);
        }
        return list;
    }

    private MapSqlParameterSource getParameterSourceForJdbcCall(Map<String, Object> params) {
        MapSqlParameterSource inputParams = new MapSqlParameterSource();
        params.forEach(inputParams::addValue);
        return inputParams;
    }

    private void setSchemaNameToJdbcCall(SimpleJdbcCall jdbcCall, String schemaName) {
        if (StringUtils.hasText(schemaName))
            jdbcCall.withSchemaName(schemaName);
    }

    private void setPackageNameToJdbcCall(SimpleJdbcCall jdbcCall, String packageName) {
        if (StringUtils.hasText(packageName))
            jdbcCall.withCatalogName(packageName);
    }

    private void setFunctionNameToJdbcCall(SimpleJdbcCall jdbcCall, String functionName) {
        if (StringUtils.hasText(functionName))
            jdbcCall.withFunctionName(functionName);
    }

}
