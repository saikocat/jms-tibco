package com.cloudera.sa.tibco.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AllDbFieldsToPojoTransformer<T> {
    T processQueryAllFields() throws SQLException;
    T rowMapper(ResultSet rs) throws SQLException;
}
