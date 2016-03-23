package com.cloudera.sa.tsel;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.common.base.Optional;

import java.io.IOException;

public class TibcoRuleMessageSerDes {
    private static XmlMapper xmlMapper;

    private TibcoRuleMessageSerDes() {
    }

    public static String serialize(TibcoRuleMessage ruleMessage) throws JsonProcessingException {
        return getXmlMapper().writeValueAsString(ruleMessage);
    }

    public static TibcoRuleMessage deserialize(String text) throws IOException {
        return getXmlMapper().readValue(text, TibcoRuleMessage.class);
    }

    public static XmlMapper getXmlMapper() {
        if (!Optional.fromNullable(xmlMapper).isPresent())
            xmlMapper = new XmlMapper();
        return xmlMapper;
    }
}
