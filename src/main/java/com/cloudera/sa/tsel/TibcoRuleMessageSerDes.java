package com.cloudera.sa.tsel;

import com.cloudera.sa.tsel.dto.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.common.base.Optional;

import java.io.IOException;

public class TibcoRuleMessageSerDes {
    private static XmlMapper xmlMapper;

    private TibcoRuleMessageSerDes() {
    }

    public static String serialize(Object o) throws JsonProcessingException {
        return getXmlMapper().writeValueAsString(o);
    }

    public static KpiDetails deserializeKpiDetails(String text) throws IOException {
        return getXmlMapper().readValue(text, KpiDetails.class);
    }

    public static KpiDetail deserializeKpiDetail(String text) throws IOException {
        return getXmlMapper().readValue(text, KpiDetail.class);
    }

    public static XmlMapper getXmlMapper() {
        if (!Optional.fromNullable(xmlMapper).isPresent())
            xmlMapper = new XmlMapper();
        return xmlMapper;
    }
}
