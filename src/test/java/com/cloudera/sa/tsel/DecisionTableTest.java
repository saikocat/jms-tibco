package com.cloudera.sa.tsel;

import com.cloudera.sa.tsel.dto.KpiDetail;
import com.cloudera.sa.tsel.dto.KpiDetails;
import com.cloudera.sa.tsel.handler.DbHandler;
import com.cloudera.sa.tsel.handler.KpiDetailHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DecisionTableTest extends JdbcTest {

    private Connection conn;
    private String initSql = "sql/000-kpi-detail-create-table-mysql.sql";

    @Before
    public void setup() {
        super.setup();
        conn = super.getConnection();
        logger.info("Inited connection from base class...");
    }

    @Test
    public void shouldInsertAndReadForMySQL() throws SQLException, JMSException, IOException {
        Statement stmt = conn.createStatement();
        setDbCompatMode("MySQL", stmt);
        initScript(initSql, stmt);

        // Mock the TextMesage
        Session session = mock(Session.class);
        when(session.createTextMessage(any(String.class))).thenAnswer(new Answer<TextMessage>() {
            @Override
            public TextMessage answer(InvocationOnMock invocationOnMock) throws Throwable {
                TextMessage mockTextMessage = mock(TextMessage.class);
                when(mockTextMessage.getText()).thenReturn((String) invocationOnMock.getArguments()[0]);
                return mockTextMessage;
            }
        });

        // Insertion
        KpiDetailHandler handler = new KpiDetailHandler("KPI_Detail", new DbHandler<KpiDetail>(conn));
        handler.processMessage(session.createTextMessage(getFileContent("/kpi-details.xml")));

        // Verify insertion by SQL
        Statement verifyStmt = conn.createStatement();
        ResultSet verifyRs= verifyStmt.executeQuery("SELECT * FROM KPI_Detail LIMIT 1");
        verifyRs.next();
        assertEquals(verifyRs.getString("KPI_Name"), "kpi name");
        assertEquals(verifyRs.getString("Fraud_Type"), "Fraud Cat");
        assertEquals(verifyRs.getInt("Threshold"), 12345);
        assertEquals(verifyRs.getBoolean("Enable_SMS"), true);

        // Query the result using the Handler
        KpiDetails kds = handler.processQueryAllFields();
        // Verify the result of POJO comparison
        assertEquals(
            kds,
            TibcoRuleMessageSerDes.deserialize(
                getFileContent("/kpi-details.xml"), KpiDetails.class)
        );
    }
}
