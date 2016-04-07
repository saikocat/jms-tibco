package com.cloudera.sa.tsel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import com.google.common.collect.ImmutableList;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.cloudera.sa.tsel.dto.*;

public class TibcoRuleMessageSerDesTest {

    @Before
    public void setup() {
        // Enable Indentation of XML
        // WARNING: XmlMapper is a singleton so this will affect all writing of xml
        TibcoRuleMessageSerDes.getXmlMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    public void shouldDeserialize_KpiDetails() throws URISyntaxException,
           IOException, UnsupportedEncodingException {
        String xml = getFileContent("/kpi-details.xml");
        assertEquals(TibcoRuleMessageSerDes.deserialize(xml, KpiDetails.class),
                     createKpiDetails());
    }

    @Test
    public void shouldSerialize_KpiDetails() throws URISyntaxException,
           IOException, UnsupportedEncodingException, JsonProcessingException {
        KpiDetails kds = createKpiDetails();
        assertEquals(TibcoRuleMessageSerDes.serialize(kds),
                     getFileContent("/kpi-details.xml"));
    }

    public String getFileContent(String resourceUrl) throws URISyntaxException,
           IOException, UnsupportedEncodingException {
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

}
