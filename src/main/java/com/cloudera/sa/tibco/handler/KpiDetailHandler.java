package com.cloudera.sa.tibco.handler;

import com.cloudera.sa.tibco.dto.*;
import com.cloudera.sa.tibco.TibcoRuleMessageSerDes;

import javax.jms.TextMessage;

import java.io.IOException;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KpiDetailHandler implements BaseHandler, AllDbFieldsToPojoTransformer {

    final Logger logger = LoggerFactory.getLogger(KpiDetailHandler.class);

    private final String tableName;
    private DbHandler<KpiDetail> dbHandler;

    public KpiDetailHandler(String tableName, final DbHandler dbHandler) {
        this.tableName = tableName;
        this.dbHandler = dbHandler;
    }

    @Override
    public void processMessage(TextMessage message) throws IOException, JMSException {
        KpiDetails kpiDetails =
            TibcoRuleMessageSerDes.deserialize(message.getText(), KpiDetails.class);
        logger.debug(kpiDetails.toString());

        dbHandler.insert(new DbHandler.InsertOperation<KpiDetail>() {
            @Override
            public void doInsert(KpiDetail[] kpis) throws SQLException {
                Connection conn = dbHandler.getConnection();
                for (int i=0; i < kpis.length; i++) {
                    KpiDetail kd = kpis[i];
                    PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO " + tableName +
                        "(KPI_Priority, KPI_Number, KPI_Name, KPI_Category, Fraud_Type, Service_Cust_Type, Enable_SMS, Enable_Email, Threshold, Duration_Frequency, Enable_KPI, Description) VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)");
                    stmt.setString(1, kd.priority);
                    stmt.setString(2, kd.number);
                    stmt.setString(3, kd.name);
                    stmt.setString(4, kd.category);
                    stmt.setString(5, kd.fraudType);
                    stmt.setString(6, kd.serviceCustType);
                    stmt.setBoolean(7, kd.enableSms);
                    stmt.setBoolean(8, kd.enableEmail);
                    stmt.setInt(9, kd.threshold);
                    stmt.setInt(10, kd.durationFrequency);
                    stmt.setString(11, kd.enableKpi);
                    stmt.setString(12, kd.description);
                    stmt.executeUpdate();
                }
            }
        }, kpiDetails.kpiDetails);
    }

    @Override
    public KpiDetails processQueryAllFields() throws SQLException {
        Connection conn = dbHandler.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName);

        KpiDetail[] kdItems = dbHandler.query(stmt, new DbHandler.ResultSetTransformer<KpiDetail>() {
            @Override
            public KpiDetail[] transform(ResultSet rs) throws SQLException {
                java.util.List<KpiDetail> items = new java.util.ArrayList<KpiDetail>();
                while (rs.next()) {
                    KpiDetail kd = rowMapper(rs);
                    items.add(kd);
                }
                logger.debug(items.toString());
                return items.toArray(new KpiDetail[items.size()]);
            }
        });

        return new KpiDetails(kdItems);
    }

    // Call this after you moved the cursor
    @Override
    public KpiDetail rowMapper(ResultSet rs) throws SQLException {
        return new KpiDetail.Builder()
            .withPriority(rs.getString("KPI_Priority"))
            .withNumber(rs.getString("KPI_Number"))
            .withName(rs.getString("KPI_Name"))
            .withCategory(rs.getString("KPI_Category"))
            .withFraudType(rs.getString("Fraud_Type"))
            .withServiceCustType(rs.getString("Service_Cust_Type"))
            .withEnableSms(rs.getBoolean("Enable_SMS"))
            .withEnableEmail(rs.getBoolean("Enable_Email"))
            .withThreshold(rs.getInt("Threshold"))
            .withDurationFrequency(rs.getInt("Duration_Frequency"))
            .withEnableKpi(rs.getString("Enable_KPI"))
            .withDescription(rs.getString("Description"))
            .build();
    }
}
