package com.csinfotechbd.testModule;

import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class TestStoredProcedure extends StoredProcedure {

    private static final String SPROC_NAME = "FIND_ALL_PRODUCT";
    private static final String CUTOFF_DATE_PARAM = "p_var";

    public TestStoredProcedure(DataSource dataSource) {
        super(dataSource, SPROC_NAME);
        setFunction(true);
        declareParameter(new SqlParameter(CUTOFF_DATE_PARAM, OracleTypes.VARCHAR));
        //declareParameter(new SqlOutParameter(SPROC_NAME, OracleTypes.REF_CURSOR, new TestMapper()));
        compile();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> execute(String code) {
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(CUTOFF_DATE_PARAM, code);
        return super.execute(inputs);
    }
}