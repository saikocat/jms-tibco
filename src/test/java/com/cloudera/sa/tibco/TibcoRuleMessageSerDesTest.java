package com.cloudera.sa.tibco;

import com.cloudera.sa.tibco.dto.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TibcoRuleMessageSerDesTest {

    final Logger logger = LoggerFactory.getLogger(TibcoRuleMessageSerDesTest.class);

    @Before
    public void setup() {
        // Enable Indentation of XML
        // WARNING: XmlMapper is a singleton so this will affect all writing of xml
        TibcoRuleMessageSerDes.getXmlMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    }

    // WARNING: Be careful about testing string comparison for XML here
    // This is rather lazy way of testing so the content must match exactly
    // Common thing to look out in XML is the use of ' and ", UTF-8 capitalization

    @Test
    public void shouldDeserialize_KpiDetails() throws URISyntaxException, IOException {
        String xml = getFileContent("/kpi-details.xml");
        assertEquals(TibcoRuleMessageSerDes.deserialize(xml, KpiDetails.class),
                     createKpiDetails());
    }

    @Test
    public void shouldSerialize_KpiDetails() throws URISyntaxException, IOException {
        KpiDetails kds = createKpiDetails();
        assertEquals(TibcoRuleMessageSerDes.serialize(kds),
                     getFileContent("/kpi-details.xml"));
    }

    @Test
    public void shouldDeserialize_AlertDetails() throws URISyntaxException, IOException {
        String xml = getFileContent("/alert-details.xml");
        assertEquals(TibcoRuleMessageSerDes.deserialize(xml, AlertDetails.class),
                     createAlertDetails());
    }

    @Test
    public void shouldSerialize_AlertDetails() throws URISyntaxException, IOException {
        AlertDetails kds = createAlertDetails();
        assertEquals(TibcoRuleMessageSerDes.serialize(kds),
                     getFileContent("/alert-details.xml"));
    }

    @Test
    public void shouldDeserialize_TicketDetails() throws URISyntaxException, IOException {
        String xml = getFileContent("/ticket-details.xml");
        assertEquals(TibcoRuleMessageSerDes.deserialize(xml, TicketDetails.class),
                     createTicketDetails());
    }

    @Test
    public void shouldSerialize_TicketDetails() throws URISyntaxException, IOException {
        TicketDetails kds = createTicketDetails();
        assertEquals(TibcoRuleMessageSerDes.serialize(kds),
                     getFileContent("/ticket-details.xml"));
    }

    public String getFileContent(String resourceUrl) throws URISyntaxException, IOException {
        URL url = getClass().getResource(resourceUrl);
        Path resPath = Paths.get(url.toURI());
        return new String(Files.readAllBytes(resPath), "UTF8");
    }

    public KpiDetails createKpiDetails() {
        KpiDetail kd = new KpiDetail.Builder()
            .withPriority("High")
            .withNumber("tst 123")
            .withName("kpi name")
            .withCategory("cat 1")
            .withFraudType("Fraud Cat")
            .withServiceCustType("Service Cat 1")
            .withEnableSms(true)
            .withEnableEmail(false)
            .withThreshold(12345)
            .withDurationFrequency(123)
            .withEnableKpi("kpi cat")
            .withDescription("kpi description")
            .build();

        KpiDetails kds = new KpiDetails.Builder()
            .withKpiDetails(ImmutableList.<KpiDetail>of(kd, kd).asList())
            .build();

        return kds;
    }

    public AlertDetails createAlertDetails() {
        AlertDetail ad = new AlertDetail.Builder()
            .withAlertId("alert_id:str")
            .withKpiNumber("kpi_number:str")
            .withMsisdn("msisdn:str")
            .build();

        AlertDetails ads = new AlertDetails.Builder()
            .withAlertDetails(ImmutableList.<AlertDetail>of(ad).asList())
            .build();

        return ads;
    }

    public TicketDetails createTicketDetails() {
        TicketDetail td = new TicketDetail.Builder()
            .withAlertId("alert_id:str")
            .withTicketId("ticket_id:str")
            .withMsisdn("msisdn:str")
            .build();

        TicketDetails tds = new TicketDetails.Builder()
            .withTicketDetails(ImmutableList.<TicketDetail>of(td).asList())
            .build();

        return tds;
    }
}
